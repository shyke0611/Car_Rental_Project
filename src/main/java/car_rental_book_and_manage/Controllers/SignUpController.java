package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.App;
import car_rental_book_and_manage.Objects.Client;
import car_rental_book_and_manage.Utility.AlertManager;
import car_rental_book_and_manage.Utility.PIIHashManager;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import car_rental_book_and_manage.Utility.ValidationManager;
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
    clientdb.saveClient(client);
    System.out.println("Client saved successfully: " + client.getFirstName());
    clearTextFields();
  }

  /**
   * Validates the user input for sign-up.
   *
   * @return true if the input is valid, false otherwise
   */
  private boolean isUserInputValid() {
    String name = nameField.getText();
    String password = passwordField.getText();
    String username = usernameField.getText();
    String phoneNo = phoneField.getText();
    String license = licenseField.getText().toUpperCase();

    if (!ValidationManager.validateSignUpUserInput(name, password, username, phoneNo, license, clientdb)) {
      return false;
    }
    return true;
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
    if (isUserInputValid()) {
      Client client = createClientFromInput();
      storeClientData(client);
      AlertManager.showAlert(AlertType.CONFIRMATION, "Sign Up", "Account Created Successfully");
    }
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
    // hash the password
    String hashedPassword = PIIHashManager.hashPassword(password);
    return new Client(username, hashedPassword, name, phoneNo, license);
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
