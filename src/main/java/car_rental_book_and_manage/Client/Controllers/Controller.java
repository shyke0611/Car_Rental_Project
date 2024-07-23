package car_rental_book_and_manage.Client.Controllers;

import car_rental_book_and_manage.Client.App;
import car_rental_book_and_manage.Client.ClientUtility.SceneManager.Scenes;
import car_rental_book_and_manage.Server.DAO.ClientDB;
import car_rental_book_and_manage.Server.DAO.ReservationDB;
import car_rental_book_and_manage.Server.DAO.VehicleDB;
import car_rental_book_and_manage.Server.Data.DataModel;
import car_rental_book_and_manage.Server.ServerUtility.ReservationManager;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

/** Abstract base controller class that provides common functionality for all controllers. */
public abstract class Controller {

  protected ClientDB clientdb;
  protected VehicleDB vehicledb;
  protected ReservationDB reservationdb;
  protected final DataModel dataModel;
  protected final ReservationManager reservationManager;

  /** Constructor for initializing the controller with necessary database and model instances. */
  public Controller() {
    clientdb = new ClientDB();
    vehicledb = new VehicleDB();
    reservationdb = new ReservationDB();
    dataModel = DataModel.getInstance();
    reservationManager = ReservationManager.getInstance();
  }

  /**
   * Handles the event to view customers.
   *
   * @param event the mouse event
   */
  @FXML
  protected void onViewCustomers(MouseEvent event) {
    App.setUi(Scenes.CUSTOMERS);
  }

  /**
   * Handles the event to view bookings.
   *
   * @param event the mouse event
   */
  @FXML
  protected void onViewBookings(MouseEvent event) {
    App.setUi(Scenes.BOOKINGS);
  }

  /**
   * Handles the event to manage vehicles.
   *
   * @param event the mouse event
   */
  @FXML
  protected void onManageVehicles(MouseEvent event) {
    App.setUi(Scenes.MANAGE);
  }

  /**
   * Handles the event to view the dashboard.
   *
   * @param event the mouse event
   */
  @FXML
  protected void onDashBoard(MouseEvent event) {
    App.setUi(Scenes.ADMIN);
  }

  /**
   * Handles the event to sign out the user.
   *
   * @param event the mouse event
   */
  @FXML
  protected void onSignOut(MouseEvent event) {
    App.setUi(Scenes.LOGIN);
    App.resetAllDatePickers();
    reservationManager.clearUserSession();
    reservationManager.clearSession();
  }

  /**
   * Handles the event to view the user's booking.
   *
   * @param event the mouse event
   */
  @FXML
  protected void onMyBooking(MouseEvent event) {
    App.setUi(Scenes.MYBOOKING);
  }

  /**
   * Handles the event to find vehicles.
   *
   * @param event the mouse event
   */
  @FXML
  protected void onFindVehicles(MouseEvent event) {
    App.setUi(Scenes.FINDVEHICLES);
  }
}
