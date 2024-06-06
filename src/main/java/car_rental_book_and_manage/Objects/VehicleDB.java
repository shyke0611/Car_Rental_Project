package car_rental_book_and_manage.Objects;

import car_rental_book_and_manage.App;
import car_rental_book_and_manage.Utility.DataManager;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Class for managing vehicle database operations. */
public class VehicleDB implements VehicleDAO {

  private static final DataModel model = DataModel.getInstance();

  /**
   * Saves a vehicle to the database.
   *
   * @param vehicle the vehicle to save
   */
  @Override
  public synchronized void saveVehicle(Vehicle vehicle) {
    App.vehicledbExecutor.execute(
        () -> {
          String query =
              "INSERT INTO VEHICLE (Brand, Model, Make_year, Reg_no, Colour, Fuel_option,"
                  + " Fuel_economy, Daily_rate, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
          try (Connection connection = DataManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
              setVehicleStatementParams(statement, vehicle);
              statement.executeUpdate();
              connection.commit();
              retrieveLatestVehicleToSave();
              model.setNumOfVehicles(String.valueOf(getNumOfVehicles()));
              System.out.println("Vehicle saved");
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
   * Updates a vehicle in the database.
   *
   * @param vehicle the vehicle to update
   */
  @Override
  public synchronized void updateVehicle(Vehicle vehicle) {
    App.vehicledbExecutor.execute(
        () -> {
          String query =
              "UPDATE VEHICLE SET Brand = ?, Model = ?, Make_year = ?, Reg_no = ?, Colour = ?,"
                  + " Fuel_option = ?, Fuel_economy = ?, Daily_rate = ?, image_path = ? WHERE V_Id"
                  + " = ?";
          try (Connection connection = DataManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
              setVehicleStatementParams(statement, vehicle);
              statement.setInt(10, vehicle.getVehicleId());
              statement.executeUpdate();
              connection.commit();
              retrieveVehicleByIdToUpdate(vehicle.getVehicleId());
              System.out.println("Vehicle updated");
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
   * Deletes a vehicle from the database.
   *
   * @param vehicle the vehicle to delete
   */
  @Override
  public synchronized void deleteVehicle(Vehicle vehicle) {
    App.vehicledbExecutor.execute(
        () -> {
          String query = "DELETE FROM VEHICLE WHERE V_Id = ?";
          try (Connection connection = DataManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
              statement.setInt(1, vehicle.getVehicleId());
              statement.executeUpdate();
              connection.commit();
              model.removeVehicle(vehicle);
              model.setNumOfVehicles(String.valueOf(getNumOfVehicles()));
              System.out.println("Vehicle deleted");
            } catch (SQLException e) {
              connection.rollback();
              handleSQLException(e);
            }
          } catch (SQLException e) {
            handleSQLException(e);
          }
        });
  }

  /** Retrieves all vehicles from the database and adds them to the model. */
  @Override
  public synchronized void retrieveAllVehicles() {
    App.vehicledbExecutor.execute(
        () -> {
          String sql = "SELECT * FROM VEHICLE";
          try (Connection connection = DataManager.getConnection();
              PreparedStatement statement = connection.prepareStatement(sql);
              ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
              Vehicle vehicle = mapResultSetToVehicle(resultSet);
              model.addVehicle(vehicle);
            }
          } catch (SQLException e) {
            handleSQLException(e);
          }
          System.err.println("Retrieved all vehicles");
        });
  }

  /** Retrieves the latest saved vehicle from the database and adds it to the model. */
  @Override
  public synchronized void retrieveLatestVehicleToSave() {
    App.vehicledbExecutor.execute(
        () -> {
          String sql = "SELECT * FROM VEHICLE ORDER BY V_Id DESC LIMIT 1";
          try (Connection connection = DataManager.getConnection();
              PreparedStatement statement = connection.prepareStatement(sql);
              ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
              Vehicle vehicle = mapResultSetToVehicle(resultSet);
              model.addVehicle(vehicle);
            }
          } catch (SQLException e) {
            handleSQLException(e);
          }
          System.err.println("Retrieved latest vehicle");
        });
  }

  /**
   * Retrieves a vehicle by its ID and updates it in the model.
   *
   * @param vehicleId the ID of the vehicle to retrieve
   */
  @Override
  public synchronized void retrieveVehicleByIdToUpdate(int vehicleId) {
    App.vehicledbExecutor.execute(
        () -> {
          String sql = "SELECT * FROM VEHICLE WHERE V_Id = ?";
          try (Connection connection = DataManager.getConnection();
              PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vehicleId);
            try (ResultSet resultSet = statement.executeQuery()) {
              if (resultSet.next()) {
                Vehicle vehicle = mapResultSetToVehicle(resultSet);
                model.updateVehicle(vehicle);
              }
            }
          } catch (SQLException e) {
            handleSQLException(e);
          }
          System.err.println("Retrieved vehicle by ID: " + vehicleId);
        });
  }

  /**
   * Gets a vehicle by its ID.
   *
   * @param vehicleId the ID of the vehicle to retrieve
   * @return the vehicle with the specified ID, or null if not found
   */
  @Override
  public synchronized Vehicle getVehicleById(int vehicleId) {
    String sql = "SELECT * FROM VEHICLE WHERE V_Id = ?";
    try (Connection connection = DataManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, vehicleId);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return mapResultSetToVehicle(resultSet);
        }
      }
    } catch (SQLException e) {
      handleSQLException(e);
    }
    return null;
  }

  /**
   * Sets the availability of a vehicle.
   *
   * @param vehicleId the ID of the vehicle
   * @param availability the availability status to set
   */
  @Override
  public synchronized void setVehicleAvailability(int vehicleId, boolean availability) {
    App.vehicledbExecutor.execute(
        () -> {
          String query = "UPDATE VEHICLE SET Availability = ? WHERE V_Id = ?";
          try (Connection connection = DataManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
              statement.setBoolean(1, availability);
              statement.setInt(2, vehicleId);
              statement.executeUpdate();
              connection.commit();

              Vehicle vehicle = model.getVehicle(vehicleId);
              if (vehicle != null) {
                vehicle.setAvailability(availability);
                model.updateVehicle(vehicle);
              }

              System.out.println("Vehicle availability updated");
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
   * Checks if a vehicle with the given registration number exists.
   *
   * @param regNo the registration number to check
   * @return true if the registration number exists, false otherwise
   */
  @Override
  public synchronized boolean doesRegistrationNoExist(String regNo) {
    String sql = "SELECT COUNT(*) AS count FROM VEHICLE WHERE Reg_no = ?";
    try (Connection connection = DataManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, regNo);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          int countOfExistingRegNo = resultSet.getInt("count");
          return countOfExistingRegNo > 0;
        }
      }
    } catch (SQLException e) {
      handleSQLException(e);
    }
    return false;
  }

  /**
   * Gets the total number of vehicles in the database.
   *
   * @return the total number of vehicles
   */
  @Override
  public synchronized int getNumOfVehicles() {
    int totalVehicles = 0;
    String sql = "SELECT COUNT(*) AS totalVehicles FROM VEHICLE";
    try (Connection connection = DataManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
      if (resultSet.next()) {
        totalVehicles = resultSet.getInt("totalVehicles");
      }
    } catch (SQLException e) {
      handleSQLException(e);
    }
    return totalVehicles;
  }

  /**
   * Sets the parameters for a vehicle in a prepared statement.
   *
   * @param statement the prepared statement
   * @param vehicle the vehicle to set the parameters for
   * @throws SQLException if a database access error occurs
   */
  private void setVehicleStatementParams(PreparedStatement statement, Vehicle vehicle)
      throws SQLException {
    statement.setString(1, vehicle.getBrand());
    statement.setString(2, vehicle.getModel());
    statement.setInt(3, vehicle.getMakeYear());
    statement.setString(4, vehicle.getLicensePlate());
    statement.setString(5, vehicle.getColour());
    statement.setString(6, vehicle.getFuelType());
    statement.setString(7, vehicle.getEconomy());
    statement.setBigDecimal(8, vehicle.getPricePerDay());
    statement.setString(9, vehicle.getImage());
  }

  /**
   * Maps a result set to a vehicle object.
   *
   * @param resultSet the result set to map
   * @return the vehicle object
   * @throws SQLException if a database access error occurs
   */
  private Vehicle mapResultSetToVehicle(ResultSet resultSet) throws SQLException {
    BigDecimal dailyRate = resultSet.getBigDecimal("Daily_rate").setScale(2, RoundingMode.HALF_UP);
    Vehicle vehicle = new Vehicle();
    vehicle.setVehicleId(resultSet.getInt("V_Id"));
    vehicle.setBrand(resultSet.getString("Brand"));
    vehicle.setModel(resultSet.getString("Model"));
    vehicle.setMakeYear(resultSet.getInt("Make_year"));
    vehicle.setLicensePlate(resultSet.getString("Reg_no"));
    vehicle.setColour(resultSet.getString("Colour"));
    vehicle.setFuelType(resultSet.getString("Fuel_option"));
    vehicle.setEconomy(resultSet.getString("Fuel_economy"));
    vehicle.setPricePerDay(dailyRate);
    vehicle.setAvailability(resultSet.getBoolean("Availability"));
    vehicle.setImage(resultSet.getString("image_path"));
    return vehicle;
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
