package car_rental_book_and_manage.Server.RestControllers;

import static spark.Spark.*;

import car_rental_book_and_manage.Server.Data.DataModel;
import car_rental_book_and_manage.Server.Payment.CardPayment;
import car_rental_book_and_manage.Server.Reservation.Reservation;
import car_rental_book_and_manage.Server.Reservation.ReservationDB;

import com.fasterxml.jackson.databind.ObjectMapper;

/** REST controller for handling reservation-related HTTP requests. */
public class ReservationController {
  private ReservationDB reservationDB = new ReservationDB();
  private ObjectMapper objectMapper = new ObjectMapper();
  private static final DataModel model = DataModel.getInstance();

  /** Constructor to set up the reservation-related endpoints. */
  public ReservationController() {
    setupEndpoints();
  }

  /** Sets up the Spark endpoints for reservation-related API requests. */
  private void setupEndpoints() {
    // Handles GET requests for retrieving reservations
    get(
        "/api/reservations",
        (req, res) -> {
          String idParam = req.queryParams("id");
          if (idParam != null) {
            int id = Integer.parseInt(idParam);
            Reservation reservation = model.getReservation(id);
            if (reservation != null) {
              return objectMapper.writeValueAsString(reservation);
            } else {
              res.status(404);
              return "";
            }
          } else {
            reservationDB.retrieveAllReservations();
            return objectMapper.writeValueAsString(model.getReservationList());
          }
        });

    // Handles POST requests for saving a new reservation and payment
    post(
        "/api/reservations",
        (req, res) -> {
          Reservation reservation = objectMapper.readValue(req.body(), Reservation.class);
          CardPayment payment = objectMapper.readValue(req.body(), CardPayment.class);
          reservationDB.saveReservationAndPayment(reservation, payment);
          res.status(201);
          return "Reservation saved successfully";
        });
  }
}
