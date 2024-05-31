package car_rental_book_and_manage;

import car_rental_book_and_manage.Objects.DataModel;
import car_rental_book_and_manage.Objects.ReservationDB;
import car_rental_book_and_manage.Objects.VehicleDB;
import car_rental_book_and_manage.Utility.BookingScheduler;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/** JavaFX App */
public class App extends Application {

  private static Scene scene;
 
  public static final ExecutorService vehicledbExecutor = Executors.newFixedThreadPool(5);
  public static final ExecutorService clientdbExecutor = Executors.newFixedThreadPool(5);
  public static final ExecutorService reservationdbExecutor = Executors.newFixedThreadPool(5);
  private static final DataModel model = DataModel.getInstance();
  private VehicleDB v = new VehicleDB();
  private ReservationDB r = new ReservationDB();
  private BookingScheduler bookingScheduler = new BookingScheduler();


  public void shutdown() {
    bookingScheduler.stop();
    vehicledbExecutor.shutdown();
    clientdbExecutor.shutdown();
    reservationdbExecutor.shutdown();
    model.clearVehicles();
    model.clearClients();
    model.clearReservations();
  }

 /**
   * Static method to reset all DatePicker fields in the application.
   */
  public static void resetAllDatePickers() {
    Parent root = scene.getRoot();
    List<DatePicker> datePickers = root.lookupAll(".date-picker").stream()
        .map(node -> (DatePicker) node)
        .collect(Collectors.toList());

    for (DatePicker datePicker : datePickers) {
      datePicker.setValue(null);
      datePicker.getEditor().setPromptText(datePicker.getAccessibleText());
    }
  }

  @Override
  public void start(Stage stage) throws IOException {

    v.retrieveAllVehicles();
    r.retrieveAllReservations();

    SceneManager.addController(SceneManager.Scenes.ADMIN, null);
    SceneManager.addUi(SceneManager.Scenes.ADMIN, loadFXML("admin"));
    
    SceneManager.addController(SceneManager.Scenes.LOGIN, null);
    SceneManager.addUi(SceneManager.Scenes.LOGIN, loadFXML("login"));

    SceneManager.addController(SceneManager.Scenes.SIGNUP, null);
    SceneManager.addUi(SceneManager.Scenes.SIGNUP, loadFXML("signup"));

    SceneManager.addController(SceneManager.Scenes.MANAGE, null);
    SceneManager.addUi(SceneManager.Scenes.MANAGE, loadFXML("manage"));

    SceneManager.addController(SceneManager.Scenes.BOOKINGS, null);
    SceneManager.addUi(SceneManager.Scenes.BOOKINGS, loadFXML("bookings"));

    SceneManager.addController(SceneManager.Scenes.CUSTOMERS, null);
    SceneManager.addUi(SceneManager.Scenes.CUSTOMERS, loadFXML("customers"));

    SceneManager.addController(SceneManager.Scenes.CONFIRMATION, null);
    SceneManager.addUi(SceneManager.Scenes.CONFIRMATION, loadFXML("confirmation"));

    SceneManager.addController(SceneManager.Scenes.FINDVEHICLES, null);
    SceneManager.addUi(SceneManager.Scenes.FINDVEHICLES, loadFXML("findvehicles"));

    SceneManager.addController(SceneManager.Scenes.INSURANCE, null);
    SceneManager.addUi(SceneManager.Scenes.INSURANCE, loadFXML("insurance"));

    SceneManager.addController(SceneManager.Scenes.MYBOOKING, null);
    SceneManager.addUi(SceneManager.Scenes.MYBOOKING, loadFXML("mybooking"));

    Parent root = SceneManager.getUiRoot(Scenes.LOGIN);

    scene = new Scene(root, 900, 600);
    stage.setScene(scene);
    stage.setTitle("My Vehicle Rental Company");
    stage.setResizable(false);
    stage.show();
    root.requestFocus();

    bookingScheduler.processOverdueBookings();
    bookingScheduler.start();
  }

  @Override
  public void stop() throws Exception {
    // Shut down the executor service when the application is closing
    shutdown();
  }

  static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFXML(fxml));
  }

  /**
   * Change the scene to the input scene using the scenemanager map of scenes.
   *
   * @param newUi - The scene to change to.
   */
  public static void setUi(Scenes newUi) {
    scene.setRoot(SceneManager.getUiRoot(newUi));
    SceneManager.setActiveController(SceneManager.getController(newUi));
  }

  private static Parent loadFXML(String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  public static void main(String[] args) {
    launch();
  }
}
