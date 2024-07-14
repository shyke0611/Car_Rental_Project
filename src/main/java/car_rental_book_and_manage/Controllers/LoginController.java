package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.App;
import car_rental_book_and_manage.Objects.Client;
import car_rental_book_and_manage.Utility.AlertManager;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import car_rental_book_and_manage.Utility.ValidationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/** Controller class for handling login operations. */
public class LoginController extends Controller {

  @FXML private Button loginBtn;
  @FXML private PasswordField password;
  @FXML private Button signUpBtn;
  @FXML private Button adminBtn;
  @FXML private TextField username;
  @FXML private Label authenticationLbl;

  /** Constructor for initializing the controller. */
  public LoginController() {
    super();
  }

  /** Initializes the view and sets the current scene. */
  public void initialize() {
    SceneManager.setController(Scenes.LOGIN, this);

    username.addEventFilter(KeyEvent.KEY_PRESSED, this::onEnter);
    password.addEventFilter(KeyEvent.KEY_PRESSED, this::onEnter);
  }

  /**
   * Handles the event to navigate to the sign-up page.
   *
   * @param event the mouse event
   */
  @FXML
  public void onSignUp(MouseEvent event) {
    App.setUi(Scenes.SIGNUP);
  }

  /**
   * Handles the event to navigate to the admin page.
   *
   * @param event the mouse event
   */
  @FXML
  public void onAdmin(MouseEvent event) {
    App.setUi(Scenes.ADMIN);
  }

  /**
   * Handles the login process when the login button is clicked.
   *
   * @param event the mouse event
   */
  @FXML
  public void onLogIn(MouseEvent event) {
    performLogin();
  }

  /** Handles the key pressed event to perform login when Enter is pressed. */
  @FXML
  private void onEnter(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
      performLogin();
    }
  }

  /** Performs the login logic. */
  private void performLogin() {
    String enteredUsername = username.getText();
    String enteredPassword = password.getText();

    if (isLoginInputValid(enteredUsername, enteredPassword)) {
      if (authenticateUser(enteredUsername, enteredPassword)) {
        handleSuccessfulLogin(enteredUsername, enteredPassword);
      } else {
        AlertManager.showAlert(
            AlertType.WARNING, "Login Unsuccessful", "Wrong Username or Password");
      }
    }
  }

  /**
   * Validates the login input.
   *
   * @param username the entered username
   * @param password the entered password
   * @return true if the input is valid, false otherwise
   */
  private boolean isLoginInputValid(String username, String password) {
    return ValidationManager.isLoginInputValid(username, password);
  }

  /**
   * Authenticates the user with the given credentials.
   *
   * @param username the entered username
   * @param password the entered password
   * @return true if the credentials are valid, false otherwise
   */
  private boolean authenticateUser(String username, String password) {
    return clientdb.isLoginCredentialsValid(username, password);
  }

  /**
   * Handles the successful login process.
   *
   * @param username the entered username
   * @param password the entered password
   */
  private void handleSuccessfulLogin(String username, String password) {
    Client loggedInClient = clientdb.getClient(username);
    reservationManager.setLoggedInClient(loggedInClient);
    dataModel.setLoggedClientName(loggedInClient.getFirstName());
    clearLoginFields();
    AlertManager.showAlert(AlertType.CONFIRMATION, "Login Successful", "You Are Now Logging In");
    App.setUi(Scenes.FINDVEHICLES);
  }

  /** Clears the login input fields. */
  private void clearLoginFields() {
    username.clear();
    password.clear();
  }
}
