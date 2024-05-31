package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.Objects.Vehicle;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller class for the Admin scene. Manages the admin dashboard, including displaying
 * statistics and managing vehicles.
 */
public class AdminController extends Controller {

  @FXML private Label customerLbl;
  @FXML private Label vehicleLbl;
  @FXML private Label earningsLbl;
  @FXML private Button dashBoardBtn;
  @FXML private Button manageVehiclesButton;
  @FXML private Button viewBookingsBtn;
  @FXML private Button viewCustomersBtn;
  @FXML private PieChart piechart;

  /** Initializes the controller and sets up bindings and listeners. */
  public void initialize() {
    SceneManager.setController(Scenes.ADMIN, this);
    setupBindings();
    updateDataModel();
    initializePieChart();
    addVehicleListChangeListener();
  }

  /** Sets up the bindings for the labels. */
  private void setupBindings() {
    vehicleLbl.textProperty().bind(dataModel.numOfVehiclesProperty());
    customerLbl.textProperty().bind(dataModel.numOfClientsProperty());
    earningsLbl.textProperty().bind(dataModel.totalEarningsProperty());
  }

  /** Updates the data model with the latest counts from the database. */
  private void updateDataModel() {
    dataModel.setNumOfVehicles(String.valueOf(vehicledb.getNumOfVehicles()));
    dataModel.setNumOfClients(String.valueOf(clientdb.getNumOfClients()));
  }

  /** Initializes the PieChart with the current data. */
  private void initializePieChart() {
    updatePieChart();
  }

  /** Adds a listener to update the PieChart when the vehicle list changes. */
  private void addVehicleListChangeListener() {
    dataModel
        .getVehicleList()
        .addListener((ListChangeListener<? super Vehicle>) change -> updatePieChart());
  }

  /** Updates the PieChart with the latest available and booked cars data. */
  private void updatePieChart() {
    int availableCars = dataModel.getNumOfAvailableCars();
    int bookedCars = dataModel.getNumOfBookedCars();

    PieChart.Data availableCarsData = new PieChart.Data("Available Cars", availableCars);
    PieChart.Data bookedCarsData = new PieChart.Data("Booked Cars", bookedCars);

    piechart.getData().clear();
    piechart.getData().addAll(availableCarsData, bookedCarsData);
  }
}
