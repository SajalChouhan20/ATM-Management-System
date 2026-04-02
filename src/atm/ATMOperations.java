package atm;
import java.sql.*;

public class ATMOperations {

    public static User login(String card, String pin) {
        try {
            Connection con = DBConnection.getConnection();

            String hashedPin = SecurityUtil.hashPin(pin);

            String query = "SELECT * FROM users WHERE card_number=? AND pin=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, card);
            ps.setString(2, pin);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getString("card_number"),
                    rs.getString("pin"),
                    rs.getDouble("balance")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateBalance(User user) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "UPDATE users SET balance=? WHERE card_number=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setDouble(1, user.balance);
            ps.setString(2, user.cardNumber);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deposit(User user, double amount) {
        user.balance += amount;
        updateBalance(user);
        saveTransaction(user, "DEPOSIT", amount);
        System.out.println("Deposited Successfully!");
    }

    public static void withdraw(User user, double amount) {
        if (amount > user.balance) {
            System.out.println("Insufficient Balance!");
        } else {
            user.balance -= amount;
            updateBalance(user);
            saveTransaction(user, "WITHDRAW", amount);
            System.out.println("Withdraw Successful!");
        }
    }

    public static void checkBalance(User user) {
        System.out.println("Balance: " + user.balance);
    }

    public static void saveTransaction(User user, String type, double amount) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO transactions (card_number, type, amount) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, user.cardNumber);
            ps.setString(2, type);
            ps.setDouble(3, amount);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showTransactions(User user) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM transactions WHERE card_number=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, user.cardNumber);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                    rs.getString("type") + " | " +
                    rs.getDouble("amount") + " | " +
                    rs.getTimestamp("date")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}