package car_rental_book_and_manage.Utility;

import car_rental_book_and_manage.Objects.Reservation;
import car_rental_book_and_manage.Objects.ReservationDB;
import car_rental_book_and_manage.Objects.VehicleDB;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** Class for scheduling and processing booking tasks. */
public class BookingScheduler {

  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private final ReservationDB reservationDB = new ReservationDB();
  private final VehicleDB vehicleDB = new VehicleDB();

  /** Starts the booking scheduler to check and drop bookings at a fixed rate. */
  public void start() {
    Runnable task = this::checkAndDropBookings;
    scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.DAYS);
  }

  /** Stops the booking scheduler. */
  public void stop() {
    scheduler.shutdown();
  }

  /** Checks and drops bookings based on the current date. */
  private void checkAndDropBookings() {
    LocalDate currentDate = LocalDate.now();
    processBookingsForDate(currentDate);
  }

  /** Processes overdue bookings and drops them if necessary. */
  public void processOverdueBookings() {
    LocalDate currentDate = LocalDate.now();
    processBookingsForDate(currentDate);
  }

  /**
   * Processes bookings for the specified date and drops them if necessary.
   *
   * @param date the date to process bookings for
   */
  private void processBookingsForDate(LocalDate date) {
    List<Reservation> reservationsToDrop = reservationDB.getReservationsToDrop(date);

    for (Reservation reservation : reservationsToDrop) {
      reservationDB.deleteReservationAndPayment(reservation.getReservationId());
      vehicleDB.setVehicleAvailability(reservation.getVehicleId(), true);
      System.out.println("Dropped reservation for client: " + reservation.getClientId());
    }
  }
}
