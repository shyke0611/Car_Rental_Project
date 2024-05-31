package car_rental_book_and_manage.Utility;

import car_rental_book_and_manage.Objects.Client;
import car_rental_book_and_manage.Objects.Reservation;
import car_rental_book_and_manage.Objects.Vehicle;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.beans.property.*;
import javafx.scene.image.Image;

/**
 * Singleton class that manages reservations, including details such as the selected vehicle,
 * pick-up and return dates, and total costs. It provides properties and methods to handle the
 * reservation process.
 */
public class ReservationManager {
  private static ReservationManager instance;

  private final ObjectProperty<Client> loggedInClient = new SimpleObjectProperty<>();
  private final ObjectProperty<Vehicle> selectedVehicle = new SimpleObjectProperty<>();
  private final ObjectProperty<Reservation> currentReservation = new SimpleObjectProperty<>();
  private final ObjectProperty<LocalDate> pickUpDate = new SimpleObjectProperty<>();
  private final ObjectProperty<LocalDate> returnDate = new SimpleObjectProperty<>();
  private final ObjectProperty<Image> vehicleImage = new SimpleObjectProperty<>();
  private final StringProperty totalDays = new SimpleStringProperty("0");
  private final StringProperty dailyPrice = new SimpleStringProperty("0.0");
  private final StringProperty totalCost = new SimpleStringProperty("0.0");
  private final StringProperty brand = new SimpleStringProperty("");
  private final StringProperty model = new SimpleStringProperty("");
  private final StringProperty dailyTotal = new SimpleStringProperty("0.0");
  private final StringProperty totalAmount = new SimpleStringProperty("0.0");
  private final StringProperty insuranceCost = new SimpleStringProperty("0.0");
  private final StringProperty insuranceType = new SimpleStringProperty("");

  private ReservationManager() {
    pickUpDate.addListener((obs, oldDate, newDate) -> updateTotalDays());
    returnDate.addListener((obs, oldDate, newDate) -> updateTotalDays());
    selectedVehicle.addListener((obs, oldVehicle, newVehicle) -> updateVehicleDetails());
  }

  /**
   * Get the singleton instance of the ReservationManager.
   *
   * @return the singleton instance
   */
  public static synchronized ReservationManager getInstance() {
    if (instance == null) {
      instance = new ReservationManager();
    }
    return instance;
  }

  /**
   * Get the current reservation property.
   *
   * @return the current reservation property
   */
  public ObjectProperty<Reservation> currentReservationProperty() {
    return currentReservation;
  }

  /**
   * Get the current reservation.
   *
   * @return the current reservation
   */
  public Reservation getCurrentReservation() {
    return currentReservation.get();
  }

  /**
   * Set the current reservation.
   *
   * @param reservation the reservation to set
   */
  public void setCurrentReservation(Reservation reservation) {
    this.currentReservation.set(reservation);
  }

  /**
   * Get the logged-in client.
   *
   * @return the logged-in client
   */
  public Client getLoggedInClient() {
    return loggedInClient.get();
  }

  /**
   * Set the logged-in client.
   *
   * @param client the client to set as logged in
   */
  public void setLoggedInClient(Client client) {
    this.loggedInClient.set(client);
  }

  /**
   * Get the logged-in client property.
   *
   * @return the logged-in client property
   */
  public ObjectProperty<Client> loggedInClientProperty() {
    return loggedInClient;
  }

  /** Clear the session, including the selected vehicle and dates. */
  public void clearSession() {
    this.selectedVehicle.set(null);
    this.pickUpDate.set(null);
    this.returnDate.set(null);
    this.totalDays.set("0");
    this.dailyPrice.set("0.0");
    this.brand.set("");
    this.model.set("");
    this.dailyTotal.set("0.0");
  }

  /** Clear the user session, including the logged-in client. */
  public void clearUserSession() {
    this.loggedInClient.set(null);
  }

  /**
   * Check if a client is logged in.
   *
   * @return true if a client is logged in, false otherwise
   */
  public boolean isLoggedIn() {
    return loggedInClient.get() != null;
  }

  /**
   * Get the selected vehicle.
   *
   * @return the selected vehicle
   */
  public Vehicle getSelectedVehicle() {
    return selectedVehicle.get();
  }

  /**
   * Set the selected vehicle.
   *
   * @param vehicle the vehicle to set as selected
   */
  public void setSelectedVehicle(Vehicle vehicle) {
    this.selectedVehicle.set(vehicle);
  }

  /**
   * Get the selected vehicle property.
   *
   * @return the selected vehicle property
   */
  public ObjectProperty<Vehicle> selectedVehicleProperty() {
    return selectedVehicle;
  }

  /**
   * Get the pick-up date property.
   *
   * @return the pick-up date property
   */
  public ObjectProperty<LocalDate> pickUpDateProperty() {
    return pickUpDate;
  }

  /**
   * Get the pick-up date.
   *
   * @return the pick-up date
   */
  public LocalDate getPickUpDate() {
    return pickUpDate.get();
  }

  /**
   * Set the pick-up date.
   *
   * @param date the date to set as the pick-up date
   */
  public void setPickUpDate(LocalDate date) {
    this.pickUpDate.set(date);
  }

  /**
   * Get the return date property.
   *
   * @return the return date property
   */
  public ObjectProperty<LocalDate> returnDateProperty() {
    return returnDate;
  }

  /**
   * Get the return date.
   *
   * @return the return date
   */
  public LocalDate getReturnDate() {
    return returnDate.get();
  }

  /**
   * Set the return date.
   *
   * @param date the date to set as the return date
   */
  public void setReturnDate(LocalDate date) {
    this.returnDate.set(date);
  }

  /**
   * Get the total days property.
   *
   * @return the total days property
   */
  public StringProperty totalDaysProperty() {
    return totalDays;
  }

  /**
   * Get the total days.
   *
   * @return the total days
   */
  public String getTotalDays() {
    return totalDays.get();
  }

  /**
   * Get the daily price property.
   *
   * @return the daily price property
   */
  public StringProperty dailyPriceProperty() {
    return dailyPrice;
  }

  /**
   * Get the daily price.
   *
   * @return the daily price
   */
  public String getDailyPrice() {
    return dailyPrice.get();
  }

  /**
   * Get the brand property.
   *
   * @return the brand property
   */
  public StringProperty brandProperty() {
    return brand;
  }

  /**
   * Get the brand.
   *
   * @return the brand
   */
  public String getBrand() {
    return brand.get();
  }

  /**
   * Get the model property.
   *
   * @return the model property
   */
  public StringProperty modelProperty() {
    return model;
  }

  /**
   * Get the model.
   *
   * @return the model
   */
  public String getModel() {
    return model.get();
  }

  /**
   * Get the daily total property.
   *
   * @return the daily total property
   */
  public StringProperty dailyTotalProperty() {
    return dailyTotal;
  }

  /**
   * Get the daily total.
   *
   * @return the daily total
   */
  public String getDailyTotal() {
    return dailyTotal.get();
  }

  /**
   * Get the total cost property.
   *
   * @return the total cost property
   */
  public StringProperty totalCostProperty() {
    return totalCost;
  }

  /**
   * Get the total cost.
   *
   * @return the total cost
   */
  public String getTotalCost() {
    return totalCost.get();
  }

  /**
   * Set the total cost.
   *
   * @param cost the total cost to set
   */
  public void setTotalCost(String cost) {
    this.totalCost.set(cost);
  }

  /**
   * Get the total amount property.
   *
   * @return the total amount property
   */
  public StringProperty totalAmountProperty() {
    return totalAmount;
  }

  /**
   * Get the total amount.
   *
   * @return the total amount
   */
  public String getTotalAmount() {
    return totalAmount.get();
  }

  /**
   * Get the vehicle image property.
   *
   * @return the vehicle image property
   */
  public ObjectProperty<Image> vehicleImageProperty() {
    return vehicleImage;
  }

  /**
   * Get the vehicle image.
   *
   * @return the vehicle image
   */
  public Image getVehicleImage() {
    return vehicleImage.get();
  }

  /**
   * Set the vehicle image.
   *
   * @param image the image to set for the vehicle
   */
  public void setVehicleImage(Image image) {
    this.vehicleImage.set(image);
  }

  /**
   * Get the insurance cost property.
   *
   * @return the insurance cost property
   */
  public StringProperty insuranceCostProperty() {
    return insuranceCost;
  }

  /**
   * Get the insurance cost.
   *
   * @return the insurance cost
   */
  public String getInsuranceCost() {
    return insuranceCost.get();
  }

  /**
   * Set the insurance cost.
   *
   * @param dailyCost the daily cost of insurance
   */
  public void setInsuranceCost(double dailyCost) {
    int days = Integer.parseInt(totalDays.get());
    double totalInsuranceCost = dailyCost * days;
    this.insuranceCost.set(String.format("%.2f", totalInsuranceCost));
    updateTotalAmount();
  }

  /**
   * Get the insurance type property.
   *
   * @return the insurance type property
   */
  public StringProperty insuranceTypeProperty() {
    return insuranceType;
  }

  /**
   * Get the insurance type.
   *
   * @return the insurance type
   */
  public String getInsuranceType() {
    return insuranceType.get();
  }

  /**
   * Set the insurance type.
   *
   * @param type the type of insurance
   */
  public void setInsuranceType(String type) {
    this.insuranceType.set(type);
  }

  /** Update the total days based on the pick-up and return dates. */
  private void updateTotalDays() {
    if (pickUpDate.get() != null && returnDate.get() != null) {
      LocalDate pickUp = pickUpDate.get();
      LocalDate returnD = returnDate.get();
      if (!returnD.isBefore(pickUp)) {
        totalDays.set(String.valueOf(ChronoUnit.DAYS.between(pickUp, returnD)));
      } else {
        totalDays.set("0");
      }
    } else {
      totalDays.set("0");
    }
    updateTotalAmount();
    System.out.println(
        "Total days between "
            + pickUpDate.get()
            + " and "
            + returnDate.get()
            + ": "
            + totalDays.get());
  }

  /** Update the total amount based on the daily price, total days, and insurance cost. */
  public void updateTotalAmount() {
    try {
      double price = Double.parseDouble(dailyPrice.get());
      int days = Integer.parseInt(totalDays.get());
      double insurance = Double.parseDouble(insuranceCost.get());
      double total = price * days + insurance;
      totalAmount.set(String.format("%.2f", total));
    } catch (NumberFormatException e) {
      totalAmount.set("0.00");
    }
  }

  /** Update the vehicle details based on the selected vehicle. */
  private void updateVehicleDetails() {
    Vehicle vehicle = selectedVehicle.get();
    if (vehicle != null) {
      setVehicleDetails(vehicle);
      setVehicleImage(vehicle.getImage());
      updateDailyTotal();
    } else {
      clearVehicleDetails();
    }
  }

  /**
   * Set the vehicle details from the given vehicle object.
   *
   * @param vehicle the vehicle to extract details from
   */
  private void setVehicleDetails(Vehicle vehicle) {
    dailyPrice.set(String.valueOf(vehicle.getPricePerDay()));
    brand.set(vehicle.getBrand());
    model.set(vehicle.getModel());
  }

  /** Clear the vehicle details when no vehicle is selected. */
  private void clearVehicleDetails() {
    dailyPrice.set("0.0");
    brand.set("");
    model.set("");
    dailyTotal.set("0.0");
    vehicleImage.set(null);
  }

  /**
   * Set the vehicle image from the given image path.
   *
   * @param imagePath the path to the vehicle image
   */
  private void setVehicleImage(String imagePath) {
    if (imagePath != null && !imagePath.isEmpty()) {
      String fullPath = "/images and attribution/" + imagePath;
      Image image = new Image(getClass().getResourceAsStream(fullPath));
      if (image.isError()) {
        System.out.println("Error loading image from path: " + fullPath);
      } else {
        vehicleImage.set(image);
      }
    } else {
      System.out.println("Vehicle image path is null or empty");
      vehicleImage.set(null);
    }
  }

  /** Update the daily total based on the daily price and total days. */
  private void updateDailyTotal() {
    try {
      double price = Double.parseDouble(dailyPrice.get());
      int days = Integer.parseInt(totalDays.get());
      dailyTotal.set(String.format("%.2f", price * days));
    } catch (NumberFormatException e) {
      dailyTotal.set("0.00");
    }
  }
}
