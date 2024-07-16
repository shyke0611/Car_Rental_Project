package car_rental_book_and_manage.rest;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import car_rental_book_and_manage.Objects.Client;
import car_rental_book_and_manage.Objects.ClientDB;
import car_rental_book_and_manage.Objects.DataModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * REST controller for handling client-related HTTP requests.
 */
public class ClientController implements HttpHandler {
    private ClientDB clientDB = new ClientDB();
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
            default:
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                break;
        }
    }

    /**
     * Handles HTTP GET requests for retrieving client information.
     *
     * @param exchange the HTTP exchange containing the request and response
     * @throws IOException if an I/O error occurs
     */
    private void handleGet(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.contains("id=")) {
            int id = Integer.parseInt(query.split("=")[1]);
            Client client = clientDB.getClientById(id);
            if (client != null) {
                String jsonResponse = objectMapper.writeValueAsString(client);
                exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(404, -1); // Not Found
            }
        } else {
            clientDB.retrieveAllClients(); // Populate the model with clients
            List<Client> clients = model.getClientList(); // Get clients from the model
            String jsonResponse = objectMapper.writeValueAsString(clients);
            exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(jsonResponse.getBytes());
            os.close();
        }
    }

    /**
     * Handles HTTP POST requests for saving client information.
     *
     * @param exchange the HTTP exchange containing the request and response
     * @throws IOException if an I/O error occurs
     */
    private void handlePost(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        Client client = objectMapper.readValue(requestBody, Client.class);
        clientDB.saveClient(client);
        String response = "Client saved successfully";
        exchange.sendResponseHeaders(201, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /**
     * Handles HTTP PUT requests for updating client information.
     *
     * @param exchange the HTTP exchange containing the request and response
     * @throws IOException if an I/O error occurs
     */
    private void handlePut(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        Client client = objectMapper.readValue(requestBody, Client.class);
        clientDB.updateClient(client);
        String response = "Client updated successfully";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
