package car_rental_book_and_manage.Server.ServerUtility;

import car_rental_book_and_manage.Server.DAO.ClientDB;
import car_rental_book_and_manage.Server.DAO.VehicleDB;
import car_rental_book_and_manage.SharedObject.Client;
import car_rental_book_and_manage.SharedObject.Data.DataModel;
import car_rental_book_and_manage.SharedObject.Vehicle;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

/** ValidationManager class for validating user and vehicle input. */
public class ValidationManager {

  private static final DataModel dataModel = DataModel.getInstance();

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
      String fuelType,
      String economy) {
    return brand.isEmpty()
        || model.isEmpty()
        || makeYear.isEmpty()
        || Colour.isEmpty()
        || dailyRate.isEmpty()
        || regNo.isEmpty()
        || fuelType == null
        || fuelType.isEmpty()
        || economy.isEmpty();
  }

  /**
   * Checks if login input fields are filled out.
   *
   * @param username The user's username.
   * @param password The user's password.
   * @return false and shows an alert if any field is empty, true otherwise.
   */
  public static Map<String, String> validateClientLoginInput(
      String username, String password, ClientDB clientDB) {
    Map<String, String> errors = new HashMap<>();

    if (username.isEmpty()) {
      errors.put("username", "Username is required.");
      return errors;
    }

    if (password.isEmpty()) {
      errors.put("password", "Password is required.");
      return errors;
    } else {
      Client client = clientDB.getClient(username);
      if (client == null || !PIIHashManager.checkPassword(password, client.getPassword())) {
        errors.put("credentials", "Invalid username or password.");
        return errors;
      }
    }

    return errors;
  }

  /**
   * Validates the update client input fields.
   *
   * @param firstName The user's first name.
   * @param phoneNo The user's phone number.
   * @return Map of error messages if any field is invalid, otherwise empty map.
   */
  public static Map<String, String> validateClientUpdateInput(String firstName, String phoneNo) {
    Map<String, String> errors = new HashMap<>();

    if (firstName.isEmpty()) {
      errors.put("firstName", "First name is required.");
    } else if (!isNameValid(firstName)) {
      errors.put("firstName", "First name contains invalid characters.");
    }

    if (phoneNo.isEmpty()) {
      errors.put("phoneNo", "Phone number is required.");
    } else if (!isPhoneNoValid(phoneNo)) {
      errors.put("phoneNo", "Phone number is not valid. It should be a 10-digit number.");
    }

    return errors;
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
    String regNoPattern = "(?i)^[a-z]{3}\\d{3}$";
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

  /**
   * Validates the vehicle fuel economy.
   *
   * @param fuelEconomy The fuel economy to validate.
   * @return true if the fuel economy is a valid string containing exactly three digits, false
   *     otherwise.
   */
  public static boolean isFuelEconomyValid(String fuelEconomy) {
    return fuelEconomy.matches("\\d{2,3}");
  }

  /**
   * Validates the CVV.
   *
   * @param cvv The CVV to validate.
   * @return true if the CVV is a 3-digit number, false otherwise.
   */
  public static boolean isCvvValid(String cvv) {
    return cvv.matches("\\d{3}");
  }

  /**
   * Validates the card number.
   *
   * @param cardNumber The card number to validate.
   * @return true if the card number is 12 digits and contains a space after every 3 digits, false
   *     otherwise.
   */
  public static boolean isCardNumberValid(String cardNumber) {
    return cardNumber.matches("^\\d{3} \\d{3} \\d{3} \\d{3}$");
  }

  /**
   * Validates the expiry date.
   *
   * @param expiryDate The expiry date to validate.
   * @return true if the expiry date is in the format MM/YY, false otherwise.
   */
  public static boolean isExpiryDateValid(String expiryDate) {
    return expiryDate.matches("\\d{2}/\\d{2}");
  }

  /**
   * Validates all vehicle input fields.
   *
   * @param brand The vehicle brand.
   * @param model The vehicle model.
   * @param makeYear The vehicle's manufacturing year.
   * @param colour The vehicle color.
   * @param dailyRate The vehicle's daily rate.
   * @param regNo The vehicle's registration number.
   * @param fuelType The vehicle's fuel type.
   * @param economy The vehicle's fuel economy.
   * @return Map of error messages if any field is invalid, otherwise empty map.
   */
  public static Map<String, String> validateVehicleFields(
      String brand,
      String model,
      String makeYear,
      String colour,
      String dailyRate,
      String regNo,
      String fuelType,
      String economy) {
    Map<String, String> errors = new HashMap<>();

    if (brand.isEmpty()) {
      errors.put("brand", "Brand is required.");
      return errors;
    }
    if (model.isEmpty()) {
      errors.put("model", "Model is required.");
      return errors;
    }
    if (makeYear.isEmpty()) {
      errors.put("makeYear", "Make year is required.");
      return errors;
    } else if (!isYearValid(makeYear)) {
      errors.put(
          "makeYear", "Make year must be in XXXX format and between 1900 and the current year.");
      return errors;
    }
    if (colour.isEmpty()) {
      errors.put("colour", "Colour is required.");
      return errors;
    } else if (!isColourValid(colour)) {
      errors.put("colour", "Colour entered is not valid.");
      return errors;
    }
    if (dailyRate.isEmpty()) {
      errors.put("dailyRate", "Daily rate is required.");
      return errors;
    } else if (!isDailyRateValid(dailyRate)) {
      errors.put("dailyRate", "Daily rate must be in 0.00 format.");
      return errors;
    }
    if (regNo.isEmpty()) {
      errors.put("regNo", "Registration number is required.");
      return errors;
    } else if (!isRegNoValid(regNo)) {
      errors.put("regNo", "Registration number must be in the format AAA000.");
      return errors;
    }
    if (fuelType == null || fuelType.isEmpty()) {
      errors.put("fuelType", "Fuel type is required.");
      return errors;
    }
    if (economy.isEmpty()) {
      errors.put("economy", "Fuel economy is required.");
      return errors;
    } else if (!isFuelEconomyValid(economy)) {
      errors.put("economy", "Fuel economy must be 2 or 3 digits.");
      return errors;
    }

    return errors;
  }

  /**
   * Checks if vehicle registration number exists in the database and shows an alert if it does.
   *
   * @param regNo The registration number to check.
   * @param action The action being performed ("save" or "update").
   * @param vehicledb The vehicle database instance.
   * @param id The vehicle ID (for update action).
   * @return true if registration number exists, false otherwise.
   */
  public static Map<String, String> checkRegistrationNoExists(
      String regNo, String action, VehicleDB vehicledb, String id) {
    Map<String, String> errors = new HashMap<>();

    if ("save".equals(action)) {
      if (vehicledb.doesRegistrationNoExist(regNo)) {
        errors.put(
            "regNo",
            "Registration number already exists. You must register another Registration number.");
        return errors;
      }
    }

    if ("update".equals(action)) {
      Vehicle selectedVehicle = dataModel.getVehicle(Integer.parseInt(id));
      if (!selectedVehicle.getLicensePlate().equals(regNo)
          && vehicledb.doesRegistrationNoExist(regNo)) {
        errors.put(
            "regNo",
            "Registration number already exists. You must register another Registration number.");
        return errors;
      }
    }
    return errors;
  }

  /**
   * Validates all sign-up user input fields and checks for existing username.
   *
   * @param name The user's name.
   * @param password The user's password.
   * @param username The user's username.
   * @param phoneNo The user's phone number.
   * @param license The user's license number.
   * @param clientdb The client database instance.
   * @return Map of error messages if any field is invalid or username exists, otherwise empty map.
   */
  public static Map<String, String> validateSignUpClientInput(
      String name,
      String password,
      String username,
      String phoneNo,
      String license,
      ClientDB clientdb) {
    Map<String, String> errors = new HashMap<>();

    if (name.isEmpty()) {
      errors.put("name", "Name is required.");
      return errors;
    } else if (!isNameValid(name)) {
      errors.put("name", "First name format is invalid.");
      return errors;
    }

    if (password.isEmpty()) {
      errors.put("password", "Password is required.");
      return errors;
    }

    if (username.isEmpty()) {
      errors.put("username", "Username is required.");
      return errors;
    } else if (clientdb.doesUserNameExist(username)) {
      errors.put("username", "Username already exists.");
      return errors;
    }

    if (license.isEmpty()) {
      errors.put("license", "License number is required.");
      return errors;
    } else if (!isLicenseNoValid(license)) {
      errors.put("license", "License number format must be XXXX0000.");
      return errors;
    } else if (clientdb.doesLicenseNoExist(license)) {
      errors.put("license", "License number already exists");
      return errors;
    }

    if (phoneNo.isEmpty()) {
      errors.put("phoneNo", "Phone number is required.");
      return errors;
    } else if (!isPhoneNoValid(phoneNo)) {
      errors.put("phoneNo", "Phone number must be a 10-digit number (XXXXXXXXXX).");
      return errors;
    }

    return errors;
  }

  /**
   * Validates the card details input fields.
   *
   * @param cardName The cardholder's name.
   * @param cardNumber The card number.
   * @param cvv The CVV number.
   * @param expiryDate The expiry date.
   * @return Map of error messages if any field is invalid, otherwise empty map.
   */
  public static Map<String, String> validateCardDetails(
      String cardName, String cardNumber, String cvv, String expiryDate) {
    Map<String, String> errors = new HashMap<>();

    if (cardName.isEmpty()) {
      errors.put("cardName", "Cardholder's name is required.");
      return errors;
    }

    if (cardNumber.isEmpty()) {
      errors.put("cardNumber", "Card number is required.");
      return errors;
    } else if (!isCardNumberValid(cardNumber)) {
      errors.put("cardNumber", "Card number must be 12 digits and formatted with spaces.");
      return errors;
    }

    if (cvv.isEmpty()) {
      errors.put("cvv", "CVV is required.");
      return errors;
    } else if (!isCvvValid(cvv)) {
      errors.put("cvv", "CVV must be a 3-digit number.");
      return errors;
    }

    if (expiryDate.isEmpty()) {
      errors.put("expiryDate", "Expiry date is required.");
      return errors;
    } else if (!isExpiryDateValid(expiryDate)) {
      errors.put("expiryDate", "Expiry date must be in the format MM/YY.");
      return errors;
    }

    return errors;
  }
}
