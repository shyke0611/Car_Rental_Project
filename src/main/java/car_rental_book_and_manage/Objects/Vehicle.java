package car_rental_book_and_manage.Objects;

/** Represents a vehicle in the car rental system. */
public class Vehicle {

  private String model, licensePlate, brand, fuelType, colour, imagePath;
  private double pricePerDay;
  private int vehicleId, makeYear;
  private boolean availability;

  /** Default constructor. */
  public Vehicle() {}

  /**
   * Constructs a vehicle with the specified details.
   *
   * @param vehicleId the ID of the vehicle
   * @param makeYear the make year of the vehicle
   * @param model the model of the vehicle
   * @param licensePlate the license plate of the vehicle
   * @param pricePerDay the daily rental price of the vehicle
   * @param brand the brand of the vehicle
   * @param fuelType the fuel type of the vehicle
   * @param colour the color of the vehicle
   * @param imagePath the path to the vehicle's image
   */
  public Vehicle(
      int vehicleId,
      int makeYear,
      String model,
      String licensePlate,
      double pricePerDay,
      String brand,
      String fuelType,
      String colour,
      String imagePath) {
    this.vehicleId = vehicleId;
    this.model = model;
    this.makeYear = makeYear;
    this.licensePlate = licensePlate;
    this.pricePerDay = pricePerDay;
    this.brand = brand;
    this.colour = colour;
    this.fuelType = fuelType;
    this.imagePath = imagePath;
  }

  /**
   * Constructs a vehicle with the specified details, without an ID.
   *
   * @param makeYear the make year of the vehicle
   * @param model the model of the vehicle
   * @param licensePlate the license plate of the vehicle
   * @param pricePerDay the daily rental price of the vehicle
   * @param brand the brand of the vehicle
   * @param fuelType the fuel type of the vehicle
   * @param colour the color of the vehicle
   * @param imagePath the path to the vehicle's image
   */
  public Vehicle(
      int makeYear,
      String model,
      String licensePlate,
      double pricePerDay,
      String brand,
      String fuelType,
      String colour,
      String imagePath) {
    this.model = model;
    this.makeYear = makeYear;
    this.licensePlate = licensePlate;
    this.pricePerDay = pricePerDay;
    this.brand = brand;
    this.colour = colour;
    this.fuelType = fuelType;
    this.imagePath = imagePath;
  }

  /**
   * Gets the model of the vehicle.
   *
   * @return the model of the vehicle
   */
  public String getModel() {
    return model;
  }

  /**
   * Sets the model of the vehicle.
   *
   * @param model the model of the vehicle
   */
  public void setModel(String model) {
    this.model = model;
  }

  /**
   * Gets the license plate of the vehicle.
   *
   * @return the license plate of the vehicle
   */
  public String getLicensePlate() {
    return licensePlate;
  }

  /**
   * Sets the license plate of the vehicle.
   *
   * @param licensePlate the license plate of the vehicle
   */
  public void setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
  }

  /**
   * Gets the daily rental price of the vehicle.
   *
   * @return the daily rental price of the vehicle
   */
  public double getPricePerDay() {
    return pricePerDay;
  }

  /**
   * Sets the daily rental price of the vehicle.
   *
   * @param pricePerDay the daily rental price of the vehicle
   */
  public void setPricePerDay(double pricePerDay) {
    this.pricePerDay = pricePerDay;
  }

  /**
   * Gets the brand of the vehicle.
   *
   * @return the brand of the vehicle
   */
  public String getBrand() {
    return brand;
  }

  /**
   * Sets the brand of the vehicle.
   *
   * @param brand the brand of the vehicle
   */
  public void setBrand(String brand) {
    this.brand = brand;
  }

  /**
   * Gets the ID of the vehicle.
   *
   * @return the ID of the vehicle
   */
  public int getVehicleId() {
    return vehicleId;
  }

  /**
   * Sets the ID of the vehicle.
   *
   * @param vehicleId the ID of the vehicle
   */
  public void setVehicleId(int vehicleId) {
    this.vehicleId = vehicleId;
  }

  /**
   * Gets the make year of the vehicle.
   *
   * @return the make year of the vehicle
   */
  public int getMakeYear() {
    return makeYear;
  }

  /**
   * Sets the make year of the vehicle.
   *
   * @param makeYear the make year of the vehicle
   */
  public void setMakeYear(int makeYear) {
    this.makeYear = makeYear;
  }

  /**
   * Gets the fuel type of the vehicle.
   *
   * @return the fuel type of the vehicle
   */
  public String getFuelType() {
    return fuelType;
  }

  /**
   * Sets the fuel type of the vehicle.
   *
   * @param fuelType the fuel type of the vehicle
   */
  public void setFuelType(String fuelType) {
    this.fuelType = fuelType;
  }

  /**
   * Gets the color of the vehicle.
   *
   * @return the color of the vehicle
   */
  public String getColour() {
    return colour;
  }

  /**
   * Sets the color of the vehicle.
   *
   * @param colour the color of the vehicle
   */
  public void setColour(String colour) {
    this.colour = colour;
  }

  /**
   * Gets the availability status of the vehicle.
   *
   * @return the availability status of the vehicle
   */
  public boolean getAvailability() {
    return availability;
  }

  /**
   * Sets the availability status of the vehicle.
   *
   * @param availability the availability status of the vehicle
   */
  public void setAvailability(boolean availability) {
    this.availability = availability;
  }

  /**
   * Gets the image path of the vehicle.
   *
   * @return the image path of the vehicle
   */
  public String getImage() {
    return imagePath;
  }

  /**
   * Sets the image path of the vehicle.
   *
   * @param imagePath the image path of the vehicle
   */
  public void setImage(String imagePath) {
    this.imagePath = imagePath;
  }
}
