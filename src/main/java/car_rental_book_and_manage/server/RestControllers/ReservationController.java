package car_rental_book_and_manage.Server.RestControllers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import car_rental_book_and_manage.Server.DAO.ReservationDB;
import car_rental_book_and_manage.Server.Data.DataModel;
import car_rental_book_and_manage.Server.Payment.CardPayment;
import car_rental_book_and_manage.SharedObject.Reservation;

/**
 * ReservationController handles reservation-related HTTP requests.
 * It provides endpoints for retrieving, saving, and updating reservations.
 */
@Path("/reservations")
public class ReservationController {
    private ReservationDB reservationDB = new ReservationDB();
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final DataModel model = DataModel.getInstance();

    /**
     * Handles HTTP GET requests for retrieving reservations.
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
     * Handles HTTP POST requests for saving a new reservation and payment.
     * @param requestBody JSON payload containing the reservation and payment information.
     * @return Response indicating the outcome of the save operation.
     * @throws Exception if an error occurs during processing.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveReservation(String requestBody) throws Exception {
        Reservation reservation = objectMapper.readValue(requestBody, Reservation.class);
        CardPayment payment = objectMapper.readValue(requestBody, CardPayment.class);
        reservationDB.saveReservationAndPayment(reservation, payment);
        return Response.status(Response.Status.CREATED).entity("Reservation saved successfully").build();
    }
}
