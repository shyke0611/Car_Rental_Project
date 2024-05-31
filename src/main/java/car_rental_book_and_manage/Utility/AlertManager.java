package car_rental_book_and_manage.Utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/** Utility class for displaying alerts in the application. */
public class AlertManager {

  /**
   * Displays an alert with the specified type, title, and message.
   *
   * @param alertType the type of the alert (e.g., ERROR, INFORMATION, WARNING)
   * @param title the title of the alert dialog
   * @param message the message content of the alert dialog
   */
  public static void showAlert(AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
