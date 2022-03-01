package Project0;

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
        //Customer c = arraylist<username>

        // alternatively, store it all in an ArrayList of type arrayList<Customer>


        // READ FILE


        String CustUsername;
        String CustPassword;
        Boolean AccountTypeSingle;

        HashMap<String, Customer> usernames = new HashMap<String, Customer>();




        // Welcome Page
        // Outputs "Customer or Employee?"

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Sydney Central Bank");
        String welcome = "";
        while (!welcome.equals("c") && !welcome.equals("e")) {
            System.out.println("Are you a Customer (Enter 'c'), or an Employee (Enter 'e')?");
            welcome = sc.next();
        };
        if (welcome .equals("c")) {
            System.out.println("You are a customer");

            // Customer Homepage
            // Output "Register or Login?"
            // Accept input and direct user

            //Scanner sc = new Scanner(System.in);

            System.out.println("Welcome to Customer Homepage");
            String custLogin = "";
            while (!custLogin.equals("l") && !custLogin.equals("r")) {
                System.out.println("Login? (Enter 'l'), or Register? (Enter 'r')?");
                custLogin = sc.next();
            };
            if (custLogin.equals("l")) {
                //CustomerLogin();
                // send to Customer Login







            }

            if (custLogin.equals("r")) {

                Customer x = CustomerRegistration();
                usernames.put(x.username, x);
                //CustomerRegistration(); // Add to arraylist
                // send to Customer Registration

                CustomerLogin(usernames);





            }

            //CustomerHomepage();
        } // Send to Customer Homepage

        if (welcome .equals("e")) {
            System.out.println("You are an employee");
            EmployeeLogin();
        } // Send to Employee Login














        //WelcomePage();

        // Logout
        // Returns to Welcome Page


        // WRITE TO FILE

    } // End Main Method

    public static void WelcomePage() {

    }

    public static void CustomerHomepage() {



    }

    public static void CustomerLogin(HashMap<String, Customer> usernames) {

        Scanner sc = new Scanner(System.in);

        // Customer Login
        System.out.println("Welcome to Customer Login");
        // Accepts established credentials and compares to saved value
        System.out.println("Enter username: ");
        // needs to search a list or map for the username
        // if arraylist contains username...
        String testingUsername = sc.next();
        // if people<> contains testingUsername...
        if (usernames.containsKey(testingUsername)) {
            System.out.println("Enter password: ");
            if (sc.next().equals(usernames.get(testingUsername).password)) {
                System.out.println("Welcome customer!");
                //AccountBalancePage(c);
                // Send to account balance page





            }
            else {
                System.out.println("Incorrect");
                WelcomePage();
            }
        }
        else {
            System.out.println("Incorrect");
            WelcomePage();
        }
        // Returns Login (sends to account balance page) or Fail

    }

    public static Customer CustomerRegistration() {

        Scanner sc = new Scanner(System.in);

        Customer c = new Customer();
        // Customer Registration
        // Accepts credentials and saves them
        System.out.println("Enter new username: ");
        c.username = sc.next();
        System.out.println("Enter new password: ");
        c.password = sc.next();
        // Asks whether they want Single or Joint account type
        System.out.println("Do you want a Single Account (enter s) or Joint Account (enter any other key)? ");
        if (sc.next().equals("s")) {
            c.accountType = true;
        }
        else {
            c.accountType = false;
        }

        // needs to create a new instance of Customer class
        // add new customer to map

        return c;

        //CustomerLogin();

    }

    public static void AccountBalancePage(Customer c) {

        Scanner sc = new Scanner(System.in);

        // Account Balance Page
        // Displays account balances
        System.out.println("Welcome " + c.username);
        System.out.println("You balance is " + c.balance);
        // Offers Deposit, Withdraw, or Transfer options, Logout
        System.out.println("How much would you like to deposit?");
        c.deposit(sc.nextDouble());

        System.out.println("How much would you like to withdraw?");
        c.withdraw(sc.nextDouble());

        System.out.println("You balance is " + c.balance);

        //WelcomePage(c);

    }

    public static void EmployeeLogin() {

        Scanner sc = new Scanner(System.in);

        String EmpUsername = "Admin";
        String EmpPassword = "admin";

        // Employee Login
        System.out.println("Welcome to Employee Login");
        // Accepts credentials: "Admin" and "admin" or something
        System.out.println("Enter username: ");
        if (sc.next().equals(EmpUsername)) {
            System.out.println("Enter password: ");
            if (sc.next().equals(EmpPassword)) {
                System.out.println("Welcome Employee!");
                EmployeePage();
                // Send to Employee page
            }
            else {
                System.out.println("Incorrect");
                WelcomePage();
            }
        }
        else {
            System.out.println("Incorrect");
            WelcomePage();
        }
    }

    public static void EmployeePage() {
        // Can view, edit, approve/deny Customer Accounts, Logout
    }

}
