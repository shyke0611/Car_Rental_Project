package car_rental_book_and_manage.Objects;

/** Interface for client data access operations. */
public interface ClientDAO {

  /**
   * Saves a client to the database.
   *
   * @param client the client to save
   */
  void saveClient(Client client);

  /**
   * Updates a client in the database.
   *
   * @param client the client to update
   */
  void updateClient(Client client);

  /** Retrieves all clients from the database. */
  void retrieveAllClients();

  /**
   * Gets the total number of clients in the database.
   *
   * @return the total number of clients
   */
  int getNumOfClients();

  /**
   * Checks if a username already exists in the database.
   *
   * @param username the username to check
   * @return true if the username exists, false otherwise
   */
  boolean doesUserNameExist(String username);

  /**
   * Checks if login credentials are valid.
   *
   * @param username the username to check
   * @param password the password to check
   * @return true if the credentials are valid, false otherwise
   */
  boolean isLoginCredentialsValid(String username, String password);

 /**
   * Gets a client by username.
   *
   * @param username the username of the client
   * @return the client with the specified username, or null if not found
   */
  Client getClient(String username);

  /** Retrieves the latest saved client from the database and adds it to the model. */
  void retrieveLatestClientToSave();

  /**
   * Retrieves a client by its ID and updates it in the model.
   *
   * @param clientId the ID of the client to retrieve
   */
  void retrieveClientByIdToUpdate(int clientId);
}
