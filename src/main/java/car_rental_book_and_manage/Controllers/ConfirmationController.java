package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller class for the confirmation scene. Handles the display of booking confirmation details.
 */
public class ConfirmationController extends Controller {

  @FXML private Label bookingDate;
  @FXML private Label clientNameLbl;

  /** Initializes the controller and sets up the initial data. */
  public void initialize() {
    SceneManager.setController(Scenes.CONFIRMATION, this);
    clientNameLbl.textProperty().bind(dataModel.loggedInClientName());
    bookingDate.setText(LocalDate.now().toString());
  }
}
