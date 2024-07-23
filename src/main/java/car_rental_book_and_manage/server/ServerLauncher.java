package car_rental_book_and_manage.Server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * ServerLauncher is responsible for starting and stopping the HTTP server using Jetty.
 * It configures the server to handle REST API requests using JAX-RS.
 */
public class ServerLauncher {
    private static Server server;

    /**
     * Starts the HTTP server on port 8000.
     * @throws Exception if an error occurs during server startup.
     */
    public static void startServer() throws Exception {
        // Create a new Jetty server instance on port 8000
        server = new Server(8000);

        // Set up the context handler for handling sessions
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Configure the Jersey servlet to handle requests to /api/*
        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/api/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "car_rental_book_and_manage.Server.RestControllers");

        // Start the server
        server.start();
        // Print to console when the server starts
        System.out.println("Server started at http://localhost:8000");
    }

    /**
     * Stops the HTTP server.
     */
    public static void stopServer() {
        if (server != null) {
            try {
                // Stop the Jetty server
                server.stop();
                // Print to console when the server stops
                System.out.println("Server stopped");
            } catch (Exception e) {
                // Print the error to the console
                e.printStackTrace();
                System.err.println("Error stopping the server: " + e.getMessage());
            }
        }
    }

    /**
     * Main method to start the server.
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            startServer();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error starting the server: " + e.getMessage());
        }
    }
}
