package car_rental_book_and_manage.Objects;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Singleton class that serves as the data model for the car rental application. It manages clients,
 * vehicles, and reservations.
 */
public class DataModel {

  private static DataModel instance;

  private final StringProperty numOfClients = new SimpleStringProperty();
  private final StringProperty numOfVehicles = new SimpleStringProperty();
  private final StringProperty loggedInClientName = new SimpleStringProperty();
  private final StringProperty totalEarnings = new SimpleStringProperty("0.0");
  private final ObservableList<Client> obClientList =
      FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
  private final ObservableList<Vehicle> obVehicleList =
      FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
  private final ObservableList<Vehicle> availableVehicleList;
  private final ObservableList<Reservation> obReservationList =
      FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

  private DataModel() {
    FilteredList<Vehicle> filteredList =
        new FilteredList<>(obVehicleList, Vehicle::getAvailability);
    availableVehicleList = FXCollections.synchronizedObservableList(filteredList);
  }

  /**
   * Returns the singleton instance of the DataModel.
   *
   * @return the singleton instance
   */
  public static synchronized DataModel getInstance() {
    if (instance == null) {
      instance = new DataModel();
    }
    return instance;
  }

  /** Updates the total earnings based on the reservations. */
  private void updateTotalEarnings() {
    Platform.runLater(
        () -> {
          double total = obReservationList.stream().mapToDouble(Reservation::getTotalRate).sum();
          totalEarnings.set(String.format("%.2f", total));
        });
  }

  // Client list methods

  /**
   * Returns the observable list of clients.
   *
   * @return the client list
   */
  public final ObservableList<Client> getClientList() {
    return obClientList;
  }

  /**
   * Adds a client to the client list.
   *
   * @param client the client to add
   */
  public void addClient(Client client) {
    Platform.runLater(() -> obClientList.add(client));
  }

  /**
   * Removes a client from the client list.
   *
   * @param client the client to remove
   */
  public void removeClient(Client client) {
    Platform.runLater(() -> obClientList.remove(client));
  }

  /** Clears all clients from the client list. */
  public void clearClients() {
    Platform.runLater(obClientList::clear);
  }

  /**
   * Updates the information of an existing client in the client list.
   *
   * @param client the client with updated information
   */
  public void updateClient(Client client) {
    Platform.runLater(
        () -> {
          for (Client c : obClientList) {
            if (c.getClientId() == client.getClientId()) {
              int index = obClientList.indexOf(c);
              obClientList.set(index, client);
              break;
            }
          }
        });
  }

  /**
   * Retrieves a client by their ID.
   *
   * @param id the ID of the client
   * @return the client if found, null otherwise
   */
  public Client getClient(int id) {
    return obClientList.stream().filter(c -> c.getClientId() == id).findFirst().orElse(null);
  }

  // Vehicle list methods

  /**
   * Returns the observable list of vehicles.
   *
   * @return the vehicle list
   */
  public final ObservableList<Vehicle> getVehicleList() {
    return obVehicleList;
  }

  /**
   * Returns the observable list of available vehicles.
   *
   * @return the available vehicle list
   */
  public final ObservableList<Vehicle> getAvailableVehicleList() {
    return availableVehicleList;
  }

  /**
   * Adds a vehicle to the vehicle list.
   *
   * @param vehicle the vehicle to add
   */
  public void addVehicle(Vehicle vehicle) {
    Platform.runLater(() -> obVehicleList.add(vehicle));
  }

  /**
   * Removes a vehicle from the vehicle list.
   *
   * @param vehicle the vehicle to remove
   */
  public void removeVehicle(Vehicle vehicle) {
    Platform.runLater(() -> obVehicleList.remove(vehicle));
  }

  /** Clears all vehicles from the vehicle list. */
  public void clearVehicles() {
    Platform.runLater(obVehicleList::clear);
  }

  /**
   * Updates the information of an existing vehicle in the vehicle list.
   *
   * @param vehicle the vehicle with updated information
   */
  public void updateVehicle(Vehicle vehicle) {
    Platform.runLater(
        () -> {
          for (Vehicle v : obVehicleList) {
            if (v.getVehicleId() == vehicle.getVehicleId()) {
              int index = obVehicleList.indexOf(v);
              obVehicleList.set(index, vehicle);
              break;
            }
          }
        });
  }

  /**
   * Retrieves a vehicle by their ID.
   *
   * @param id the ID of the vehicle
   * @return the vehicle if found, null otherwise
   */
  public Vehicle getVehicle(int id) {
    return obVehicleList.stream().filter(v -> v.getVehicleId() == id).findFirst().orElse(null);
  }

  // Reservation list methods

  /**
   * Returns the observable list of reservations.
   *
   * @return the reservation list
   */
  public final ObservableList<Reservation> getReservationList() {
    return obReservationList;
  }

  /**
   * Adds a reservation to the reservation list.
   *
   * @param reservation the reservation to add
   */
  public void addReservation(Reservation reservation) {
    Platform.runLater(
        () -> {
          obReservationList.add(reservation);
          updateTotalEarnings();
        });
  }

  /**
   * Removes a reservation from the reservation list.
   *
   * @param reservation the reservation to remove
   */
  public void removeReservation(Reservation reservation) {
    Platform.runLater(
        () -> {
          obReservationList.remove(reservation);
          updateTotalEarnings();
        });
  }

  /** Clears all reservations from the reservation list. */
  public void clearReservations() {
    Platform.runLater(
        () -> {
          obReservationList.clear();
          updateTotalEarnings();
        });
  }

  /**
   * Updates the information of an existing reservation in the reservation list.
   *
   * @param reservation the reservation with updated information
   */
  public void updateReservation(Reservation reservation) {
    Platform.runLater(
        () -> {
          for (Reservation r : obReservationList) {
            if (r.getReservationId() == reservation.getReservationId()) {
              int index = obReservationList.indexOf(r);
              obReservationList.set(index, reservation);
              updateTotalEarnings();
              break;
            }
          }
        });
  }

  /**
   * Retrieves a reservation by their ID.
   *
   * @param id the ID of the reservation
   * @return the reservation if found, null otherwise
   */
  public Reservation getReservation(int id) {
    return obReservationList.stream()
        .filter(r -> r.getReservationId() == id)
        .findFirst()
        .orElse(null);
  }

  // Property methods

  /**
   * Returns the number of clients as a property.
   *
   * @return the number of clients property
   */
  public final StringProperty numOfClientsProperty() {
    return numOfClients;
  }

  /**
   * Returns the number of clients.
   *
   * @return the number of clients
   */
  public final String getNumOfClients() {
    return numOfClients.get();
  }

  /**
   * Sets the number of clients.
   *
   * @param numOfClients the number of clients
   */
  public final void setNumOfClients(String numOfClients) {
    this.numOfClients.set(numOfClients);
  }

  /**
   * Returns the number of vehicles as a property.
   *
   * @return the number of vehicles property
   */
  public final StringProperty numOfVehiclesProperty() {
    return numOfVehicles;
  }

  /**
   * Returns the number of vehicles.
   *
   * @return the number of vehicles
   */
  public final String getNumOfVehicles() {
    return numOfVehicles.get();
  }

  /**
   * Sets the number of vehicles.
   *
   * @param numOfVehicles the number of vehicles
   */
  public final void setNumOfVehicles(String numOfVehicles) {
    this.numOfVehicles.set(numOfVehicles);
  }

  /**
   * Returns the logged in client name as a property.
   *
   * @return the logged in client name property
   */
  public final StringProperty loggedInClientName() {
    return loggedInClientName;
  }

  /**
   * Returns the logged in client name.
   *
   * @return the logged in client name
   */
  public final String getLoggedInClientName() {
    return loggedInClientName.get();
  }

  /**
   * Sets the logged in client name.
   *
   * @param name the logged in client name
   */
  public final void setLoggedClientName(String name) {
    this.loggedInClientName.set(name);
  }

  /**
   * Returns the total earnings as a property.
   *
   * @return the total earnings property
   */
  public final StringProperty totalEarningsProperty() {
    return totalEarnings;
  }

  /**
   * Returns the total earnings.
   *
   * @return the total earnings
   */
  public final String getTotalEarnings() {
    return totalEarnings.get();
  }

  /**
   * Returns the number of available cars.
   *
   * @return the number of available cars
   */
  public int getNumOfAvailableCars() {
    return (int) obVehicleList.stream().filter(Vehicle::getAvailability).count();
  }

  /**
   * Returns the number of booked cars.
   *
   * @return the number of booked cars
   */
  public int getNumOfBookedCars() {
    return (int) obVehicleList.stream().filter(vehicle -> !vehicle.getAvailability()).count();
  }
}
