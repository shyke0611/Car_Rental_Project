package car_rental_book_and_manage.Objects;

/** Interface for vehicle data access operations. */
public interface VehicleDAO {

  /**
   * Saves a vehicle to the database.
   *
   * @param vehicle the vehicle to save
   */
  void saveVehicle(Vehicle vehicle);

  /**
   * Updates a vehicle in the database.
   *
   * @param vehicle the vehicle to update
   */
  void updateVehicle(Vehicle vehicle);

  /**
   * Deletes a vehicle from the database.
   *
   * @param vehicle the vehicle to delete
   */
  void deleteVehicle(Vehicle vehicle);

  /**
   * Checks if a vehicle with the given registration number exists.
   *
   * @param regNo the registration number to check
   * @return true if the registration number exists, false otherwise
   */
  boolean doesRegistrationNoExist(String regNo);

  /** Retrieves all vehicles from the database. */
  void retrieveAllVehicles();

  /**
   * Gets the total number of vehicles in the database.
   *
   * @return the total number of vehicles
   */
  int getNumOfVehicles();

  /**
   * Retrieves a vehicle by its ID and updates it in the model.
   *
   * @param vehicleId the ID of the vehicle to retrieve
   */
  void retrieveVehicleByIdToUpdate(int vehicleId);

  /** Retrieves the latest saved vehicle from the database and adds it to the model. */
  void retrieveLatestVehicleToSave();

  /**
   * Sets the availability of a vehicle.
   *
   * @param vehicleId the ID of the vehicle
   * @param availability the availability status to set
   */
  void setVehicleAvailability(int vehicleId, boolean availability);

  /**
   * Gets a vehicle by its ID.
   *
   * @param vehicleId the ID of the vehicle to retrieve
   * @return the vehicle with the specified ID, or null if not found
   */
  Vehicle getVehicleById(int vehicleId);
}
