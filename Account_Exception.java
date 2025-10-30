import java.util.Scanner;

class InvalidATMPinException extends Exception {
    public InvalidATMPinException(String message) {
        super(message);
    }
}

class NoCashException extends Exception {
    public NoCashException(String message) {
        super(message);
    }
}

class Account {
    private String name;
    private double balance;
    private int atmPin;
    private boolean isPinVerified;

    public Account(String name, double balance, int atmPin) {
        this.name = name;
        this.balance = balance;
        this.atmPin = atmPin;
        this.isPinVerified = false;
    }

    public void checkPin(int pinEntered) throws InvalidATMPinException {
        if (this.atmPin == pinEntered) {
            this.isPinVerified = true;
            System.out.println("PIN Accepted. Welcome, " + this.name + ".");
        } else {
            this.isPinVerified = false;
            throw new InvalidATMPinException("Invalid ATM PIN entered.");
        }
    }

    public void withdraw(double amount) throws NoCashException, IllegalStateException {
        if (!this.isPinVerified) {
            throw new IllegalStateException("PIN not verified. Please verify PIN before withdrawal.");
        }

        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
        } else if (amount > this.balance) {
            throw new NoCashException("Insufficient funds. Available balance: " + this.balance);
        } else {
            this.balance -= amount;
            System.out.println("Withdrawal successful. Amount withdrawn: " + amount);
            System.out.println("New balance: " + this.balance);
        }
    }
}

public class Account_Exception {
    public static void main(String[] args) {
        Account myAccount = new Account("Steve ", 25000.0, 2006);
        Scanner scanner = new Scanner(System.in);
        
        boolean proceed = false;

        System.out.println("--- Welcome to the ATM ---");

        try {
            System.out.print("Please enter your PIN: ");
            int pinAttempt = scanner.nextInt();
            
            myAccount.checkPin(pinAttempt);
            
            proceed = true;

        } catch (InvalidATMPinException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Transaction Canceled.");
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter numbers only.");
        }
        
        System.out.println("--------------------------");

        if (proceed) {
            try {
                System.out.print("Enter amount to withdraw: ");
                double amount = scanner.nextDouble();
                
                myAccount.withdraw(amount);

            } catch (NoCashException e) {
                System.out.println("ERROR: " + e.getMessage());
                System.out.println("Transaction Failed.");
            } catch (IllegalStateException e) {
                System.out.println("SECURITY ERROR: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid amount.");
            }
        }

        System.out.println("--- Thank you for using the ATM ---");
        scanner.close();
    }
}