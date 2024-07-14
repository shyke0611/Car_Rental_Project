package car_rental_book_and_manage.Utility;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password hashing using bcrypt.
 * 
 * Source for the methods: https://www.mindrot.org/projects/jBCrypt/ 
 * Source for idea: https://stackoverflow.com/questions/69791042/how-to-validate-user-password-after-hashing-using-sha-256-salted-in-java 
 */
public class PIIHashManager {

  /**
   * Hashes a password using bcrypt.
   *
   * @param password the password to hash
   * @return the hashed password
   */
  public static String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  /**
   * Checks if the provided password matches the hashed password.
   *
   * @param password the password to check
   * @param hashedPassword the hashed password to compare against
   * @return true if the password matches, false otherwise
   */
  public static boolean checkPassword(String password, String hashedPassword) {
    return BCrypt.checkpw(password, hashedPassword);
  }
}
