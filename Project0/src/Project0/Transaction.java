package Project0;

import java.io.Serializable;

public class Transaction implements Serializable {

    public String username;
    public String transactionType;
    public String targetUser;
    public double transactionAmount;

    public Transaction (String username, String transactionType, String targetUser, double transactionAmount) {

        this.username = username;
        this.transactionType = transactionType;
        this.targetUser = targetUser;
        this.transactionAmount = transactionAmount;

    }

}
