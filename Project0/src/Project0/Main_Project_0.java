package Project0;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main_Project_0 {
/*
    # Project 0: Banking App

### Part 1
            **Description**

    Leveraging Java 8, create an application that simulates simple banking transactions

**Requirements**
            *	Build the application using Java
*	All interaction with the user should be done through the console using the `Scanner` class
*	Customers of the bank should be able to register with a username and password, and apply to open an account.
  * Customers should be able to apply for joint accounts
*	Once the account is open, customers should be able to withdraw, deposit, and transfer funds between accounts
  * All basic validation should be done, such as trying to input negative amounts, overdrawing from accounts etc.
*	Employees of the bank should be able to view all of their customer's information. This includes:
            * Account information
  * Account balances
  * Personal information
*	Employees should be able to approve/deny open applications for accounts
*	Bank admins should be able to view and edit all accounts
  * This includes:
            * Approving/denying accounts
  * withdrawing, depositing, transferring from all accounts
  * canceling accounts
*	All information should be persisted using text files and serialization

 */

    // Main Method
    public static void main(String[] args) {

        // Create a map to store customer usernames (key) and their Object name (value)
        // When customers login, check the map to see if the customer exists in the map, and then
        // use their Object name to import their password, balance, and account type

        HashMap<String, Customer> usernamesAndCustomerInfo = new HashMap<>();
        ArrayList<Transaction> arrayListOfTransactions = new ArrayList<>();

        if (ReadFile()!=null) {

            usernamesAndCustomerInfo = ReadFile();

        }

        if (ReadListOfTransactions()!=null) {

            arrayListOfTransactions = ReadListOfTransactions();

        }

        // Welcome Page
        // Outputs "Customer or Employee?"

        Scanner sc = new Scanner(System.in);

        System.out.println("-----------------------------\n\nWelcome to Sydney Central Bank\n\n");

        String welcome = "";

        while (!welcome.equals("1") && !welcome.equals("2")) {

            System.out.println("Are you a Customer, or an Employee?\n\n(1) Customer\n(2) Employee");

            welcome = sc.next();

        }

        if (welcome.equals("1")) {

            System.out.println("\n----------Customer Homepage----------\n");

            String custLogin = "";

            while (!custLogin.equals("1") && !custLogin.equals("2")) {

                System.out.println("(1) Login\n(2) Register");

                custLogin = sc.next();

            }
            if (custLogin.equals("1")) {

                CustomerLogin(usernamesAndCustomerInfo, arrayListOfTransactions);

            }

            if (custLogin.equals("2")) {

                Customer newCustomer = CustomerRegistration();
                usernamesAndCustomerInfo.put(newCustomer.username, newCustomer);
                WriteToFile(usernamesAndCustomerInfo);

                main(null);
                //CustomerLogin(usernamesAndCustomerInfo, arrayListOfTransactions);

            }

        }

        if (welcome.equals("2")) {

            EmployeeLogin(usernamesAndCustomerInfo, arrayListOfTransactions);
        }

    } // End Main Method

    public static void WriteToFile(HashMap<String, Customer> c) {

        try {
            FileOutputStream fileOut = new FileOutputStream("ListOfCustomers.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(c);
            out.close();
            fileOut.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addTransaction(ArrayList<Transaction> t, String username, String transactionType, String targetUser, double transactionAmount) {

        Transaction transaction = new Transaction(username, transactionType, targetUser, transactionAmount);
        t.add(transaction);
        logTransaction(t);
    }

    public static void logTransaction(ArrayList<Transaction> t) {

        try {
            FileOutputStream fileOut = new FileOutputStream("ListOfTransactions.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(t);
            out.close();
            fileOut.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Transaction> ReadListOfTransactions() {

        ArrayList<Transaction> t = null;

        try {
            FileInputStream fileIn = new FileInputStream("ListOfTransactions.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            t = (ArrayList<Transaction>) in.readObject();
            in.close();
            fileIn.close();
        }
        catch (IOException e) {}
        catch (ClassNotFoundException ee) {
            ee.printStackTrace();
        } // End Try Block

        return t;

    }

    public static HashMap<String, Customer> ReadFile() {

        HashMap<String, Customer> p = null;

        try {
            FileInputStream fileIn = new FileInputStream("ListOfCustomers.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            p = (HashMap<String, Customer>) in.readObject();
            in.close();
            fileIn.close();
        }
        catch (IOException e) {}
        catch (ClassNotFoundException ee) {
            ee.printStackTrace();
        } // End Try Block

        return p;

    }

    public static void CustomerLogin(HashMap<String, Customer> usernamesAndCustomerInfo, ArrayList<Transaction> arrayListOfTransactions) {

        Scanner sc = new Scanner(System.in);

        if (ReadFile()==null) {

            System.out.println("\nNo Customers yet\n");
            main(null);

        } else {

            usernamesAndCustomerInfo = ReadFile();

        }

        // Customer Login
        // Accepts established credentials and compares to saved value
        System.out.print("Enter username: ");

        String testingUsername = sc.next();

        if (usernamesAndCustomerInfo.containsKey(testingUsername)) {
            System.out.print("\nEnter password: ");
            if (sc.next().equals(usernamesAndCustomerInfo.get(testingUsername).password)) {

                AccountBalancePage(usernamesAndCustomerInfo.get(testingUsername), usernamesAndCustomerInfo, arrayListOfTransactions);
                // Send to account balance page

            }

            else {

                System.out.println("Incorrect");

                CustomerLogin(usernamesAndCustomerInfo, arrayListOfTransactions);

            }

        }

        else {

            System.out.println("Incorrect");

            main(null);

        }

    }

    public static Customer CustomerRegistration() {
        // Accepts credentials and saves them

        Scanner sc = new Scanner(System.in);

        Customer c = new Customer();

        System.out.println("Enter new username: ");

        c.username = sc.next();

        System.out.println("Enter new password: ");

        c.password = sc.next();

        System.out.println("Do you want a Single Account (enter s) or Joint Account (enter any other key)? ");

        c.accountType = sc.next().equals("s");

        System.out.println("\nThank you for applying for a new account. Your application will be reviewed for approval\n");

        return c;

    }

    public static void AccountBalancePage(Customer c, HashMap<String, Customer> x, ArrayList<Transaction> t) {

        System.out.println("\n-----" + c.username + "'s Account-----\n");

        // Check to see if customer is approved
        if (c.cancelled) {
            System.out.println("Your account has been cancelled");
            System.exit(0);
        }
        else if (!c.approved) {
            System.out.println("Your account has not been approved yet\n\n");
            main(null);
        }

        Scanner sc = new Scanner(System.in);

        // Account Balance Page
        // Displays account balances
        System.out.println("You balance is " + c.balance);
        // Offers Deposit, Withdraw, or Transfer options, Logout
        System.out.println("(1) Deposit\n(2) Withdraw\n(3) Transfer\n(4) Logout");

        String userChoice = sc.next();

        switch (userChoice) {
            case "1" -> {
                System.out.println("How much would you like to deposit?");
                try {
                    double amount = Double.parseDouble(sc.next());
                    c.deposit(amount);
                    addTransaction(t, c.username, "deposit", null, amount);
                } catch (Exception e) {
                    System.out.println("Sorry, I didn't recognize that");
                }
                AccountBalancePage(c, x, t);
            }
            case "2" -> {
                System.out.println("How much would you like to withdraw?");
                try {
                    double amount = Double.parseDouble(sc.next());
                    c.withdraw(amount);
                    addTransaction(t, c.username, "withdraw", null, amount);
                } catch (Exception e) {
                    System.out.println("Sorry, I didn't recognize that");
                }
                AccountBalancePage(c, x, t);
            }
            case "3" -> {
                System.out.println("Who would you like to transfer to? (Enter their username: )");
                String targetUser = sc.next();
                System.out.println("How much would you like to transfer? ");
                try {

                    double amount = Double.parseDouble(sc.next());

                    if (x.containsKey(targetUser)) {
                        c.transfer(amount, x.get(targetUser));
                        addTransaction(t, c.username, "transfer", targetUser, amount);
                    } else {
                        System.out.println("Sorry, I didn't recognize that username");
                    }
                } catch (Exception e) {
                    System.out.println("Sorry, I didn't recognize that");
                }
                AccountBalancePage(c, x, t);
            }
            case "4" -> main(null);
            default -> {
                System.out.println("Sorry, I didn't recognize that");
                AccountBalancePage(c, x, t);
            }
        }

        System.out.println("Your balance is " + c.balance);

        x.put(c.username, c);
        WriteToFile(x);

        main(null);

    }

    public static void EmployeeLogin(HashMap<String, Customer> usernamesAndCustomerInfo, ArrayList<Transaction> arrayListOfTransactions) {

        Scanner sc = new Scanner(System.in);

        String EmpUsername = "Admin";
        String EmpPassword = "admin";

        System.out.println("\n--------Employee Login--------\n");

        System.out.print("Enter username: ");

        if (sc.next().equals(EmpUsername)) {

            System.out.print("\nEnter password: ");

            if (sc.next().equals(EmpPassword)) {

                //System.out.println("\nWelcome Employee!");

                EmployeePage(usernamesAndCustomerInfo, arrayListOfTransactions);

            }

            else {

                System.out.println("\nIncorrect password");
                EmployeeLogin(usernamesAndCustomerInfo, arrayListOfTransactions);

            }

        }

        else {

            System.out.println("\nIncorrect\n");
            main(null);

        }

    }

    public static void EmployeePage(HashMap<String, Customer> usernamesAndCustomerInfo, ArrayList<Transaction> arrayListOfTransactions) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n--------Employee Page--------\n");

        System.out.print("(1) Logout\n(2) See all transactions\n(3) View a Customer\n");

        switch (sc.next()) {
            case "1": {

                main(null);

            } case "2": {

                if (ReadListOfTransactions() == null) {

                    System.out.println("\nNo transactions yet");

                    EmployeePage(usernamesAndCustomerInfo, arrayListOfTransactions);

                } else {

                    System.out.println("Reading file...");
                    arrayListOfTransactions = ReadListOfTransactions();

                    System.out.println("\n--------------------------------\n");

                    for (Transaction transaction : arrayListOfTransactions) {

                        System.out.println("Transaction #" + (arrayListOfTransactions.indexOf(transaction)+1));
                        System.out.println("Username: " + transaction.username);
                        System.out.println("Transaction Type: " + transaction.transactionType);
                        System.out.println("Target User, if applicable: " + transaction.targetUser);
                        System.out.println("Amount: " + transaction.transactionAmount);
                        System.out.println("\n--------------------------------\n");

                    }

                }

                EmployeePage(usernamesAndCustomerInfo, arrayListOfTransactions);

            } case "3": {

                if (usernamesAndCustomerInfo.isEmpty()) {

                    System.out.println("\nThere are no customers yet\n");

                    main(null);

                }

                System.out.println("\nWho would you like to view? " + usernamesAndCustomerInfo.keySet());

                String user = sc.next();

                if (usernamesAndCustomerInfo.containsKey(user)) {

                    if (usernamesAndCustomerInfo.get(user).cancelled) { //If the user was already canceled, they are still in the system but cannot be accessed or used

                        System.out.println("\nThis account has been cancelled");

                        EmployeeLogin(usernamesAndCustomerInfo, arrayListOfTransactions);

                    } else if (!usernamesAndCustomerInfo.get(user).approved) { //This block allows any employee to approve a new user

                        System.out.println("\nThis account has not been approved yet. Would you like to approve it? ");

                        System.out.print("Enter your password to approve or press any other key to deny: ");

                        if (sc.next().equals("admin")) {

                            System.out.println("\nUser approved");

                            usernamesAndCustomerInfo.get(user).approved = true;

                            WriteToFile(usernamesAndCustomerInfo);

                        } else {

                            System.out.println("\nUser denied");
                            System.exit(0);

                        }

                    } else { //This block allows the bank manager, but not the bank employee, to cancel any user

                        System.out.println("\nWould you like to Cancel this account? ");

                        System.out.print("Enter bank manager password to cancel or press any other key to continue: ");

                        if (sc.next().equals("admin123")) {

                            System.out.println("\nUser Canceled");

                            usernamesAndCustomerInfo.get(user).cancelled = true;

                            WriteToFile(usernamesAndCustomerInfo);

                            System.exit(0);

                        }

                    }


                    System.out.println("\n" + user + " has a balance of " + usernamesAndCustomerInfo.get(user).balance);

                    System.out.print("\nHow much would you like to deposit?: ");

                    try {
                        double amount = Double.parseDouble(sc.next());
                        usernamesAndCustomerInfo.get(user).deposit(amount);
                        addTransaction(arrayListOfTransactions, usernamesAndCustomerInfo.get(user).username, "deposit", null, amount);
                    } catch (Exception e) {
                        System.out.println("Sorry, I didn't recognize that");
                    }


                    System.out.print("\nHow much would you like to withdraw?: ");

                    try {
                        double amount = Double.parseDouble(sc.next());
                        usernamesAndCustomerInfo.get(user).withdraw(amount);
                        addTransaction(arrayListOfTransactions, usernamesAndCustomerInfo.get(user).username, "withdraw", null, amount);
                    } catch (Exception e) {
                        System.out.println("Sorry, I didn't recognize that");
                    }


                    System.out.println("\nWould you like to transfer?\n(1) Transfer\n(2) Continue");

                    if (sc.next().equals("1")) {

                        System.out.print("\nWho would you like to transfer to? Enter their username: ");

                        String targetUser = sc.next();

                        System.out.print("\nHow much would you like to transfer?: ");

                        double amount;

                        try {

                            amount = Double.parseDouble(sc.next());

                            if (usernamesAndCustomerInfo.containsKey(targetUser)) {

                                usernamesAndCustomerInfo.get(user).transfer(amount, usernamesAndCustomerInfo.get(targetUser));
                                addTransaction(arrayListOfTransactions, usernamesAndCustomerInfo.get(user).username, "transfer", targetUser, amount);

                                System.out.println("\n" + targetUser + " has a new balance of " + usernamesAndCustomerInfo.get(targetUser).balance);

                            } else {

                                System.out.println("\nInvalid entry");

                            }

                        } catch (Exception e) {
                            System.out.println("Sorry, I didn't recognize that");
                        }
                    }

                    System.out.println(user + " has a balance of " + usernamesAndCustomerInfo.get(user).balance);

                    usernamesAndCustomerInfo.put(usernamesAndCustomerInfo.get(user).username, usernamesAndCustomerInfo.get(user));

                    WriteToFile(usernamesAndCustomerInfo);

                    EmployeePage(usernamesAndCustomerInfo, arrayListOfTransactions);

                } else {

                    System.out.println("Invalid entry");

                    EmployeePage(usernamesAndCustomerInfo, arrayListOfTransactions);

                }
            } default :

                System.out.println("\nSorry, I didn't recognize that");

                EmployeePage(usernamesAndCustomerInfo, arrayListOfTransactions);

        }
    }

}

