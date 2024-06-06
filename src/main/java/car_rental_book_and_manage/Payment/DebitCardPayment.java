package car_rental_book_and_manage.Payment;

public class DebitCardPayment extends CardPayment {

    public DebitCardPayment(String cardNumber, String cardHolderName, String cvv, String expiryDate, double amount) {
        super(cardNumber, cardHolderName, cvv, expiryDate, "Debit Card", amount);
    }
}
