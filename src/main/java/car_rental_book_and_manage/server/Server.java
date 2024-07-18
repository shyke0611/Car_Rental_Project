package car_rental_book_and_manage.server;

import static spark.Spark.*;

import car_rental_book_and_manage.rest.ClientController;
import car_rental_book_and_manage.rest.ReservationController;
import car_rental_book_and_manage.rest.VehicleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Class for starting and stopping the HTTP server to handle REST API requests. */
public class Server {
  private static final Logger logger = LoggerFactory.getLogger(Server.class);

  /** Starts the HTTP server on port 8000. */
  public static void startServer() {
    port(8000); // Set the port to 8000

    // Initialize controllers
    new ClientController();
    new VehicleController();
    new ReservationController();

    init(); // Ensure the server is initialized
    awaitInitialization(); // Wait for the server to be fully initialized

    logger.info("Server started on port 8000");
  }

  /** Stops the HTTP server. */
  public static void stopServer() {
    stop(); // Stop the Spark server
    logger.info("Server stopped");
  }
}
