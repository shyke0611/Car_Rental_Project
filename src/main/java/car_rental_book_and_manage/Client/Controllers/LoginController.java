package car_rental_book_and_manage.Client.Controllers;

import car_rental_book_and_manage.Client.App;
import car_rental_book_and_manage.Client.ClientUtility.AlertManager;
import car_rental_book_and_manage.Client.ClientUtility.ErrorHandlingUtil;
import car_rental_book_and_manage.Client.ClientUtility.HttpClientUtil;
import car_rental_book_and_manage.Client.ClientUtility.SceneManager;
import car_rental_book_and_manage.Client.ClientUtility.SceneManager.Scenes;
import car_rental_book_and_manage.SharedObject.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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

  private ObjectMapper objectMapper = new ObjectMapper();

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

    try {
      Map<String, String> loginRequest = new HashMap<>();
      loginRequest.put("username", enteredUsername);
      loginRequest.put("password", enteredPassword);

      String jsonResponse =
          HttpClientUtil.sendPostRequest("http://localhost:8000/api/clients/login", loginRequest);
      Client client = objectMapper.readValue(jsonResponse, Client.class);
      handleSuccessfulLogin(client);
    } catch (Exception e) {
      ErrorHandlingUtil.handleServerErrors(e.getMessage(), "Login Error", AlertType.ERROR);
    }
  }

  /**
   * Handles the successful login process.
   *
   * @param client the logged-in client
   */
  private void handleSuccessfulLogin(Client client) {
    reservationManager.setLoggedInClient(client);
    dataModel.setLoggedClientName(client.getFirstName());
    clearLoginFields();
    AlertManager.showAlert(AlertType.CONFIRMATION, "Login Successful", "You are now logging in.");
    App.setUi(Scenes.FINDVEHICLES);
  }

  /** Clears the login input fields. */
  private void clearLoginFields() {
    username.clear();
    password.clear();
  }
}
