package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.App;
import car_rental_book_and_manage.InsuranceStrategy.BasicCoverStrategy;
import car_rental_book_and_manage.InsuranceStrategy.InsuranceManager;
import car_rental_book_and_manage.InsuranceStrategy.LimitedCoverStrategy;
import car_rental_book_and_manage.InsuranceStrategy.PremiumCoverStrategy;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Controller class for the Insurance scene. Manages user interaction with insurance options and
 * updates the reservation accordingly.
 */
public class InsuranceController extends Controller {

  @FXML private Button BasicBtn;
  @FXML private Button PremiumBtn;
  @FXML private Label clientNameLbl;
  @FXML private Label dailyCostLbl;
  @FXML private Label dailyTotalLbl;
  @FXML private Label insuranceCostLbl;
  @FXML private Label insuranceDetailsLbl;
  @FXML private Label insuranceTypeLbl;
  @FXML private Button limitedBtn;
  @FXML private Label noOfDaysLbl;
  @FXML private Label pickUpLbl;
  @FXML private Label returnLbl;
  @FXML private Label totalAmountLbl;
  @FXML private Label brandLbl;
  @FXML private Label modelLbl;
  @FXML private VBox imageVbox;
  @FXML private ImageView vehicleImageView;
  @FXML private Pane basicCoverPane;
  @FXML private Pane limitedCoverPane;
  @FXML private Pane premiumCoverPane;

  private InsuranceManager insuranceManage = new InsuranceManager();

  /**
   * Initializes the InsuranceController. Sets up bindings for UI elements and initializes default
   * selections.
   */
  public void initialize() {
    setupBindings();
    selectInitialInsuranceOption();
  }

  /** Sets up data bindings between the UI elements and the data model. */
  private void setupBindings() {
    SceneManager.setController(Scenes.INSURANCE, this);
    clientNameLbl.textProperty().bind(dataModel.loggedInClientName());
    pickUpLbl.textProperty().bind(reservationManager.pickUpDateProperty().asString());
    returnLbl.textProperty().bind(reservationManager.returnDateProperty().asString());
    noOfDaysLbl.textProperty().bind(reservationManager.totalDaysProperty());
    dailyCostLbl.textProperty().bind(reservationManager.dailyPriceProperty());
    totalAmountLbl.textProperty().bind(reservationManager.totalAmountProperty());
    brandLbl.textProperty().bind(reservationManager.brandProperty());
    modelLbl.textProperty().bind(reservationManager.modelProperty());
    insuranceCostLbl.textProperty().bind(reservationManager.insuranceCostProperty());
    insuranceTypeLbl.textProperty().bind(reservationManager.insuranceTypeProperty());
    dailyTotalLbl.textProperty().bind(reservationManager.dailyTotalProperty());
    vehicleImageView.imageProperty().bind(reservationManager.vehicleImageProperty());
  }

  /** Sets the initial insurance option to Limited Cover. */
  private void selectInitialInsuranceOption() {
    onSelectLimited(null);
  }

  /**
   * Handles the Go Back button click event. Switches the scene back to the Find Vehicles scene.
   *
   * @param event the mouse event triggering this action
   */
  @FXML
  void onGoBack(MouseEvent event) {
    App.setUi(Scenes.FINDVEHICLES);
    onSelectLimited(null);
  }

  /**
   * Handles the Proceed button click event. Sets insurance details and switches to the Payment scene.
   *
   * @param event the mouse event triggering this action
   */
  @FXML
  void onProceed(MouseEvent event) {
    setInsuranceDetails();
    App.setUi(Scenes.PAYMENT);
  }

  /** Sets the insurance details in the reservation manager. */
  private void setInsuranceDetails() {
    updateInsuranceDetails();
  }

  /**
   * Handles the selection of the Basic insurance option.
   *
   * @param event the mouse event triggering this action
   */
  @FXML
  void onSelectBasic(MouseEvent event) {
    insuranceManage.setStrategy(new BasicCoverStrategy());
    updateInsuranceDetails();
    setSelectedButton(BasicBtn);
  }

  /**
   * Handles the selection of the Premium insurance option.
   *
   * @param event the mouse event triggering this action
   */
  @FXML
  void onSelectPremium(MouseEvent event) {
    insuranceManage.setStrategy(new PremiumCoverStrategy());
    updateInsuranceDetails();
    setSelectedButton(PremiumBtn);
  }

  /**
   * Handles the selection of the Limited insurance option.
   *
   * @param event the mouse event triggering this action
   */
  @FXML
  void onSelectLimited(MouseEvent event) {
    insuranceManage.setStrategy(new LimitedCoverStrategy());
    updateInsuranceDetails();
    setSelectedButton(limitedBtn);
  }

  /** Updates the insurance details displayed in the UI. */
  private void updateInsuranceDetails() {
    reservationManager.setInsuranceType(insuranceManage.getStrategy().getInsuranceTypeName());
    reservationManager.setInsuranceCost(insuranceManage.getDailyCost());
    reservationManager.updateTotalAmount();
    insuranceDetailsLbl.setText(insuranceManage.getDescription());
  }

  /**
   * Sets the selected state for the specified button and updates the UI accordingly.
   *
   * @param selectedButton the button to set as selected
   */
  private void setSelectedButton(Button selectedButton) {
    resetButtonStyles();
    setButtonAsSelected(selectedButton);
  }

  /** Resets the styles for all insurance option buttons to their default state. */
  private void resetButtonStyles() {
    resetButtonStyle(BasicBtn);
    resetButtonStyle(PremiumBtn);
    resetButtonStyle(limitedBtn);
  }

  /**
   * Resets the style for a specific button to its default state.
   *
   * @param button the button to reset
   */
  private void resetButtonStyle(Button button) {
    button.getStyleClass().remove("selected-button");
    button.getStyleClass().add("button-insurance");
    button.setText("SELECT");
  }

  /**
   * Sets the specified button as selected and updates its style.
   *
   * @param button the button to set as selected
   */
  private void setButtonAsSelected(Button button) {
    button.getStyleClass().remove("button-insurance");
    button.getStyleClass().add("selected-button");
    button.setText("SELECTED");
  }
}
