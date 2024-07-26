package car_rental_book_and_manage.Client.Controllers;

import car_rental_book_and_manage.Client.App;
import car_rental_book_and_manage.Client.ClientUtility.AlertManager;
import car_rental_book_and_manage.Client.ClientUtility.ErrorHandlingUtil;
import car_rental_book_and_manage.Client.ClientUtility.HttpClientUtil;
import car_rental_book_and_manage.Client.ClientUtility.SceneManager.Scenes;
import car_rental_book_and_manage.SharedObject.Client;
import car_rental_book_and_manage.SharedObject.Payment.CardPayment;
import car_rental_book_and_manage.SharedObject.Payment.CreditCardPayment;
import car_rental_book_and_manage.SharedObject.Payment.DebitCardPayment;
import car_rental_book_and_manage.SharedObject.Reservation;
import car_rental_book_and_manage.SharedObject.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/** Controller for handling the payment process in the car rental system. */
public class PaymentController extends Controller {

  @FXML private FontAwesomeIconView card;
  @FXML private TextField cardNameLbl;
  @FXML private TextField cardNoLbl;
  @FXML private Label clientNameLbl;
  @FXML private AnchorPane creditBlock;
  @FXML private TextField cvvLbl;
  @FXML private AnchorPane debitBlock;
  @FXML private TextField expiryDateLbl;
  @FXML private Label informationLbl;
  @FXML private Button proceedBtn;
  @FXML private VBox informationBox;

  private AnchorPane selectedBlock;
  private String selectedPaymentType;

  private ObjectMapper objectMapper = new ObjectMapper();

  /** Initializes the controller by setting up necessary bindings and listeners. */
  @FXML
  void initialize() {
    clientNameLbl.textProperty().bind(dataModel.loggedInClientName());
    informationBox.setDisable(true);
    addTextListeners();
    updateProceedButtonState();
  }

  /**
   * Handles the selection of the credit card payment method.
   *
   * @param event the mouse event triggering this action
   */
  @FXML
  void onCredit(MouseEvent event) {
    selectPaymentMethod(creditBlock, "Credit Card");
  }

  /**
   * Handles the selection of the debit card payment method.
   *
   * @param event the mouse event triggering this action
   */
  @FXML
  void onDebit(MouseEvent event) {
    selectPaymentMethod(debitBlock, "Debit Card");
  }

  /**
   * Selects a payment method and updates the UI accordingly.
   *
   * @param block the UI block representing the payment method
   * @param paymentType the type of payment method selected
   */
  private void selectPaymentMethod(AnchorPane block, String paymentType) {
    if (selectedBlock != null) {
      selectedBlock.getStyleClass().remove("selected");
    }
    selectedBlock = block;
    selectedBlock.getStyleClass().add("selected");
    selectedPaymentType = paymentType;
    informationBox.setDisable(false);
    updateProceedButtonState();
  }

  /** Adds listeners to text fields to monitor changes and update the proceed button state. */
  private void addTextListeners() {
    ChangeListener<String> listener =
        (observable, oldValue, newValue) -> updateProceedButtonState();
    cardNameLbl.textProperty().addListener(listener);
    cardNoLbl.textProperty().addListener(listener);
    cvvLbl.textProperty().addListener(listener);
    expiryDateLbl.textProperty().addListener(listener);
  }

  /**
   * Updates the state of the proceed button based on the input fields and selected payment method.
   */
  private void updateProceedButtonState() {
    boolean isDisabled =
        cardNameLbl.getText().isEmpty()
            || cardNoLbl.getText().isEmpty()
            || cvvLbl.getText().isEmpty()
            || expiryDateLbl.getText().isEmpty()
            || selectedPaymentType == null;
    proceedBtn.setDisable(isDisabled);
  }

  /**
   * Handles the Go Back button click event. Switches the scene back to the Insurance scene.
   *
   * @param event the mouse event triggering this action
   */
  @FXML
  void onGoBack(MouseEvent event) {
    App.setUi(Scenes.INSURANCE);
  }

  /**
   * Handles the Proceed button click event. Creates and saves the reservation and payment, updates
   * vehicle availability, and switches to the Confirmation scene.
   *
   * @param event the mouse event triggering this action
   */
  @FXML
  void onProceed(MouseEvent event) {
    Reservation newReservation = createReservationObject();
    CardPayment payment = createPaymentObject(newReservation);

    boolean isSuccess = saveReservationAndPayment(newReservation, payment);

    if (isSuccess) {
      updateVehicleAvailability(newReservation.getVehicleId(), false);
      resetFields();
      App.resetAllDatePickers();
      App.setUi(Scenes.CONFIRMATION);
    }
  }

  /**
   * Creates a CardPayment object based on the selected payment type.
   *
   * @param reservation the reservation for which the payment is being made
   * @return a CardPayment object
   */
  private CardPayment createPaymentObject(Reservation reservation) {
    String cardNumber = cardNoLbl.getText();
    String cardHolderName = cardNameLbl.getText();
    String cvv = cvvLbl.getText();
    String expiryDate = expiryDateLbl.getText();
    double amount = Double.parseDouble(reservationManager.getDailyTotal());
    LocalDate paymentDate = LocalDate.now();
    int clientId = reservation.getClientId();
    int rentalId = reservation.getReservationId();

    if (selectedPaymentType.equals("Credit Card")) {
      return new CreditCardPayment(
          cardNumber, cardHolderName, cvv, expiryDate, amount, rentalId, clientId, paymentDate);
    } else {
      return new DebitCardPayment(
          cardNumber, cardHolderName, cvv, expiryDate, amount, rentalId, clientId, paymentDate);
    }
  }

  /**
   * Saves the reservation and payment to the database.
   *
   * @param reservation the reservation to save
   * @param payment the payment to save
   * @return boolean indicating success or failure
   */
  private boolean saveReservationAndPayment(Reservation reservation, CardPayment payment) {
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("reservation", reservation);
    requestBody.put("payment", payment);

    try {
      HttpClientUtil.sendPostRequest("http://localhost:8000/api/reservations", requestBody);
      reservationManager.setCurrentReservation(reservation);

      // Fetch the vehicle details from the server
      String vehicleResponse =
          HttpClientUtil.sendGetRequest(
              "http://localhost:8000/api/vehicles/" + reservation.getVehicleId());
      Vehicle vehicle = objectMapper.readValue(vehicleResponse, Vehicle.class);
      reservationManager.setSelectedVehicle(vehicle);

      AlertManager.showAlert(
          AlertType.CONFIRMATION, "Reservation", "Reservation saved successfully");
      return true;
    } catch (Exception e) {
      ErrorHandlingUtil.handleServerErrors(e.getMessage(), "Reservation Error", AlertType.WARNING);
      return false;
    }
  }

  /**
   * Creates a new Reservation object with the current reservation details.
   *
   * @return a new Reservation object
   */
  private Reservation createReservationObject() {
    Vehicle selectedVehicle = reservationManager.getSelectedVehicle();
    Client loggedInClient = reservationManager.getLoggedInClient();
    LocalDate pickUpDate = reservationManager.getPickUpDate();
    LocalDate returnDate = reservationManager.getReturnDate();
    double totalRate = Double.parseDouble(reservationManager.getTotalAmount());
    String insuranceType = reservationManager.getInsuranceType();

    return new Reservation(
        loggedInClient.getClientId(),
        selectedVehicle.getVehicleId(),
        totalRate,
        selectedVehicle.getLicensePlate(),
        loggedInClient.getLicenseNo(),
        pickUpDate,
        returnDate,
        insuranceType);
  }

  /**
   * Updates the availability status of the selected vehicle.
   *
   * @param vehicleId the ID of the vehicle to update
   * @param available the new availability status
   */
  private void updateVehicleAvailability(int vehicleId, boolean available) {
    try {
      String url =
          "http://localhost:8000/api/vehicles/"
              + vehicleId
              + "/availability?available="
              + available;
      HttpClientUtil.sendPutRequest(url, null);
      System.out.println("Vehicle availability updated on the server.");
    } catch (Exception e) {
      ErrorHandlingUtil.handleServerErrors(
          e.getMessage(), "Vehicle Availability Error", AlertType.WARNING);
    }
  }

  /** Resets all input fields and state variables. */
  private void resetFields() {
    cardNameLbl.clear();
    cardNoLbl.clear();
    cvvLbl.clear();
    expiryDateLbl.clear();
    informationBox.setDisable(true);
    selectedBlock.getStyleClass().remove("selected");
    selectedBlock = null;
    selectedPaymentType = null;
    updateProceedButtonState();
  }

  /**
   * Calls the base class sign-out method and resets the payment-specific fields.
   *
   * @param event the mouse event triggering the sign-out
   */
  @Override
  protected void onSignOut(MouseEvent event) {
    super.onSignOut(event);
    resetFields();
  }
}
