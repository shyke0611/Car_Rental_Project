package car_rental_book_and_manage;

import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;

/**
 * CarRentalApplication is the entry point for the JAX-RS application.
 * It configures the RESTful web services and specifies the base URI path.
 */
@ApplicationPath("/api")
public class CarRentalApp extends ResourceConfig {

    /**
     * Constructor that configures the application.
     * It specifies the package where the REST controllers are located.
     */
    public CarRentalApp() {
        // Register packages containing REST controllers
        packages("car_rental_book_and_manage.Server.RestControllers");
    }
}
