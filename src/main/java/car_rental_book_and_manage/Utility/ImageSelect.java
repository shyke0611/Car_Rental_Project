package car_rental_book_and_manage.Utility;

import java.io.File;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/** Utility class for selecting and validating image files. */
public class ImageSelect {

  /**
   * Opens a file chooser dialog to select an image file.
   *
   * @param ownerWindow the owner window of the file chooser dialog
   * @return the selected image file, or null if no file is selected
   */
  public static File selectImageFile(Window ownerWindow) {
    FileChooser fileChooser = new FileChooser();
    fileChooser
        .getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
    return fileChooser.showOpenDialog(ownerWindow);
  }

  /**
   * Validates if the selected file is within the 'images and attribution' directory.
   *
   * @param selectedFile the file to validate
   * @return true if the file is valid, false otherwise
   */
  public static boolean isValidImage(File selectedFile) {
    if (selectedFile != null) {
      if (!isImageInCorrectDirectory(selectedFile.getPath())) {
        AlertManager.showAlert(
            AlertType.ERROR,
            "Invalid Image",
            "Please select an image file from the 'images and attribution' directory from the"
                + " project.");
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if the image path contains the 'images and attribution' directory.
   *
   * @param imagePath the path of the image file
   * @return true if the path is valid, false otherwise
   */
  private static boolean isImageInCorrectDirectory(String imagePath) {
    return imagePath.contains("/images and attribution/");
  }
}
