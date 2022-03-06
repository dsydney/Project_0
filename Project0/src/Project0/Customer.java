package Project0;

import java.io.Serializable;

public class Customer implements Serializable {

    boolean cancelled = false;
    boolean approved = false;
    String username;
    String password;
    boolean accountType;
    double balance;

    // Deposit
    void deposit(double depositAmount) {
        balance += depositAmount;
        //return balance;
    }

    // Withdraw
    void withdraw(double withdrawAmount) {
        balance -= withdrawAmount;
        //return balance;
    }

    // Transfer
    void transfer(double transferAmount, Customer c) {
        balance -= transferAmount;
        c.balance += transferAmount;
        //return balance;
    }

}
