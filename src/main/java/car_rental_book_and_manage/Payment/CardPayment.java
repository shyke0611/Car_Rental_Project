package car_rental_book_and_manage.Payment;

import java.time.LocalDate;

/** Abstract class representing a card payment. */
public abstract class CardPayment {
  private String cardNumber;
  private String cardHolderName;
  private String cvv;
  private String expiryDate;
  private String paymentType;
  private double amount;
  private int rentalId;
  private int clientId;
  private LocalDate paymentDate;

  /**
   * Constructs a CardPayment instance.
   *
   * @param cardNumber the card number
   * @param cardHolderName the name of the cardholder
   * @param cvv the CVV code of the card
   * @param expiryDate the expiry date of the card
   * @param paymentType the type of payment (e.g., Credit Card, Debit Card)
   * @param amount the amount to be paid
   * @param rentalId the rental ID associated with the payment
   * @param clientId the client ID associated with the payment
   * @param paymentDate the date of the payment
   */
  public CardPayment(
      String cardNumber,
      String cardHolderName,
      String cvv,
      String expiryDate,
      String paymentType,
      double amount,
      int rentalId,
      int clientId,
      LocalDate paymentDate) {
    this.cardNumber = cardNumber;
    this.cardHolderName = cardHolderName;
    this.cvv = cvv;
    this.expiryDate = expiryDate;
    this.paymentType = paymentType;
    this.amount = amount;
    this.rentalId = rentalId;
    this.clientId = clientId;
    this.paymentDate = paymentDate;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getCardHolderName() {
    return cardHolderName;
  }

  public void setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
  }

  public String getCvv() {
    return cvv;
  }

  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public int getRentalId() {
    return rentalId;
  }

  public void setRentalId(int rentalId) {
    this.rentalId = rentalId;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public LocalDate getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDate paymentDate) {
    this.paymentDate = paymentDate;
  }
}
