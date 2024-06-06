package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.App;
import car_rental_book_and_manage.Payment.CardPayment;
import car_rental_book_and_manage.Payment.CreditCardPayment;
import car_rental_book_and_manage.Payment.DebitCardPayment;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

  @FXML
  void initialize() {
    informationBox.setDisable(true);
    addTextListeners();
    updateProceedButtonState();
  }

  @FXML
  void onCredit(MouseEvent event) {
    selectPaymentMethod(creditBlock, "Credit Card");
  }

  @FXML
  void onDebit(MouseEvent event) {
    selectPaymentMethod(debitBlock, "Debit Card");
  }

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

  private void addTextListeners() {
    ChangeListener<String> listener =
        new ChangeListener<String>() {
          @Override
          public void changed(
              ObservableValue<? extends String> observable, String oldValue, String newValue) {
            updateProceedButtonState();
          }
        };

    cardNameLbl.textProperty().addListener(listener);
    cardNoLbl.textProperty().addListener(listener);
    cvvLbl.textProperty().addListener(listener);
    expiryDateLbl.textProperty().addListener(listener);
  }

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
   * Handles the Go Back button click event. Switches the scene back to the Find Vehicles scene.
   *
   * @param event the mouse event triggering this action
   */
  @FXML
  void onGoBack(MouseEvent event) {
    App.setUi(Scenes.INSURANCE);
  }

  /**
   * Handles the Proceed button click event. Creates and saves the reservation, updates vehicle
   * availability, and switches to the Confirmation scene.
   *
   * @param event the mouse event triggering this action
   */
  @FXML
  void onProceed(MouseEvent event) {
    // Retrieve user input
    String cardNumber = cardNoLbl.getText();
    String cardHolderName = cardNameLbl.getText();
    String cvv = cvvLbl.getText();
    String expiryDate = expiryDateLbl.getText();
    double amount =
        calculateAmount(); // You will need to implement this method to calculate the total amount

    // Create a CardPayment object
    CardPayment payment;
    if (selectedPaymentType.equals("Credit Card")) {
      payment = new CreditCardPayment(cardNumber, cardHolderName, cvv, expiryDate, amount);
    } else {
      payment = new DebitCardPayment(cardNumber, cardHolderName, cvv, expiryDate, amount);
    }

    // Save the payment to the database (you will need to implement this part)
    savePayment(payment);

    App.resetAllDatePickers();
    App.setUi(Scenes.CONFIRMATION);
  }

  private void savePayment(CardPayment payment) {
    // Implement the logic to save the payment information to the database
  }

  private double calculateAmount() {
    // Implement the logic to calculate the total amount
    return 0.0; // Example value, replace with actual calculation
  }
}
