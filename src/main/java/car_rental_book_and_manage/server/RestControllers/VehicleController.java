package car_rental_book_and_manage.Server.RestControllers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import car_rental_book_and_manage.Server.DAO.VehicleDB;
import car_rental_book_and_manage.Server.Data.DataModel;
import car_rental_book_and_manage.SharedObject.Vehicle;

/**
 * VehicleController handles vehicle-related HTTP requests.
 * It provides endpoints for retrieving, saving, updating, and deleting vehicles.
 */
@Path("/vehicles")
public class VehicleController {
    private VehicleDB vehicleDB = new VehicleDB();
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final DataModel model = DataModel.getInstance();

    /**
     * Handles HTTP GET requests for retrieving vehicles.
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
     * Handles HTTP POST requests for saving a new vehicle.
     * @param requestBody JSON payload containing the vehicle information.
     * @return Response indicating the outcome of the save operation.
     * @throws Exception if an error occurs during processing.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveVehicle(String requestBody) throws Exception {
        Vehicle vehicle = objectMapper.readValue(requestBody, Vehicle.class);
        vehicleDB.saveVehicle(vehicle);
        return Response.status(Response.Status.CREATED).entity("Vehicle saved successfully").build();
    }

    /**
     * Handles HTTP PUT requests for updating an existing vehicle.
     * @param requestBody JSON payload containing the updated vehicle information.
     * @return Response indicating the outcome of the update operation.
     * @throws Exception if an error occurs during processing.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateVehicle(String requestBody) throws Exception {
        Vehicle vehicle = objectMapper.readValue(requestBody, Vehicle.class);
        vehicleDB.updateVehicle(vehicle);
        return Response.ok("Vehicle updated successfully").build();
    }

    /**
     * Handles HTTP DELETE requests for removing a vehicle.
     * @param requestBody JSON payload containing the vehicle information to be deleted.
     * @return Response indicating the outcome of the delete operation.
     * @throws Exception if an error occurs during processing.
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteVehicle(String requestBody) throws Exception {
        Vehicle vehicle = objectMapper.readValue(requestBody, Vehicle.class);
        vehicleDB.deleteVehicle(vehicle);
        return Response.ok("Vehicle deleted successfully").build();
    }
}
