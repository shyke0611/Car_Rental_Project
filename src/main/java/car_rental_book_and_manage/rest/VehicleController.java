package car_rental_book_and_manage.rest;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import car_rental_book_and_manage.Objects.DataModel;
import car_rental_book_and_manage.Objects.Vehicle;
import car_rental_book_and_manage.Objects.VehicleDB;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * REST controller for handling vehicle-related HTTP requests.
 */
public class VehicleController implements HttpHandler {
    private VehicleDB vehicleDB = new VehicleDB();
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
            case "PUT":
                handlePut(exchange);
                break;
            case "DELETE":
                handleDelete(exchange);
                break;
            default:
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                break;
        }
    }

    /**
     * Handles HTTP GET requests for retrieving vehicle information.
     *
     * @param exchange the HTTP exchange containing the request and response
     * @throws IOException if an I/O error occurs
     */
    private void handleGet(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.contains("id=")) {
            int id = Integer.parseInt(query.split("=")[1]);
            Vehicle vehicle = vehicleDB.getVehicleById(id);
            if (vehicle != null) {
                String jsonResponse = objectMapper.writeValueAsString(vehicle);
                exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(404, -1); // Not Found
            }
        } else {
            vehicleDB.retrieveAllVehicles(); // Populate the model with vehicles
            List<Vehicle> vehicles = model.getVehicleList(); // Get vehicles from the model
            String jsonResponse = objectMapper.writeValueAsString(vehicles);
            exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(jsonResponse.getBytes());
            os.close();
        }
    }

    /**
     * Handles HTTP POST requests for saving vehicle information.
     *
     * @param exchange the HTTP exchange containing the request and response
     * @throws IOException if an I/O error occurs
     */
    private void handlePost(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        Vehicle vehicle = objectMapper.readValue(requestBody, Vehicle.class);
        vehicleDB.saveVehicle(vehicle);
        String response = "Vehicle saved successfully";
        exchange.sendResponseHeaders(201, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /**
     * Handles HTTP PUT requests for updating vehicle information.
     *
     * @param exchange the HTTP exchange containing the request and response
     * @throws IOException if an I/O error occurs
     */
    private void handlePut(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        Vehicle vehicle = objectMapper.readValue(requestBody, Vehicle.class);
        vehicleDB.updateVehicle(vehicle);
        String response = "Vehicle updated successfully";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /**
     * Handles HTTP DELETE requests for deleting vehicle information.
     *
     * @param exchange the HTTP exchange containing the request and response
     * @throws IOException if an I/O error occurs
     */
    private void handleDelete(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        Vehicle vehicle = objectMapper.readValue(requestBody, Vehicle.class);
        vehicleDB.deleteVehicle(vehicle);
        String response = "Vehicle deleted successfully";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
