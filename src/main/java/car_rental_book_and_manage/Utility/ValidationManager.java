package car_rental_book_and_manage.Utility;

import java.time.Year;
import javafx.scene.control.Alert.AlertType;

/** ValidationManager class for validating user and vehicle input. */
public class ValidationManager {

  /**
   * Checks if all sign-up user input fields are filled out.
   *
   * @param name The user's name.
   * @param password The user's password.
   * @param username The user's username.
   * @param phoneNo The user's phone number.
   * @param LicenceNo The user's license number.
   * @return true if any field is empty, false otherwise.
   */
  public static boolean isSignUpUserInputValid(
      String name, String password, String username, String phoneNo, String LicenceNo) {
    return name.isEmpty()
        || password.isEmpty()
        || username.isEmpty()
        || phoneNo.isEmpty()
        || LicenceNo.isEmpty();
  }

  /**
   * Validates the user's name.
   *
   * @param name The user's name.
   * @return true if the name contains only letters, false otherwise.
   */
  public static boolean isNameValid(String name) {
    return name.matches("[a-zA-Z]+");
  }

  /**
   * Validates the user's phone number.
   *
   * @param phoneNo The user's phone number.
   * @return true if the phone number is a 10-digit number, false otherwise.
   */
  public static boolean isPhoneNoValid(String phoneNo) {
    return phoneNo.matches("\\d{10}");
  }

  /**
   * Validates the user's license number.
   *
   * @param license The user's license number.
   * @return true if the license number matches the pattern, false otherwise.
   */
  public static boolean isLicenseNoValid(String license) {
    String licNoPattern = "(?i)^[a-z]{4}\\d{4}$";
    return license.matches(licNoPattern);
  }

  /**
   * Checks if all vehicle input fields are filled out.
   *
   * @param brand The vehicle brand.
   * @param model The vehicle model.
   * @param makeYear The vehicle's manufacturing year.
   * @param Colour The vehicle color.
   * @param dailyRate The vehicle's daily rate.
   * @param regNo The vehicle's registration number.
   * @param fuelType The vehicle's fuel type.
   * @return true if any field is empty, false otherwise.
   */
  public static boolean isVehicleInputValid(
      String brand,
      String model,
      String makeYear,
      String Colour,
      String dailyRate,
      String regNo,
      String fuelType) {
    return brand.isEmpty()
        || model.isEmpty()
        || makeYear.isEmpty()
        || Colour.isEmpty()
        || dailyRate.isEmpty()
        || regNo.isEmpty()
        || fuelType == null
        || fuelType.isEmpty();
  }

  /**
   * Checks if login input fields are filled out.
   *
   * @param username The user's username.
   * @param password The user's password.
   * @return false and shows an alert if any field is empty, true otherwise.
   */
  public static boolean isLoginInputValid(String username, String password) {
    if (username.isEmpty() || password.isEmpty()) {
      AlertManager.showAlert(
          AlertType.WARNING, "Login Unsuccessful", "Please Enter Your Information");
      return false;
    }
    return true;
  }

  /**
   * Validates the year.
   *
   * @param year The year to validate.
   * @return true if the year is between 1901 and the current year, false otherwise.
   */
  public static boolean isYearValid(String year) {
    if (!year.matches("\\d{4}")) {
      return false;
    }
    int currentYear = Year.now().getValue();
    final int MIN_YEAR = 1901;
    int yearValue = Integer.parseInt(year);
    return yearValue >= MIN_YEAR && yearValue <= currentYear;
  }

  /**
   * Validates the daily rate.
   *
   * @param rate The daily rate to validate.
   * @return true if the rate is a valid decimal number, false otherwise.
   */
  public static boolean isDailyRateValid(String rate) {
    String decimalPattern = "^\\d*\\.?\\d+$";
    return rate.matches(decimalPattern);
  }

  /**
   * Validates the vehicle registration number.
   *
   * @param regNo The registration number to validate.
   * @return true if the registration number matches the pattern, false otherwise.
   */
  public static boolean isRegNoValid(String regNo) {
    String regNoPattern = "^[A-Z]{3}\\d{3}$";
    return regNo.matches(regNoPattern);
  }

  /**
   * Validates the vehicle color.
   *
   * @param colour The color to validate.
   * @return true if the color contains only letters, false otherwise.
   */
  public static boolean isColourValid(String colour) {
    return colour.matches("[a-zA-Z]+");
  }
}
