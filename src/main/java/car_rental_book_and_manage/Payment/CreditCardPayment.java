package car_rental_book_and_manage.Payment;

public class CreditCardPayment extends CardPayment {

    public CreditCardPayment(String cardNumber, String cardHolderName, String cvv, String expiryDate, double amount) {
        super(cardNumber, cardHolderName, cvv, expiryDate, "Credit Card", amount);
    }
}
