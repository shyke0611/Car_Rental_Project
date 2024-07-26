package car_rental_book_and_manage.Server.RestControllers;

import car_rental_book_and_manage.Server.DAO.VehicleDB;
import car_rental_book_and_manage.Server.ServerUtility.ValidationManager;
import car_rental_book_and_manage.SharedObject.Data.DataModel;
import car_rental_book_and_manage.SharedObject.Vehicle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * VehicleController handles vehicle-related HTTP requests. It provides endpoints for retrieving,
 * saving, updating, and deleting vehicles.
 */
@Path("/vehicles")
public class VehicleController {
  private VehicleDB vehicleDB = new VehicleDB();
  private ObjectMapper objectMapper = new ObjectMapper();
  private static final DataModel model = DataModel.getInstance();

  /**
   * Handles HTTP GET requests for retrieving vehicles.
   *
   * @param id Optional query parameter to retrieve a specific vehicle by ID.
   * @return Response containing the vehicle(s) in JSON format.
   * @throws Exception if an error occurs during processing.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getVehicles(@QueryParam("id") Integer id) throws Exception {
    if (id != null) {
      Vehicle vehicle = model.getVehicle(id);
      if (vehicle != null) {
        return Response.ok(objectMapper.writeValueAsString(vehicle)).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } else {
      vehicleDB.retrieveAllVehicles();
      return Response.ok(objectMapper.writeValueAsString(model.getVehicleList())).build();
    }
  }

  /**
   * Handles HTTP GET requests for retrieving vehicles by ID.
   *
   * @param id the ID of the vehicle to retrieve.
   * @return Response containing the vehicle in JSON format.
   * @throws Exception if an error occurs during processing.
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getVehicleById(@PathParam("id") int id) throws Exception {
    Vehicle vehicle = vehicleDB.getVehicleById(id);
    if (vehicle != null) {
      return Response.ok(objectMapper.writeValueAsString(vehicle)).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Vehicle not found for ID: " + id)
          .build();
    }
  }

  /**
   * Handles HTTP GET requests for retrieving the count of vehicles.
   *
   * @return Response containing the count of vehicles in JSON format.
   * @throws JsonProcessingException if an error occurs during JSON processing.
   */
  @GET
  @Path("/count")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getVehicleCount() throws Exception {
    int count = vehicleDB.getNumOfVehicles();
    return Response.ok(objectMapper.writeValueAsString(count)).build();
  }

  /**
   * Handles HTTP POST requests for saving a new vehicle.
   *
   * @param requestBody JSON payload containing the vehicle information.
   * @return Response indicating the outcome of the save operation.
   * @throws Exception if an error occurs during processing.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response saveVehicle(String requestBody) throws Exception {
    return handleSaveOrUpdateVehicle(requestBody, "save");
  }

  /**
   * Handles HTTP PUT requests for updating an existing vehicle.
   *
   * @param requestBody JSON payload containing the updated vehicle information.
   * @return Response indicating the outcome of the update operation.
   * @throws Exception if an error occurs during processing.
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateVehicle(String requestBody) throws Exception {
    return handleSaveOrUpdateVehicle(requestBody, "update");
  }

  /**
   * Handles HTTP DELETE requests for removing a vehicle.
   *
   * @param requestBody JSON payload containing the vehicle information.
   * @return Response indicating the outcome of the delete operation.
   * @throws Exception if an error occurs during processing.
   */
  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteVehicle(String requestBody) throws Exception {
    Vehicle vehicle = objectMapper.readValue(requestBody, Vehicle.class);
    vehicleDB.deleteVehicle(vehicle);
    return Response.ok("Vehicle deleted successfully").build();
  }

  /**
   * Handles HTTP PUT requests for updating the availability of a vehicle.
   *
   * @param id the ID of the vehicle to update.
   * @param available the new availability status.
   * @return Response indicating the outcome of the update operation.
   * @throws Exception if an error occurs during processing.
   */
  @PUT
  @Path("/{id}/availability")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateVehicleAvailability(
      @PathParam("id") int id, @QueryParam("available") boolean available) throws Exception {
    Vehicle vehicle = vehicleDB.getVehicleById(id);
    if (vehicle != null) {
      vehicleDB.setVehicleAvailability(id, available);
      return Response.ok("Vehicle availability updated successfully").build();
    } else {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Vehicle not found for ID: " + id)
          .build();
    }
  }

  /**
   * Handles the save or update operation for a vehicle.
   *
   * @param requestBody JSON payload containing the vehicle information.
   * @param action the action being performed ("save" or "update").
   * @return Response indicating the outcome of the operation.
   * @throws Exception if an error occurs during processing.
   */
  private Response handleSaveOrUpdateVehicle(String requestBody, String action) throws Exception {
    Vehicle vehicle = objectMapper.readValue(requestBody, Vehicle.class);
    Map<String, String> errors =
        ValidationManager.validateVehicleFields(
            vehicle.getBrand(),
            vehicle.getModel(),
            String.valueOf(vehicle.getMakeYear()),
            vehicle.getColour(),
            String.valueOf(vehicle.getPricePerDay()),
            vehicle.getLicensePlate(),
            vehicle.getFuelType(),
            vehicle.getEconomy());

    // Check for existing registration number
    if (errors.isEmpty()) {
      errors.putAll(
          ValidationManager.checkRegistrationNoExists(
              vehicle.getLicensePlate(),
              action,
              vehicleDB,
              action.equals("update") ? String.valueOf(vehicle.getVehicleId()) : null));
    }

    if (!errors.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(objectMapper.writeValueAsString(errors))
          .build();
    }

    if ("save".equals(action)) {
      vehicleDB.saveVehicle(vehicle);
      return Response.status(Response.Status.CREATED).entity("Vehicle saved successfully").build();
    } else if ("update".equals(action)) {
      vehicleDB.updateVehicle(vehicle);
      return Response.ok("Vehicle updated successfully").build();
    } else {
      return Response.status(Response.Status.BAD_REQUEST).entity("Invalid action").build();
    }
  }
}
