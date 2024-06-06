package car_rental_book_and_manage.Objects;

import java.time.LocalDate;
import java.util.List;

import car_rental_book_and_manage.Payment.CardPayment;

/** Interface for reservation data access operations. */
public interface ReservationDAO {

  /**
   * Saves a reservation to the database.
   *
   * @param reservation the reservation to save
   */
  void saveReservationAndPayment(Reservation reservation, CardPayment payment);

  /** Retrieves all reservations from the database and adds them to the model. */
  void retrieveAllReservations();

  /**
   * Gets a reservation for a specific client.
   *
   * @param clientId the ID of the client
   * @return the reservation for the client, or null if not found
   */
  Reservation getReservationForClient(int clientId);

  /**
   * Gets a list of reservations that need to be dropped by a specific date.
   *
   * @param date the date to check for overdue reservations
   * @return the list of reservations to drop
   */
  List<Reservation> getReservationsToDrop(LocalDate date);

  /**
   * Deletes a reservation from the database.
   *
   * @param reservationId the ID of the reservation to delete
   */
  void deleteReservationAndPayment(int reservationId);

  /** Retrieves the latest saved reservation from the database and adds it to the model. */
  void retrieveLatestReservationToSave();
}
