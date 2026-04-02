package atm;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== ATM LOGIN =====");

        System.out.print("Enter Card Number: ");
        String card = sc.nextLine();

        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        User user = ATMOperations.login(card, pin);

        if (user == null) {
            System.out.println("Invalid Credentials!");
            return;
        }

        while (true) {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    ATMOperations.checkBalance(user);
                    break;
                case 2:
                    System.out.print("Enter Amount: ");
                    ATMOperations.deposit(user, sc.nextDouble());
                    break;
                case 3:
                    System.out.print("Enter Amount: ");
                    ATMOperations.withdraw(user, sc.nextDouble());
                    break;
                case 4:
                    ATMOperations.showTransactions(user);
                    break;
                case 5:
                    System.out.println("Thank you!");
                    return;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}