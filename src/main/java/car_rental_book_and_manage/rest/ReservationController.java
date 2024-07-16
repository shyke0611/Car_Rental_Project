package car_rental_book_and_manage.rest;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import car_rental_book_and_manage.Objects.Reservation;
import car_rental_book_and_manage.Objects.ReservationDB;
import car_rental_book_and_manage.Payment.CardPayment;
import car_rental_book_and_manage.Objects.DataModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * REST controller for handling reservation-related HTTP requests.
 */
public class ReservationController implements HttpHandler {
    private ReservationDB reservationDB = new ReservationDB();
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final DataModel model = DataModel.getInstance();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                handleGet(exchange);
                break;
            case "POST":
                handlePost(exchange);
                break;
            default:
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                break;
        }
    }

    /**
     * Handles HTTP GET requests for retrieving reservation information.
     *
     * @param exchange the HTTP exchange containing the request and response
     * @throws IOException if an I/O error occurs
     */
    private void handleGet(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.contains("id=")) {
            int id = Integer.parseInt(query.split("=")[1]);
            Reservation reservation = reservationDB.getReservationById(id);
            if (reservation != null) {
                String jsonResponse = objectMapper.writeValueAsString(reservation);
                exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(404, -1); // Not Found
            }
        } else {
            reservationDB.retrieveAllReservations(); // Populate the model with reservations
            List<Reservation> reservations = model.getReservationList(); // Get reservations from the model
            String jsonResponse = objectMapper.writeValueAsString(reservations);
            exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(jsonResponse.getBytes());
            os.close();
        }
    }

    /**
     * Handles HTTP POST requests for saving reservation and payment information.
     *
     * @param exchange the HTTP exchange containing the request and response
     * @throws IOException if an I/O error occurs
     */
    private void handlePost(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        Reservation reservation = objectMapper.readValue(requestBody, Reservation.class);
        CardPayment payment = objectMapper.readValue(requestBody, CardPayment.class);
        reservationDB.saveReservationAndPayment(reservation, payment);
        String response = "Reservation saved successfully";
        exchange.sendResponseHeaders(201, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
