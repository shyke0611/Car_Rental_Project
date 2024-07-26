package car_rental_book_and_manage.Server.RestControllers;

import car_rental_book_and_manage.Server.DAO.ReservationDB;
import car_rental_book_and_manage.Server.ServerUtility.ValidationManager;
import car_rental_book_and_manage.SharedObject.Data.DataModel;
import car_rental_book_and_manage.SharedObject.Payment.CardPayment;
import car_rental_book_and_manage.SharedObject.Reservation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * ReservationController handles reservation-related HTTP requests. It provides endpoints for
 * retrieving, saving, and updating reservations.
 */
@Path("/reservations")
public class ReservationController {

  private ReservationDB reservationDB = new ReservationDB();
  private ObjectMapper objectMapper;

  private static final DataModel model = DataModel.getInstance();

  public ReservationController() {
    this.objectMapper = new ObjectMapper();
    this.objectMapper.registerModule(new JavaTimeModule());
  }

  /**
   * Handles HTTP GET requests for retrieving reservations.
   *
   * @param id Optional query parameter to retrieve a specific reservation by ID.
   * @return Response containing the reservation(s) in JSON format.
   * @throws Exception if an error occurs during processing.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getReservations(@QueryParam("id") Integer id) throws Exception {
    if (id != null) {
      Reservation reservation = model.getReservation(id);
      if (reservation != null) {
        return Response.ok(objectMapper.writeValueAsString(reservation)).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } else {
      reservationDB.retrieveAllReservations();
      return Response.ok(objectMapper.writeValueAsString(model.getReservationList())).build();
    }
  }

  /**
   * Handles HTTP POST requests for saving a reservation and payment.
   *
   * @param requestBody JSON payload containing the reservation and payment data.
   * @return Response indicating the outcome of the save operation.
   * @throws Exception if an error occurs during processing.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response saveReservationAndPayment(String requestBody) {
    try {
      Map<String, Object> requestData =
          objectMapper.readValue(requestBody, new TypeReference<Map<String, Object>>() {});
      Reservation reservation =
          objectMapper.convertValue(requestData.get("reservation"), Reservation.class);
      CardPayment payment =
          objectMapper.convertValue(requestData.get("payment"), CardPayment.class);

      Map<String, String> errors =
          ValidationManager.validateCardDetails(
              payment.getCardHolderName(),
              payment.getCardNumber(),
              payment.getCvv(),
              payment.getExpiryDate());

      if (!errors.isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(objectMapper.writeValueAsString(errors))
            .build();
      }

      reservationDB.saveReservationAndPayment(reservation, payment);
      return Response.status(Response.Status.CREATED)
          .entity("Reservation and payment saved successfully")
          .build();

    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("Internal server error")
          .build();
    }
  }

  /**
   * Handles HTTP GET requests for retrieving a reservation for a specific client.
   *
   * @param clientId the ID of the client whose reservation is to be retrieved.
   * @return Response containing the reservation in JSON format.
   * @throws Exception if an error occurs during processing.
   */
  @GET
  @Path("/client/{clientId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getReservationForClient(@PathParam("clientId") int clientId) throws Exception {
    Reservation reservation = reservationDB.getReservationForClient(clientId);
    if (reservation != null) {
      return Response.ok(objectMapper.writeValueAsString(reservation)).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("No reservation found for client ID: " + clientId)
          .build();
    }
  }
}
