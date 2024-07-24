package car_rental_book_and_manage.Client.ClientUtility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert.AlertType;
import java.util.Map;

/** Utility class for handling server errors. */
public class ErrorHandlingUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Handles server errors and displays appropriate messages to the user.
     *
     * @param errorMessage the error message thrown during the server request
     * @param alertTitle the title of the alert
     * @param alertType the type of the alert
     */
    public static void handleServerErrors(String errorMessage, String alertTitle, AlertType alertType) {
        try {
            System.err.println("Error Message: " + errorMessage);

            if (errorMessage.contains("{")) {
                String jsonErrorResponse = errorMessage.substring(errorMessage.indexOf("{"));
                Map<String, String> errors = objectMapper.readValue(jsonErrorResponse, new TypeReference<Map<String, String>>() {});
                String firstErrorMessage = errors.values().iterator().next();
                AlertManager.showAlert(alertType, alertTitle, firstErrorMessage);
            } else {
                AlertManager.showAlert(alertType, alertTitle, errorMessage);
            }
        } catch (Exception ex) {
            AlertManager.showAlert(AlertType.ERROR, alertTitle, "An error occurred while processing the request.");
            ex.printStackTrace();
        }
    }
}
