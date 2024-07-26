package car_rental_book_and_manage.Client.Controllers;

import car_rental_book_and_manage.Client.ClientUtility.AlertManager;
import car_rental_book_and_manage.Client.ClientUtility.ErrorHandlingUtil;
import car_rental_book_and_manage.Client.ClientUtility.HttpClientUtil;
import car_rental_book_and_manage.Client.ClientUtility.ImageSelect;
import car_rental_book_and_manage.Client.ClientUtility.SceneManager;
import car_rental_book_and_manage.Client.ClientUtility.SceneManager.Scenes;
import car_rental_book_and_manage.SharedObject.Vehicle;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.util.function.Predicate;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * Controller for the Manage view. Handles initialization and setting up the table columns, search
 * functionality, and choice boxes.
 */
public class ManageController extends Controller {

  @FXML private TableColumn<Vehicle, Void> colView;
  @FXML private TableColumn<Vehicle, Boolean> colAvail;
  @FXML private TableColumn<Vehicle, String> colBrand;
  @FXML private TableColumn<Vehicle, String> colColour;
  @FXML private TableColumn<Vehicle, Double> colDailyRate;
  @FXML private TableColumn<Vehicle, String> colFuel;
  @FXML private TableColumn<Vehicle, Integer> colId;
  @FXML private TableColumn<Vehicle, Integer> colMakeYear;
  @FXML private TableColumn<Vehicle, String> colModel;
  @FXML private TableColumn<Vehicle, String> colReg;
  @FXML private TableColumn<Vehicle, String> colEconomy;
  @FXML private TableView<Vehicle> tableVehicle;
  @FXML private Button cancelBtn;
  @FXML private ChoiceBox<String> choiceFuel;
  @FXML private Button importBtn;
  @FXML private Button saveVehicleBtn;
  @FXML private Button updateVehicleBtn;
  @FXML private Button deleteVehicleBtn;
  @FXML private AnchorPane vehiclePane;
  @FXML private AnchorPane pane1;
  @FXML private AnchorPane pane2;
  @FXML private TextField txtBrand;
  @FXML private TextField txtColour;
  @FXML private TextField txtDailyRate;
  @FXML private TextField txtModel;
  @FXML private TextField txtRegNumber;
  @FXML private TextField txtYear;
  @FXML private TextField txtEconomy;
  @FXML private Label titleLbl;
  @FXML private Label idLbl;
  @FXML private ChoiceBox<String> searchChoiceBox;
  @FXML private TextField searchTxt;
  @FXML private ImageView vehicleImageView;

  private String imageName;
  private Vehicle selectedVehicle;
  private boolean isDefaultImage = true;

  /**
   * Initializes the controller, setting up the choice boxes, table columns, view button column, and
   * search listener.
   */
  public void initialize() {
    SceneManager.setController(Scenes.MANAGE, this);
    initializeChoiceBoxes();
    setUpTableColumns();
    setUpViewButtonCol();
    addSearchListener();
    pane2.getStylesheets().add(getClass().getResource("/css/customcol.css").toExternalForm());
  }

  /** Initializes the choice boxes with predefined values. */
  private void initializeChoiceBoxes() {
    choiceFuel.getItems().addAll("Regular", "Diesel", "Premium");
    choiceFuel.setValue("Regular");
    searchChoiceBox.setValue("ID");
    searchChoiceBox.getItems().addAll("ID", "Registration");
  }

  /**
   * Adds a listener to the search text field to filter the table based on the entered search value.
   */
  private void addSearchListener() {
    searchTxt
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.isEmpty()) {
                tableVehicle.getSelectionModel().clearSelection();
              } else {
                searchVehicleBy(newValue);
              }
            });
  }

  /**
   * Searches for a vehicle based on the search value and option (ID or Registration).
   *
   * @param value the search value
   */
  private void searchVehicleBy(String value) {
    String searchOption = searchChoiceBox.getValue();
    Predicate<Vehicle> predicate = getSearchPredicate(searchOption, value);
    searchVehicle(predicate);
  }

  /**
   * Returns a predicate based on the search option and value.
   *
   * @param searchOption the search option (ID or Registration)
   * @param value the search value
   * @return a predicate to filter the vehicles
   */
  private Predicate<Vehicle> getSearchPredicate(String searchOption, String value) {
    switch (searchOption) {
      case "Registration":
        return vehicle -> {
          String reg = vehicle.getLicensePlate();
          return reg != null && reg.equalsIgnoreCase(value);
        };
      case "ID":
      default:
        return vehicle -> String.valueOf(vehicle.getVehicleId()).equals(value);
    }
  }

  /**
   * Searches for a vehicle in the table based on the given predicate.
   *
   * @param predicate the predicate to filter the vehicles
   */
  private void searchVehicle(Predicate<Vehicle> predicate) {
    for (int i = 0; i < tableVehicle.getItems().size(); i++) {
      if (predicate.test(tableVehicle.getItems().get(i))) {
        tableVehicle.scrollTo(i);
        tableVehicle.getSelectionModel().select(i);
        return;
      }
    }
  }

  /** Sets up the table columns with their respective properties. */
  private void setUpTableColumns() {
    setUpTableColumn(colId, "vehicleId", 30);
    setUpTableColumn(colMakeYear, "makeYear", 40);
    setUpTableColumn(colModel, "model", 100);
    setUpTableColumn(colBrand, "brand", 100);
    setUpTableColumn(colReg, "licensePlate", 65);
    setUpTableColumn(colDailyRate, "pricePerDay", 80);
    setUpTableColumn(colColour, "colour", 72);
    setUpTableColumn(colFuel, "fuelType", 75);
    setUpTableColumn(colEconomy, "economy", 99);
    setUpAvailabilityColumn();
    configureTable();
  }

  /**
   * Sets up a table column with the specified properties.
   *
   * @param column the table column
   * @param property the property name
   * @param width the column width
   */
  private void setUpTableColumn(TableColumn<Vehicle, ?> column, String property, double width) {
    column.setCellValueFactory(new PropertyValueFactory<>(property));
    column.setPrefWidth(width);
    column.setResizable(false);
  }

  /** Sets up the availability column with custom cell rendering. */
  private void setUpAvailabilityColumn() {
    colAvail.setCellValueFactory(new PropertyValueFactory<>("availability"));
    colAvail.setCellFactory(
        column ->
            new TableCell<Vehicle, Boolean>() {
              private final AnchorPane pane = new AnchorPane();

              @Override
              protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                  setGraphic(null);
                } else {
                  pane.getStyleClass().clear();
                  pane.getStyleClass().add(item ? "square-true" : "square-false");
                  setGraphic(pane);
                }
              }
            });
    colAvail.setPrefWidth(55);
    colAvail.setResizable(false);
  }

  /** Configures the table properties. */
  private void configureTable() {
    tableVehicle.setPlaceholder(new Label(""));
    tableVehicle.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    tableVehicle.setFocusTraversable(false);
    tableVehicle.setItems(dataModel.getVehicleList());
  }

  /** Sets up the view button column. */
  private void setUpViewButtonCol() {
    colView.setCellFactory(
        param ->
            new TableCell<>() {
              private final Button viewButton = createViewButton();

              {
                viewButton.setOnAction(
                    event -> {
                      Vehicle vehicle = getTableView().getItems().get(getIndex());
                      selectedVehicle = vehicle;
                      handleViewAction(vehicle);
                    });
              }

              @Override
              protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : viewButton);
              }
            });
    colView.setPrefWidth(45);
    colView.setResizable(false);
  }

  /**
   * Creates the view button with its properties and icon.
   *
   * @return the view button
   */
  private Button createViewButton() {
    Button viewButton = new Button();
    viewButton.getStyleClass().add("button-view");

    FontAwesomeIconView icon = new FontAwesomeIconView();
    icon.setGlyphName("EYE");
    icon.setFill(Color.BLACK);

    viewButton.setGraphic(icon);
    viewButton.setMaxWidth(Double.MAX_VALUE);
    viewButton.setMaxHeight(Double.MAX_VALUE);
    viewButton.setAlignment(Pos.CENTER);
    return viewButton;
  }

  /**
   * Validates the vehicle fields in the form.
   *
   * @param action the action being performed ("save" or "update")
   * @return true if the fields are valid, false otherwise
   */
  private boolean validateVehicleFields(String action) {
    if (action.equals("save") && isDefaultImage) {
      AlertManager.showAlert(
          AlertType.WARNING, "Missing Image", "Please import an image before saving.");
      return false;
    }

    return true;
  }

  /**
   * Handles the view action for a vehicle.
   *
   * @param vehicle the vehicle to view
   */
  private void handleViewAction(Vehicle vehicle) {
    showVehiclePane(true);
    vehiclePane.requestFocus();
    saveVehicleBtn.setVisible(false);
    updateVehicleBtn.setVisible(true);
    deleteVehicleBtn.setVisible(true);

    boolean isAvailable = vehicle.getAvailability();
    updateVehicleBtn.setDisable(!isAvailable);
    deleteVehicleBtn.setDisable(!isAvailable);
    importBtn.setDisable(!isAvailable);

    titleLbl.setText("View Vehicle ID:");
    idLbl.setText(String.valueOf(vehicle.getVehicleId()));
    populateVehicleFields(vehicle);
  }

  /** Handles the add action for a new vehicle. */
  private void handleAddAction() {
    titleLbl.setText("Add New Vehicle");
    showVehiclePane(true);
    vehiclePane.requestFocus();
    updateVehicleBtn.setVisible(false);
    deleteVehicleBtn.setVisible(false);
    saveVehicleBtn.setVisible(true);
    clearTextFields();
    vehicleImageView.setImage(new Image("/images and attribution/importcar.png"));
    isDefaultImage = true;
  }

  /**
   * Handles the action when a new vehicle is added.
   *
   * @param event The mouse event triggered by adding a new vehicle.
   */
  @FXML
  void onAddNewVehicle(MouseEvent event) {
    handleAddAction();
  }

  /**
   * Handles the action when a vehicle is saved.
   *
   * @param event The mouse event triggered by saving a vehicle.
   */
  @FXML
  void onSaveVehicle(MouseEvent event) {
    if (validateVehicleFields("save")) {
      try {
        Vehicle vehicle = createVehicleFromFields();
        storeVehicle(vehicle, "save");
      } catch (NumberFormatException e) {
        AlertManager.showAlert(AlertType.WARNING, "Invalid Input", e.getMessage());
      }
    }
  }

  /**
   * Handles the action when a vehicle is updated.
   *
   * @param event The mouse event triggered by updating a vehicle.
   */
  @FXML
  void onUpdateVehicle(MouseEvent event) {
    if (validateVehicleFields("update")) {
      try {
        Vehicle vehicle = createVehicleFromFields();
        vehicle.setVehicleId(Integer.parseInt(idLbl.getText()));
        storeVehicle(vehicle, "update");
      } catch (NumberFormatException e) {
        AlertManager.showAlert(AlertType.WARNING, "Invalid Input", e.getMessage());
      }
    }
  }

  /**
   * Handles the action when a vehicle is deleted.
   *
   * @param event The mouse event triggered by deleting a vehicle.
   */
  @FXML
  void onDeleteVehicle(MouseEvent event) {
    try {
      String endpoint = "http://localhost:8000/api/vehicles";
      HttpClientUtil.sendDeleteRequest(endpoint, selectedVehicle);
      AlertManager.showAlert(
          AlertType.CONFIRMATION, "Delete Vehicle", "Vehicle Deleted Successfully");
      clearTextFields();
      showVehiclePane(false);
    } catch (Exception e) {
      ErrorHandlingUtil.handleServerErrors(
          e.getMessage(), "Delete Vehicle Error", AlertType.WARNING);
    }
  }

  /**
   * Handles the action when the cancel button is clicked.
   *
   * @param event The mouse event triggered by canceling the current action.
   */
  @FXML
  void onCancel(MouseEvent event) {
    clearTextFields();
    showVehiclePane(false);
  }

  /**
   * Handles the action when an image is imported.
   *
   * @param event The mouse event triggered by importing an image.
   */
  @FXML
  void onImportImage(MouseEvent event) {
    vehiclePane.setDisable(true);

    File selectedFile = ImageSelect.selectImageFile(null);
    if (selectedFile != null) {
      if (ImageSelect.isValidImage(selectedFile)) {
        imageName = selectedFile.getName();
        vehicleImageView.setImage(new Image("/images and attribution/" + imageName));
        isDefaultImage = false;
        System.out.println("Image Path: " + imageName);
      } else {
        System.out.println("Invalid image selected.");
      }
    } else {
      System.out.println("File selection cancelled.");
    }

    vehiclePane.setDisable(false);
  }

  /**
   * Shows or hides the vehicle pane.
   *
   * @param visible true to show, false to hide
   */
  private void showVehiclePane(boolean visible) {
    vehiclePane.setVisible(visible);
    pane1.setDisable(visible);
    pane2.setDisable(visible);
  }

  /** Clears the text fields in the vehicle form. */
  private void clearTextFields() {
    txtBrand.clear();
    txtModel.clear();
    txtDailyRate.clear();
    txtRegNumber.clear();
    txtYear.clear();
    txtColour.clear();
    idLbl.setText(null);
    choiceFuel.setValue("Regular");
    txtEconomy.clear();
    vehicleImageView.setImage(new Image("/images and attribution/importcar.png"));
    isDefaultImage = true;
  }

  /**
   * Populates the form fields with the vehicle's details.
   *
   * @param vehicle the vehicle whose details are to be displayed
   */
  private void populateVehicleFields(Vehicle vehicle) {
    txtBrand.setText(vehicle.getBrand());
    txtColour.setText(vehicle.getColour());
    txtDailyRate.setText(String.valueOf(vehicle.getPricePerDay()));
    txtModel.setText(vehicle.getModel());
    txtRegNumber.setText(vehicle.getLicensePlate());
    txtYear.setText(String.valueOf(vehicle.getMakeYear()));
    choiceFuel.setValue(vehicle.getFuelType());
    txtEconomy.setText(vehicle.getEconomy());
    imageName = vehicle.getImage();
    if (imageName != null && !imageName.isEmpty()) {
      String fullImagePath = "/images and attribution/" + imageName;
      vehicleImageView.setImage(new Image(fullImagePath));
      isDefaultImage = false;
    } else {
      vehicleImageView.setImage(new Image("/images and attribution/importcar.png"));
      isDefaultImage = true;
    }
  }

  /**
   * Creates a vehicle object from the form fields.
   *
   * @return the created vehicle
   */
  private Vehicle createVehicleFromFields() throws NumberFormatException {
    String brand = txtBrand.getText();
    String model = txtModel.getText();
    String dailyRate = txtDailyRate.getText();
    String regNo = txtRegNumber.getText();
    String year = txtYear.getText();
    String colour = txtColour.getText();
    String fuel = choiceFuel.getValue();
    String econ = txtEconomy.getText();

    return new Vehicle(year, model, regNo, dailyRate, brand, fuel, colour, imageName, econ);
  }

  /**
   * Stores the vehicle by making a REST call.
   *
   * @param vehicle the vehicle to be stored or updated
   * @param action the action being performed ("save" or "update")
   */
  private void storeVehicle(Vehicle vehicle, String action) {
    try {
      if ("save".equals(action)) {
        HttpClientUtil.sendPostRequest("http://localhost:8000/api/vehicles", vehicle);
        AlertManager.showAlert(
            AlertType.CONFIRMATION, "Vehicle Created", "Vehicle Added Successfully");
      } else if ("update".equals(action)) {
        HttpClientUtil.sendPutRequest("http://localhost:8000/api/vehicles", vehicle);
        AlertManager.showAlert(
            AlertType.CONFIRMATION, "Vehicle Update", "Vehicle Updated Successfully");
      }
      clearTextFields();
      showVehiclePane(false);
    } catch (Exception e) {
      ErrorHandlingUtil.handleServerErrors(
          e.getMessage(), "Save/Update Vehicle Error", AlertType.WARNING);
    }
  }
}