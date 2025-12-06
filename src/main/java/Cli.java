import java.math.BigDecimal;
import java.util.Scanner;


public class Cli {
    private final BankSystem bank = new BankSystem();
    private final Scanner myObj = new Scanner(System.in);

    public Cli() {
        start();
    }

    public void start() {
        System.out.println("Bank System Initialized");
        System.out.println("------------------------------------");
        System.out.println("Welcome to Bank System");
        System.out.println("------------------------------------");
        System.out.println("Please login or create an account to continue");
        login();
        System.out.println("------------------------------------");
        System.out.println("Thank you for using our service");
        }
    public void login() {
        boolean flag = true;
        while (flag) {
            System.out.println("Please login or create an account to continue");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int x = myObj.nextInt();
            myObj.nextLine();
            switch (x) {
                case 1:
                    System.out.println("Enter Name");
                    String newname = myObj.nextLine();
                    System.out.println("Enter Password");
                    String newpassword = myObj.nextLine();
                    System.out.println("Enter Email");
                    String newemail = myObj.nextLine();
                    System.out.println("Enter Phone");
                    String newphone = myObj.nextLine();
                    System.out.println("Enter Initial Balance");
                    BigDecimal newbalance = myObj.nextBigDecimal();
                    while (newbalance.compareTo(BigDecimal.ZERO) < 0) {
                        System.out.println("Balance cannot be negative, please enter a valid balance");
                        newbalance = myObj.nextBigDecimal();
                    }
                    if (bank.addAccount(newname, newpassword, newemail, newphone, newbalance)) {
                        System.out.println("Account created successfully");
                    } else {
                        System.out.println("Account creation failed");
                    }
                    break;
                case 2:
                    System.out.println("Enter Name");
                    String name = myObj.nextLine();
                    System.out.println("Enter Password");
                    String password = myObj.nextLine();
                    if (bank.authenticate(name, password)) {
                        Account a = bank.getAccount(name);
                        if(a != null) {
                            loggedIn(a);
                        } else {
                            System.out.println("account does not exist");
                        }
                    } else {
                        System.out.println("account does not exist or wrong password");
                    }
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }
    public void loggedIn(Account a) {
        boolean flag = true;
        while (flag) {
            System.out.println("Welcome " + a.getName());
            System.out.println("------------------------------------");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Balance");
            System.out.println("4. View Transactions");
            System.out.println("5. Logout");
            System.out.println("------------------------------------");
            int x = myObj.nextInt();
            myObj.nextLine();
            switch (x) {
                case 1:
                    System.out.println("Enter Amount to Deposit");
                    BigDecimal d = myObj.nextBigDecimal();
                    myObj.nextLine();
                    while (d.compareTo(BigDecimal.ZERO) <= 0 ) {
                        System.out.println("Amount cannot be negative or zero, please enter a valid amount");
                        d =  myObj.nextBigDecimal();
                    }
                    if (a.deposit(d)) {
                        System.out.println("Deposit Successful");
                    } else {
                        System.out.println("Deposit Failed");
                    }
                    break;
                case 2:
                    System.out.println("Enter Amount to Withdraw");
                    BigDecimal w =  myObj.nextBigDecimal();
                    myObj.nextLine();
                    while (w.compareTo(BigDecimal.ZERO) <= 0) {
                        System.out.println("Amount cannot be negative or zero, please enter a valid amount");
                        w =  myObj.nextBigDecimal();
                    }
                    if (a.withdraw(w)) {
                        System.out.println("Withdraw Successful");
                    } else {
                        System.out.println("Withdraw Failed");
                    }
                    break;
                case 3:
                    System.out.println("Balance: " + a.getBalance());
                    break;
                case 4:
                    for (Transaction t : a.getTransactions()) {
                        System.out.println(t.toString());
                    }
                case 5:
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }

        }
    }
}