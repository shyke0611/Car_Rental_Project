package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.Objects.Vehicle;
import car_rental_book_and_manage.Utility.AlertManager;
import car_rental_book_and_manage.Utility.ImageSelect;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import car_rental_book_and_manage.Utility.ValidationManager;
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
  @FXML private Label titleLbl;
  @FXML private Label idLbl;
  @FXML private ChoiceBox<String> searchChoiceBox;
  @FXML private TextField searchTxt;
  @FXML private ImageView vehicleImageView;

  private String imageName;
  private Vehicle selectedVehicle;

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
    setUpTableColumn(colColour, "colour", 75);
    setUpTableColumn(colFuel, "fuelType", 75);
    setUpAvailabilityColumn();
    setUpViewColumn();
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
    colAvail.setPrefWidth(75);
    colAvail.setResizable(false);
  }

  /** Sets up the view column with predefined properties. */
  private void setUpViewColumn() {
    colView.setPrefWidth(60);
    colView.setResizable(false);
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
    String id = idLbl.getText();
    String brand = txtBrand.getText();
    String model = txtModel.getText();
    String dailyRate = txtDailyRate.getText();
    String regNo = txtRegNumber.getText();
    String year = txtYear.getText();
    String colour = txtColour.getText();
    String fuel = choiceFuel.getValue();

    // Check for missing or invalid input fields
    if (ValidationManager.isVehicleInputValid(brand, model, year, colour, dailyRate, regNo, fuel)) {
      AlertManager.showAlert(
          AlertType.WARNING, "Required Fields", "Please Enter All Missing Fields");
      return false;
    }
    if (!ValidationManager.isDailyRateValid(dailyRate)) {
      AlertManager.showAlert(
          AlertType.WARNING, "Invalid Daily rate", "Daily rate must be in 0.00 format");
      return false;
    }
    if (!ValidationManager.isYearValid(year)) {
      AlertManager.showAlert(
          AlertType.WARNING,
          "Invalid Make Year",
          "Make Year must be in XXXX format and in between 1900 and current year");
      return false;
    }
    if (!ValidationManager.isRegNoValid(regNo)) {
      AlertManager.showAlert(
          AlertType.WARNING,
          "Invalid Registration number",
          "Registration number plate must be in the format AAA000");
      return false;
    }

    // Check color validity
    if (!ValidationManager.isColourValid(colour)) {
      AlertManager.showAlert(
          AlertType.WARNING, "Colour is invalid", "Colour entered is not a valid Colour");
      return false;
    }

    // Check if an image is selected
    if (vehicleImageView.getImage() == null) {
      AlertManager.showAlert(
          AlertType.WARNING, "Missing Image", "Please import an image before saving.");
      return false;
    }

    // Additional validation for save action
    if (action.equals("save")) {
      if (vehicledb.doesRegistrationNoExist(regNo)) {
        AlertManager.showAlert(
            AlertType.WARNING,
            "Registration number Already exists",
            "You must register another Registration number");
        return false;
      }
    }

    // Additional validation for update action
    if (action.equals("update")) {
      Vehicle selectedVehicle = dataModel.getVehicle(Integer.parseInt(id));
      // Only check for duplicate registration number if it has been changed
      if (!selectedVehicle.getLicensePlate().equals(regNo)
          && vehicledb.doesRegistrationNoExist(regNo)) {
        AlertManager.showAlert(
            AlertType.WARNING,
            "Registration number Already exists",
            "You must register another Registration number");
        return false;
      }
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
  }

  @FXML
  void onAddNewVehicle(MouseEvent event) {
    handleAddAction();
  }

  @FXML
  void onSaveVehicle(MouseEvent event) {
    if (validateVehicleFields("save")) {
      Vehicle vehicle = createVehicleFromFields();
      storeOrUpdateVehicle(vehicle);
    }
  }

  @FXML
  void onUpdateVehicle(MouseEvent event) {
    if (validateVehicleFields("update")) {
      Vehicle vehicle = createVehicleFromFields();
      vehicle.setVehicleId(Integer.parseInt(idLbl.getText()));
      storeOrUpdateVehicle(vehicle);
    }
  }

  @FXML
  void onDeleteVehicle(MouseEvent event) {
    vehicledb.deleteVehicle(selectedVehicle);
    AlertManager.showAlert(AlertType.CONFIRMATION, "", "Vehicle Deleted Successfully");
    clearTextFields();
    showVehiclePane(false);
  }

  @FXML
  void onCancel(MouseEvent event) {
    clearTextFields();
    showVehiclePane(false);
  }

  @FXML
  void onImportImage(MouseEvent event) {
    vehiclePane.setDisable(true);

    File selectedFile = ImageSelect.selectImageFile(null);
    if (selectedFile != null) {
      if (ImageSelect.isValidImage(selectedFile)) {
        imageName = selectedFile.getName();
        vehicleImageView.setImage(new Image("/images and attribution/" + imageName));
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
    vehicleImageView.setImage(new Image("/images and attribution/importcar.png"));
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
    imageName = vehicle.getImage();
    String fullImagePath = "/images and attribution/" + imageName;
    vehicleImageView.setImage(new Image(fullImagePath));
  }

  /**
   * Creates a vehicle object from the form fields.
   *
   * @return the created vehicle
   */
  private Vehicle createVehicleFromFields() {
    String brand = txtBrand.getText();
    String model = txtModel.getText();
    String dailyRate = txtDailyRate.getText();
    String regNo = txtRegNumber.getText();
    String year = txtYear.getText();
    String colour = txtColour.getText();
    String fuel = choiceFuel.getValue();
    return new Vehicle(
        Integer.parseInt(year),
        model,
        regNo,
        Double.parseDouble(dailyRate),
        brand,
        fuel,
        colour,
        imageName);
  }

  /**
   * Stores or updates the vehicle in the database.
   *
   * @param vehicle the vehicle to be stored or updated
   */
  private void storeOrUpdateVehicle(Vehicle vehicle) {
    if (vehicle.getVehicleId() == 0) {
      vehicledb.saveVehicle(vehicle);
      AlertManager.showAlert(AlertType.CONFIRMATION, "", "Vehicle Added Successfully");
    } else {
      vehicledb.updateVehicle(vehicle);
      AlertManager.showAlert(AlertType.CONFIRMATION, "", "Vehicle Updated Successfully");
    }
    clearTextFields();
    showVehiclePane(false);
  }
}
