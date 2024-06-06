package car_rental_book_and_manage.Payment;

import java.time.LocalDate;

/** Class representing a credit card payment. */
public class CreditCardPayment extends CardPayment {

  /**
   * Constructs a CreditCardPayment instance.
   *
   * @param cardNumber the card number
   * @param cardHolderName the name of the cardholder
   * @param cvv the CVV code of the card
   * @param expiryDate the expiry date of the card
   * @param amount the amount to be paid
   * @param rentalId the rental ID associated with the payment
   * @param clientId the client ID associated with the payment
   * @param paymentDate the date of the payment
   */
  public CreditCardPayment(
      String cardNumber,
      String cardHolderName,
      String cvv,
      String expiryDate,
      double amount,
      int rentalId,
      int clientId,
      LocalDate paymentDate) {
    super(
        cardNumber,
        cardHolderName,
        cvv,
        expiryDate,
        "Credit Card",
        amount,
        rentalId,
        clientId,
        paymentDate);
  }
}
