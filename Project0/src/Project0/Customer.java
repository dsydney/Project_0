package Project0;

public class Customer {

    String username;
    String password;
    boolean accountType;
    double balance;

    // Deposit
    double deposit(Double depositAmount) {
        balance += depositAmount;
        return balance;
    }

    // Withdraw
    double withdraw(Double withdrawAmount) {
        balance -= withdrawAmount;
        return balance;
    }

    // Transfer

}
