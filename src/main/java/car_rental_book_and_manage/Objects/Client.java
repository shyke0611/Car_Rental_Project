package car_rental_book_and_manage.Objects;

/** Represents a client in the car rental system. */
public class Client {

  private String username, password, firstName, licenseNo, phoneNo;
  private int clientId;

  /** Default constructor. */
  public Client() {}

  /**
   * Constructs a client with the specified details.
   *
   * @param username the username of the client
   * @param password the password of the client
   * @param firstName the first name of the client
   * @param clientId the ID of the client
   * @param phoneNo the phone number of the client
   * @param licenseNo the license number of the client
   */
  public Client(
      String username,
      String password,
      String firstName,
      int clientId,
      String phoneNo,
      String licenseNo) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.clientId = clientId;
    this.phoneNo = phoneNo;
    this.licenseNo = licenseNo;
  }

  /**
   * Constructs a client with the specified details.
   *
   * @param clientId the ID of the client
   * @param firstName the first name of the client
   * @param phoneNo the phone number of the client
   * @param licenseNo the license number of the client
   */
  public Client(int clientId, String firstName, String phoneNo, String licenseNo) {
    this.clientId = clientId;
    this.firstName = firstName;
    this.phoneNo = phoneNo;
    this.licenseNo = licenseNo;
  }

  /**
   * Constructs a client with the specified details.
   *
   * @param username the username of the client
   * @param password the password of the client
   * @param firstName the first name of the client
   * @param phoneNo the phone number of the client
   * @param licenseNo the license number of the client
   */
  public Client(
      String username, String password, String firstName, String phoneNo, String licenseNo) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.phoneNo = phoneNo;
    this.licenseNo = licenseNo;
  }

  /**
   * Gets the client ID.
   *
   * @return the client ID
   */
  public int getClientId() {
    return clientId;
  }

  /**
   * Sets the client ID.
   *
   * @param clientId the client ID to set
   */
  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  /**
   * Gets the phone number of the client.
   *
   * @return the phone number of the client
   */
  public String getPhoneNo() {
    return phoneNo;
  }

  /**
   * Sets the phone number of the client.
   *
   * @param phoneNo the phone number to set
   */
  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  /**
   * Gets the first name of the client.
   *
   * @return the first name of the client
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name of the client.
   *
   * @param firstName the first name to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets the username of the client.
   *
   * @return the username of the client
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the client.
   *
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the password of the client.
   *
   * @return the password of the client
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the client.
   *
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets the license number of the client.
   *
   * @return the license number of the client
   */
  public String getLicenseNo() {
    return licenseNo;
  }

  /**
   * Sets the license number of the client.
   *
   * @param licenseNo the license number to set
   */
  public void setLicenseNo(String licenseNo) {
    this.licenseNo = licenseNo;
  }
}
