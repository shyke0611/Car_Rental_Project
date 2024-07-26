package car_rental_book_and_manage.Client.Controllers;

import car_rental_book_and_manage.Client.App;
import car_rental_book_and_manage.Client.ClientUtility.AlertManager;
import car_rental_book_and_manage.Client.ClientUtility.ErrorHandlingUtil;
import car_rental_book_and_manage.Client.ClientUtility.HttpClientUtil;
import car_rental_book_and_manage.Client.ClientUtility.SceneManager;
import car_rental_book_and_manage.Client.ClientUtility.SceneManager.Scenes;
import car_rental_book_and_manage.SharedObject.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Controller class for the SignUp scene. Handles user interactions and client data storage during
 * the sign-up process.
 */
public class SignUpController extends Controller {

  @FXML private Button createAccBtn;
  @FXML private Button logInBtn;
  @FXML private PasswordField passwordField;
  @FXML private TextField usernameField;
  @FXML private TextField licenseField;
  @FXML private TextField nameField;
  @FXML private TextField phoneField;

  private ObjectMapper objectMapper = new ObjectMapper();

  /** Initializes the views and sets the controller for the SignUp scene. */
  public void initialize() {
    SceneManager.setController(Scenes.SIGNUP, this);

    nameField.addEventFilter(KeyEvent.KEY_PRESSED, this::onEnter);
    passwordField.addEventFilter(KeyEvent.KEY_PRESSED, this::onEnter);
    usernameField.addEventFilter(KeyEvent.KEY_PRESSED, this::onEnter);
    phoneField.addEventFilter(KeyEvent.KEY_PRESSED, this::onEnter);
    licenseField.addEventFilter(KeyEvent.KEY_PRESSED, this::onEnter);
  }

  /** Clears all text fields in the sign-up form. */
  private void clearTextFields() {
    nameField.clear();
    passwordField.clear();
    usernameField.clear();
    phoneField.clear();
    licenseField.clear();
  }

  /**
   * Saves the client data into the database and clears the text fields.
   *
   * @param client the client to save
   */
  private void storeClientData(Client client) {
    try {
      String jsonPayload = objectMapper.writeValueAsString(client);
      System.out.println("JSON Payload: " + jsonPayload);

      HttpClientUtil.sendPostRequest("http://localhost:8000/api/clients", client);
      AlertManager.showAlert(AlertType.CONFIRMATION, "Sign Up", "Account Created Successfully");
      System.out.println("Client saved successfully: " + client.getFirstName());
      clearTextFields();
    } catch (Exception e) {
      ErrorHandlingUtil.handleServerErrors(e.getMessage(), "Sign Up Error", AlertType.WARNING);
    }
  }

  /**
   * Handles the create account button click event. Validates user input and stores client data if
   * valid.
   *
   * @param event the mouse event
   */
  @FXML
  public void onCreateAccount(MouseEvent event) {
    performCreateAccount();
  }

  /** Handles the key pressed event. */
  @FXML
  private void onEnter(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
      performCreateAccount();
    }
  }

  /** Performs the create account process. */
  private void performCreateAccount() {
    Client client = createClientFromInput();
    storeClientData(client);
  }

  /**
   * Creates a Client object from the user input fields.
   *
   * @return the created Client object
   */
  private Client createClientFromInput() {
    String name = nameField.getText();
    String password = passwordField.getText();
    String username = usernameField.getText();
    String phoneNo = phoneField.getText();
    String license = licenseField.getText().toUpperCase();
    return new Client(username, password, name, phoneNo, license);
  }

  /**
   * Handles the login button click event. Switches to the login scene.
   *
   * @param event the mouse event
   */
  @FXML
  public void onLogIn(MouseEvent event) {
    App.setUi(Scenes.LOGIN);
  }
}
