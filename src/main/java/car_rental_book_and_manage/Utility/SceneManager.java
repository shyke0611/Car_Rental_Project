package car_rental_book_and_manage.Utility;

import car_rental_book_and_manage.Controllers.Controller;
import java.util.HashMap;
import javafx.scene.Parent;

/** SceneManager class for managing different scenes and their controllers in the application. */
public class SceneManager {

  /** Enum representing all the different scenes in the system. */
  public enum Scenes {
    LOGIN,
    ADMIN,
    SIGNUP,
    MANAGE,
    BOOKINGS,
    CUSTOMERS,
    CONFIRMATION,
    FINDVEHICLES,
    INSURANCE,
    MYBOOKING,
    PAYMENT
  }

  private static Controller activeController;
  private static HashMap<Scenes, Controller> controllerMap = new HashMap<>();
  private static HashMap<Scenes, Parent> sceneMap = new HashMap<>();

  /**
   * Adds a scene to the scene map.
   *
   * @param appUi The scene to add.
   * @param uiRoot The loaded scene.
   */
  public static void addUi(Scenes appUi, Parent uiRoot) {
    sceneMap.put(appUi, uiRoot);
  }

  /**
   * Gets a scene from the scene map and sets the active controller.
   *
   * @param appUi The scene to get.
   * @return The loaded scene.
   */
  public static Parent getUiRoot(Scenes appUi) {
    activeController = controllerMap.get(appUi);
    return sceneMap.get(appUi);
  }

  /**
   * Sets the currently active controller.
   *
   * @param controller The controller to set as active.
   */
  public static void setActiveController(Controller controller) {
    activeController = controller;
  }

  /**
   * Gets the currently active controller.
   *
   * @return The active controller.
   */
  public static Controller getActiveController() {
    return activeController;
  }

  /**
   * Adds a controller to the controller map.
   *
   * @param appUi The scene of the controller.
   * @param controller The controller to add.
   */
  public static void addController(Scenes appUi, Controller controller) {
    controllerMap.put(appUi, controller);
  }

  /**
   * Gets a controller from the controller map.
   *
   * @param appUi The scene of the controller.
   * @return The controller.
   */
  public static Controller getController(Scenes appUi) {
    return controllerMap.get(appUi);
  }

  /**
   * Replaces a controller in the controller map.
   *
   * @param appUi The scene of the controller.
   * @param controller The new controller.
   */
  public static void setController(Scenes appUi, Controller controller) {
    controllerMap.replace(appUi, controller);
  }
}
