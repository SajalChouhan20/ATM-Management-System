package atm;

public class User {
    String cardNumber;
    String pin;
    double balance;

    public User(String cardNumber, String pin, double balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }
}