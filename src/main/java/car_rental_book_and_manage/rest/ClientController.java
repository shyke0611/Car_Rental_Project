package car_rental_book_and_manage.rest;

import static spark.Spark.*;

import car_rental_book_and_manage.Objects.Client;
import car_rental_book_and_manage.Objects.ClientDB;
import car_rental_book_and_manage.Objects.DataModel;
import com.fasterxml.jackson.databind.ObjectMapper;

/** REST controller for handling client-related HTTP requests. */
public class ClientController {
  private ClientDB clientDB = new ClientDB();
  private ObjectMapper objectMapper = new ObjectMapper();
  private static final DataModel model = DataModel.getInstance();

  /** Constructor to set up the client-related endpoints. */
  public ClientController() {
    setupEndpoints();
  }

  /** Sets up the Spark endpoints for client-related API requests. */
  private void setupEndpoints() {
    // Handles GET requests for retrieving clients
    get(
        "/api/clients",
        (req, res) -> {
          String idParam = req.queryParams("id");
          if (idParam != null) {
            int id = Integer.parseInt(idParam);
            Client client = model.getClient(id);
            if (client != null) {
              return objectMapper.writeValueAsString(client);
            } else {
              res.status(404);
              return "";
            }
          } else {
            clientDB.retrieveAllClients();
            return objectMapper.writeValueAsString(model.getClientList());
          }
        });

    // Handles POST requests for saving a new client
    post(
        "/api/clients",
        (req, res) -> {
          Client client = objectMapper.readValue(req.body(), Client.class);
          clientDB.saveClient(client);
          res.status(201);
          return "Client saved successfully";
        });

    // Handles PUT requests for updating an existing client
    put(
        "/api/clients",
        (req, res) -> {
          Client client = objectMapper.readValue(req.body(), Client.class);
          clientDB.updateClient(client);
          return "Client updated successfully";
        });
  }
}
