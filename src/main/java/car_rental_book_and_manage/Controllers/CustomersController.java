package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.Objects.Client;
import car_rental_book_and_manage.Utility.AlertManager;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import car_rental_book_and_manage.Utility.ValidationManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * Controller class for managing customers. Handles the display, search, and update of customer
 * information.
 */
public class CustomersController extends Controller {

  @FXML private AnchorPane clientUpdatePane;
  @FXML private AnchorPane customersPane;
  @FXML private AnchorPane pane1;
  @FXML private AnchorPane pane2;
  @FXML private TableColumn<Client, Integer> colId;
  @FXML private TableColumn<Client, String> colLicense;
  @FXML private TableColumn<Client, String> colName;
  @FXML private TableColumn<Client, Integer> colPhone;
  @FXML private TableColumn<Client, Void> colUpdate;
  @FXML private TableView<Client> tableClient;
  @FXML private ChoiceBox<String> searchChoiceBox;
  @FXML private TextField searchTxt;
  @FXML private TextField txtID;
  @FXML private TextField txtLicense;
  @FXML private TextField txtName;
  @FXML private TextField txtPhone;
  @FXML private Label clientNoLbl;

  /** Initializes the controller and sets up the table columns, choice box, and search listener. */
  public void initialize() {
    SceneManager.setController(Scenes.CUSTOMERS, this);
    setUpTableColumns();
    setUpUpdateButtonCol();
    clientdb.retrieveAllClients();
    setUpSearchChoiceBox();
    addSearchListener();
    pane2.getStylesheets().add(getClass().getResource("/css/customcol.css").toExternalForm());
  }

  /** Sets up the search choice box with available options. */
  private void setUpSearchChoiceBox() {
    searchChoiceBox.getItems().addAll("ID", "License Number");
    searchChoiceBox.setValue("ID");
  }

  /** Adds a listener to the search text field to filter clients based on user input. */
  private void addSearchListener() {
    searchTxt
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.isEmpty()) {
                tableClient.getSelectionModel().clearSelection();
              } else {
                searchClientBy(newValue);
              }
            });
  }

  /**
   * Searches for clients based on the selected search option and user input value.
   *
   * @param value the search input value
   */
  private void searchClientBy(String value) {
    String searchOption = searchChoiceBox.getValue();
    switch (searchOption) {
      case "ID":
        searchClient(client -> String.valueOf(client.getClientId()).equals(value));
        break;
      case "License Number":
        searchClient(
            client -> {
              String licenseNo = client.getLicenseNo();
              return licenseNo != null && licenseNo.equalsIgnoreCase(value);
            });
        break;
      default:
        searchClient(client -> String.valueOf(client.getClientId()).equals(value));
        break;
    }
  }

  /**
   * Filters the client list based on the given predicate and selects the matching client.
   *
   * @param predicate the predicate to filter clients
   */
  private void searchClient(Predicate<Client> predicate) {
    for (int i = 0; i < tableClient.getItems().size(); i++) {
      if (predicate.test(tableClient.getItems().get(i))) {
        tableClient.scrollTo(i);
        tableClient.getSelectionModel().select(i);
        return;
      }
    }
  }

  /** Sets up the table columns with appropriate property value factories and configurations. */
  private void setUpTableColumns() {
    colId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
    colName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
    colLicense.setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
    tableClient.setPlaceholder(new Label(""));
    tableClient.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    tableClient.setFocusTraversable(false);
    tableClient.setItems(dataModel.getClientList());
    setTableColumnsWidthAndResizable();
  }

  /** Sets the width and resizable property of all table columns. */
  private void setTableColumnsWidthAndResizable() {
    colId.setPrefWidth(120);
    colName.setPrefWidth(180);
    colPhone.setPrefWidth(170);
    colLicense.setPrefWidth(170);
    colUpdate.setPrefWidth(120);

    colId.setResizable(false);
    colName.setResizable(false);
    colPhone.setResizable(false);
    colLicense.setResizable(false);
    colUpdate.setResizable(false);
  }

  /** Sets up the update button column with appropriate action handlers. */
  private void setUpUpdateButtonCol() {
    colUpdate.setCellFactory(
        param ->
            new TableCell<>() {
              private final Button updateButton = createUpdateButton();

              {
                updateButton.setOnAction(
                    event -> {
                      Client client = getTableView().getItems().get(getIndex());
                      handleUpdateButtonAction(client);
                    });
              }

              @Override
              protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setGraphic(null);
                } else {
                  setGraphic(updateButton);
                }
              }
            });
  }

  /**
   * Creates the update button with appropriate styles and icon.
   *
   * @return the created update button
   */
  private Button createUpdateButton() {
    Button updateButton = new Button();
    updateButton.getStyleClass().add("button-view");

    FontAwesomeIconView icon = new FontAwesomeIconView();
    icon.setGlyphName("REFRESH");
    icon.setFill(Color.BLACK);

    updateButton.setGraphic(icon);
    updateButton.setMaxWidth(Double.MAX_VALUE);
    updateButton.setMaxHeight(Double.MAX_VALUE);
    updateButton.setAlignment(Pos.CENTER);
    return updateButton;
  }

  /**
   * Handles the update button action to display client information in the update pane.
   *
   * @param client the client to be updated
   */
  private void handleUpdateButtonAction(Client client) {
    showUpdatePane(true);
    clientUpdatePane.requestFocus();
    clientNoLbl.setText(String.valueOf(client.getClientId()));
    txtID.setText(String.valueOf(client.getClientId()));
    txtName.setText(client.getFirstName());
    txtPhone.setText(String.valueOf(client.getPhoneNo()));
    txtLicense.setText(client.getLicenseNo());
  }

  /**
   * Shows or hides the update pane.
   *
   * @param show whether to show or hide the update pane
   */
  private void showUpdatePane(boolean show) {
    clientUpdatePane.setVisible(show);
    pane1.setDisable(show);
    pane2.setDisable(show);
  }

  /**
   * Handles the update of a client with the given information.
   *
   * @param clientId the client ID
   * @param firstName the first name
   * @param phone the phone number
   * @param license the license number
   */
  private void handleUpdateClient(int clientId, String firstName, String phone, String license) {
    Client updatedClient = new Client(clientId, firstName, phone, license);
    clientdb.updateClient(updatedClient);
    showUpdatePane(false);
  }

  /**
   * Handles the update client button action.
   *
   * @param event the mouse event
   */
  @FXML
  void onUpdateClient(MouseEvent event) {
    String firstName = txtName.getText();
    String phoneNo = txtPhone.getText();
    if (!ValidationManager.isNameValid(firstName)) {
      AlertManager.showAlert(
          AlertType.WARNING, "Invalid Name Format", "First Name Format Is Invalid");
      return;
    }
    if (!ValidationManager.isPhoneNoValid(phoneNo)) {
      AlertManager.showAlert(
          AlertType.WARNING, "Invalid Phone Number", "Phone Number must be a 7 digit number");
      return;
    }

    int clientId = Integer.parseInt(txtID.getText());
    String licenseNo = txtLicense.getText();
    handleUpdateClient(clientId, firstName, phoneNo, licenseNo);
  }

  /**
   * Handles the cancel update button action.
   *
   * @param event the mouse event
   */
  @FXML
  void onCancelUpdate(MouseEvent event) {
    showUpdatePane(false);
  }
}
