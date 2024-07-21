package car_rental_book_and_manage.Server.ServerUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/** utility class for managing and decrypting encrypted configuration properties. 
 * 
 * Resources: 
 * https://www.devglan.com/online-tools/jasypt-online-encryption-decryption
 * https://www.devglan.com/online-tools/jasypt-online-encryption-decryption#:~:text=Decryption%3ATo%20decrypt%20data%20encrypted,to%20obtain%20the%20original%20plaintext.
 * 
 * just a temporary method to somewhat secure the JDBC credentials in the meantime until connection to server side
 * 
*/
public class ConfigManager {
  private static final Properties props = new Properties();
  private static final String ENCRYPTION_PASSWORD = "w4ldleeK03";

  // static block to load properties from the configuration file
  static {
    try (FileInputStream input = new FileInputStream("src/main/resources/database.properties")) {
      props.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Decrypts the encrypted property value associated with the given key.
   *
   * @param key the key whose associated value is to be decrypted
   * @return the decrypted value associated with the key
   * @throws RuntimeException if the property key is not found
   */
  public static String decryptProperty(String key) {
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setPassword(ENCRYPTION_PASSWORD); // Set the password for decryption
    String encryptedValue = props.getProperty(key);
    if (encryptedValue == null) {
      throw new RuntimeException("Property key not found: " + key);
    }
    // remove 'ENC('  ')' 
    encryptedValue =
        encryptedValue.substring(
            4, encryptedValue.length() - 1);
    return encryptor.decrypt(encryptedValue);
  }
}
