package car_rental_book_and_manage.Objects;

import car_rental_book_and_manage.App;
import car_rental_book_and_manage.Payment.CardPayment;
import car_rental_book_and_manage.Utility.DataManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Class for managing reservation database operations. */
public class ReservationDB implements ReservationDAO {

  private static final DataModel model = DataModel.getInstance();

  /**
   * Saves a reservation and payment to the database.
   *
   * @param reservation the reservation to save
   * @param payment the payment to save
   */
  @Override
  public synchronized void saveReservationAndPayment(Reservation reservation, CardPayment payment) {
    App.reservationdbExecutor.execute(() -> {
      String reservationQuery = "INSERT INTO RESERVATION (Client_Id, Vehicle_Id, Total_rate, Vehicle_reg, License_no, Start_date, Return_date, Insurance_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      String paymentQuery = "INSERT INTO PAYMENT (Rental_Id, Client_Id, Payment_Date, Amount, Payment_Method) VALUES (?, ?, ?, ?, ?)";

      try (Connection connection = DataManager.getConnection()) {
        connection.setAutoCommit(false);

        try (PreparedStatement reservationStatement = connection.prepareStatement(reservationQuery, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement paymentStatement = connection.prepareStatement(paymentQuery)) {

          // Save reservation
          setReservationStatementParams(reservationStatement, reservation);
          reservationStatement.executeUpdate();

          // Get generated Rental_Id
          ResultSet generatedKeys = reservationStatement.getGeneratedKeys();
          if (generatedKeys.next()) {
            int rentalId = generatedKeys.getInt(1);
            reservation.setReservationId(rentalId);
            payment.setRentalId(rentalId);
          }

          // Save payment
          setPaymentStatementParams(paymentStatement, payment);
          paymentStatement.executeUpdate();

          connection.commit();
          retrieveLatestReservationToSave();
          System.out.println("Reservation and payment saved");
        } catch (SQLException e) {
          connection.rollback();
          handleSQLException(e);
        }
      } catch (SQLException e) {
        handleSQLException(e);
      }
    });
  }

  /**
   * Deletes a reservation and its associated payment from the database.
   *
   * @param reservationId the ID of the reservation to delete
   */
  @Override
  public synchronized void deleteReservationAndPayment(int reservationId) {
    App.reservationdbExecutor.execute(() -> {
      String reservationQuery = "DELETE FROM RESERVATION WHERE Rental_Id = ?";
      String paymentQuery = "DELETE FROM PAYMENT WHERE Rental_Id = ?";

      try (Connection connection = DataManager.getConnection()) {
        connection.setAutoCommit(false);

        try (PreparedStatement reservationStatement = connection.prepareStatement(reservationQuery);
             PreparedStatement paymentStatement = connection.prepareStatement(paymentQuery)) {

          // Delete payment
          paymentStatement.setInt(1, reservationId);
          paymentStatement.executeUpdate();

          // Delete reservation
          reservationStatement.setInt(1, reservationId);
          reservationStatement.executeUpdate();

          connection.commit();
          model.removeReservation(model.getReservation(reservationId));
          System.out.println("Reservation and payment deleted");
        } catch (SQLException e) {
          connection.rollback();
          handleSQLException(e);
        }
      } catch (SQLException e) {
        handleSQLException(e);
      }
    });
  }

  /** Retrieves the latest saved reservation from the database and adds it to the model. */
  @Override
  public synchronized void retrieveLatestReservationToSave() {
    App.reservationdbExecutor.execute(() -> {
      String sql = "SELECT * FROM RESERVATION ORDER BY Rental_Id DESC LIMIT 1";
      try (Connection connection = DataManager.getConnection();
           PreparedStatement statement = connection.prepareStatement(sql);
           ResultSet resultSet = statement.executeQuery()) {

        if (resultSet.next()) {
          Reservation reservation = mapResultSetToReservation(resultSet);
          model.addReservation(reservation);
        }
      } catch (SQLException e) {
        handleSQLException(e);
      }
      System.err.println("Retrieved latest reservation");
    });
  }

  /** Retrieves all reservations from the database and adds them to the model. */
  @Override
  public synchronized void retrieveAllReservations() {
    App.reservationdbExecutor.execute(() -> {
      String sql = "SELECT * FROM RESERVATION";
      try (Connection connection = DataManager.getConnection();
           PreparedStatement statement = connection.prepareStatement(sql);
           ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
          Reservation reservation = mapResultSetToReservation(resultSet);
          model.addReservation(reservation);
        }
      } catch (SQLException e) {
        handleSQLException(e);
      }
      System.err.println("Retrieved all reservations");
    });
  }

  /**
   * Gets a reservation for a specific client.
   *
   * @param clientId the ID of the client
   * @return the reservation for the client, or null if not found
   */
  @Override
  public synchronized Reservation getReservationForClient(int clientId) {
    String sql = "SELECT * FROM RESERVATION WHERE Client_Id = ?";
    try (Connection connection = DataManager.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, clientId);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return mapResultSetToReservation(resultSet);
        }
      }
    } catch (SQLException e) {
      handleSQLException(e);
    }
    System.err.println("Retrieved reservation and payment by client ID: " + clientId);
    return null;
  }

  /**
   * Gets a list of reservations that need to be dropped by a specific date.
   *
   * @param date the date to check for overdue reservations
   * @return the list of reservations to drop
   */
  @Override
  public synchronized List<Reservation> getReservationsToDrop(LocalDate date) {
    List<Reservation> reservationsToDrop = new ArrayList<>();
    String sql = "SELECT * FROM RESERVATION WHERE Return_date <= ?";
    try (Connection connection = DataManager.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setDate(1, java.sql.Date.valueOf(date));
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          Reservation reservation = mapResultSetToReservation(resultSet);
          reservationsToDrop.add(reservation);
        }
      }
    } catch (SQLException e) {
      handleSQLException(e);
    }
    return reservationsToDrop;
  }

  /**
   * Sets the parameters for a reservation in a prepared statement.
   *
   * @param statement the prepared statement
   * @param reservation the reservation to set the parameters for
   * @throws SQLException if a database access error occurs
   */
  private void setReservationStatementParams(PreparedStatement statement, Reservation reservation) throws SQLException {
    statement.setInt(1, reservation.getClientId());
    statement.setInt(2, reservation.getVehicleId());
    statement.setDouble(3, reservation.getTotalRate());
    statement.setString(4, reservation.getLicensePlate());
    statement.setString(5, reservation.getLicenseNo());
    statement.setDate(6, java.sql.Date.valueOf(reservation.getStartDate()));
    statement.setDate(7, java.sql.Date.valueOf(reservation.getReturnDate()));
    statement.setString(8, reservation.getInsuranceType());
  }

  /**
   * Sets the parameters for a payment in a prepared statement.
   *
   * @param statement the prepared statement
   * @param payment the payment to set the parameters for
   * @throws SQLException if a database access error occurs
   */
  private void setPaymentStatementParams(PreparedStatement statement, CardPayment payment) throws SQLException {
    statement.setInt(1, payment.getRentalId());
    statement.setInt(2, payment.getClientId());
    statement.setDate(3, java.sql.Date.valueOf(payment.getPaymentDate()));
    statement.setDouble(4, payment.getAmount());
    statement.setString(5, payment.getPaymentType());
  }

  /**
   * Maps a result set to a reservation object.
   *
   * @param resultSet the result set to map
   * @return the reservation object
   * @throws SQLException if a database access error occurs
   */
  private Reservation mapResultSetToReservation(ResultSet resultSet) throws SQLException {
    Reservation reservation = new Reservation();
    reservation.setReservationId(resultSet.getInt("Rental_Id"));
    reservation.setClientId(resultSet.getInt("Client_Id"));
    reservation.setVehicleId(resultSet.getInt("Vehicle_Id"));
    reservation.setTotalRate(resultSet.getDouble("Total_rate"));
    reservation.setLicensePlate(resultSet.getString("Vehicle_reg"));
    reservation.setLicenseNo(resultSet.getString("License_no"));
    reservation.setStartDate(resultSet.getDate("Start_date").toLocalDate());
    reservation.setReturnDate(resultSet.getDate("Return_date").toLocalDate());
    reservation.setInsuranceType(resultSet.getString("Insurance_type"));
    return reservation;
  }

  /**
   * Handles SQL exceptions by printing the error message.
   *
   * @param e the SQL exception to handle
   */
  private void handleSQLException(SQLException e) {
    System.err.println("Database error: " + e.getMessage());
  }
}
