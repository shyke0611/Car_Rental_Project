package car_rental_book_and_manage.Server.RestControllers;

import car_rental_book_and_manage.Server.DAO.ClientDB;
import car_rental_book_and_manage.Server.ServerUtility.PIIHashManager;
import car_rental_book_and_manage.Server.ServerUtility.ValidationManager;
import car_rental_book_and_manage.SharedObject.Client;
import car_rental_book_and_manage.SharedObject.Data.DataModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * ClientController handles client-related HTTP requests. It provides endpoints for retrieving,
 * saving, and updating clients.
 */
@Path("/clients")
public class ClientController {
  private ClientDB clientDB = new ClientDB();
  private ObjectMapper objectMapper = new ObjectMapper();
  private static final DataModel model = DataModel.getInstance();

  /**
   * Handles HTTP GET requests for retrieving clients.
   *
   * @param id Optional query parameter to retrieve a specific client by ID.
   * @return Response containing the client(s) in JSON format.
   * @throws Exception if an error occurs during processing.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getClients(@QueryParam("id") Integer id) throws Exception {
    if (id != null) {
      Client client = model.getClient(id);
      if (client != null) {
        return Response.ok(objectMapper.writeValueAsString(client)).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } else {
      clientDB.retrieveAllClients();
      return Response.ok(objectMapper.writeValueAsString(model.getClientList())).build();
    }
  }

  /**
   * Handles HTTP GET requests for retrieving the count of clients.
   *
   * @return Response containing the count of clients in JSON format.
   * @throws JsonProcessingException
   */
  @GET
  @Path("/count")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getClientCount() throws Exception {
    int count = clientDB.getNumOfClients();
    return Response.ok(objectMapper.writeValueAsString(count)).build();
  }

  /**
   * Handles HTTP POST requests for saving a new client.
   *
   * @param requestBody JSON payload containing the client information.
   * @return Response indicating the outcome of the save operation.
   * @throws Exception if an error occurs during processing.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response saveClient(String requestBody) throws Exception {
    Client client = objectMapper.readValue(requestBody, Client.class);
    Map<String, String> errors =
        ValidationManager.validateSignUpClientInput(
            client.getFirstName(),
            client.getPassword(),
            client.getUsername(),
            client.getPhoneNo(),
            client.getLicenseNo(),
            clientDB);

    if (!errors.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(objectMapper.writeValueAsString(errors))
          .build();
    }

    client.setPassword(PIIHashManager.hashPassword(client.getPassword()));
    clientDB.saveClient(client);
    return Response.status(Response.Status.CREATED).entity("Client saved successfully").build();
  }

  /**
   * Handles HTTP PUT requests for updating an existing client.
   *
   * @param requestBody JSON payload containing the updated client information.
   * @return Response indicating the outcome of the update operation.
   * @throws Exception if an error occurs during processing.
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateClient(String requestBody) throws Exception {
    Client client = objectMapper.readValue(requestBody, Client.class);
    Map<String, String> errors =
        ValidationManager.validateClientUpdateInput(client.getFirstName(), client.getPhoneNo());

    if (!errors.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(objectMapper.writeValueAsString(errors))
          .build();
    }

    clientDB.updateClient(client);
    return Response.ok(objectMapper.writeValueAsString(client)).build();
  }

  /**
   * Handles HTTP POST requests for validating login credentials.
   *
   * @param requestBody JSON payload containing the login credentials.
   * @return Response indicating the outcome of the login validation.
   * @throws Exception if an error occurs during processing.
   */
  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response validateLogin(String requestBody) throws Exception {
    Client loginRequest = objectMapper.readValue(requestBody, Client.class);
    Map<String, String> errors =
        ValidationManager.validateClientLoginInput(
            loginRequest.getUsername(), loginRequest.getPassword(), clientDB);

    if (!errors.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(objectMapper.writeValueAsString(errors))
          .build();
    }

    Client client = clientDB.getClient(loginRequest.getUsername());
    return Response.ok(objectMapper.writeValueAsString(client)).build();
  }
}
