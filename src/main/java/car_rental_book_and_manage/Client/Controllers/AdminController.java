package car_rental_book_and_manage.Client.Controllers;

import car_rental_book_and_manage.Client.ClientUtility.ErrorHandlingUtil;
import car_rental_book_and_manage.Client.ClientUtility.HttpClientUtil;
import car_rental_book_and_manage.Client.ClientUtility.SceneManager;
import car_rental_book_and_manage.Client.ClientUtility.SceneManager.Scenes;
import car_rental_book_and_manage.SharedObject.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert.AlertType;
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

  private ObjectMapper objectMapper = new ObjectMapper();

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
    updateClientCount();
    updateVehicleCount();
  }

  /** Fetches the client count from the REST API and updates the data model. */
  private void updateClientCount() {
    try {
      String jsonResponse =
          HttpClientUtil.sendGetRequest("http://localhost:8000/api/clients/count");
      int count = objectMapper.readValue(jsonResponse, Integer.class);
      dataModel.setNumOfClients(String.valueOf(count));
    } catch (Exception e) {
      ErrorHandlingUtil.handleServerErrors(e.getMessage(), "Client Count Error", AlertType.WARNING);
    }
  }

  /** Fetches the vehicle count from the REST API and updates the data model. */
  private void updateVehicleCount() {
    try {
      String jsonResponse =
          HttpClientUtil.sendGetRequest("http://localhost:8000/api/vehicles/count");
      int count = objectMapper.readValue(jsonResponse, Integer.class);
      dataModel.setNumOfVehicles(String.valueOf(count));
    } catch (Exception e) {
      ErrorHandlingUtil.handleServerErrors(
          e.getMessage(), "Vehicle Count Error", AlertType.WARNING);
    }
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
