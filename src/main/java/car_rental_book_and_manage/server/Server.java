package car_rental_book_and_manage.server;

import com.sun.net.httpserver.HttpServer;
import car_rental_book_and_manage.rest.ClientController;
import car_rental_book_and_manage.rest.VehicleController;
import car_rental_book_and_manage.rest.ReservationController;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Class for starting and stopping the HTTP server to handle REST API requests.
 */
public class Server {
    private static HttpServer server;

    /**
     * Starts the HTTP server on port 8000.
     *
     * @throws IOException if an I/O error occurs when creating the server
     */
    public static void startServer() throws IOException {
        // Create an HTTP server listening on port 8000
        server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Create context for different API endpoints
        server.createContext("/api/clients", new ClientController());
        server.createContext("/api/vehicles", new VehicleController());
        server.createContext("/api/reservations", new ReservationController());

        // Set a default executor
        server.setExecutor(null);

        // Start the server
        server.start();
        System.out.println("Server started on port 8000");
    }

    /**
     * Stops the HTTP server.
     */
    public static void stopServer() {
        if (server != null) {
            server.stop(0);
            System.out.println("Server stopped");
        }
    }
}
