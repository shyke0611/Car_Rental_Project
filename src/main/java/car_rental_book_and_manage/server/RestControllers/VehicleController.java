package car_rental_book_and_manage.Server.RestControllers;

import static spark.Spark.*;

import car_rental_book_and_manage.Server.Data.DataModel;
import car_rental_book_and_manage.Server.Vehicle.Vehicle;
import car_rental_book_and_manage.Server.Vehicle.VehicleDB;

import com.fasterxml.jackson.databind.ObjectMapper;

/** REST controller for handling vehicle-related HTTP requests. */
public class VehicleController {
  private VehicleDB vehicleDB = new VehicleDB();
  private ObjectMapper objectMapper = new ObjectMapper();
  private static final DataModel model = DataModel.getInstance();

  /** Constructor to set up the vehicle-related endpoints. */
  public VehicleController() {
    setupEndpoints();
  }

  /** Sets up the Spark endpoints for vehicle-related API requests. */
  private void setupEndpoints() {
    // Handles GET requests for retrieving vehicles
    get(
        "/api/vehicles",
        (req, res) -> {
          String idParam = req.queryParams("id");
          if (idParam != null) {
            int id = Integer.parseInt(idParam);
            Vehicle vehicle = model.getVehicle(id);
            if (vehicle != null) {
              return objectMapper.writeValueAsString(vehicle);
            } else {
              res.status(404);
              return "";
            }
          } else {
            vehicleDB.retrieveAllVehicles();
            return objectMapper.writeValueAsString(model.getVehicleList());
          }
        });

    // Handles POST requests for saving a new vehicle
    post(
        "/api/vehicles",
        (req, res) -> {
          Vehicle vehicle = objectMapper.readValue(req.body(), Vehicle.class);
          vehicleDB.saveVehicle(vehicle);
          res.status(201);
          return "Vehicle saved successfully";
        });

    // Handles PUT requests for updating an existing vehicle
    put(
        "/api/vehicles",
        (req, res) -> {
          Vehicle vehicle = objectMapper.readValue(req.body(), Vehicle.class);
          vehicleDB.updateVehicle(vehicle);
          return "Vehicle updated successfully";
        });

    // Handles DELETE requests for removing a vehicle
    delete(
        "/api/vehicles",
        (req, res) -> {
          Vehicle vehicle = objectMapper.readValue(req.body(), Vehicle.class);
          vehicleDB.deleteVehicle(vehicle);
          return "Vehicle deleted successfully";
        });
  }
}
