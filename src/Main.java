import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Account newAccount = new Account("Teszt", "Teszt123", 5);
        Account newAccount2 = new Account("Teszt2", "Teszt123", 500);
        Account newAccount3 = new Account("Teszt3", "Teszt123", 0);

        ArrayList<Account> accounts = Account.getAccounts();

        System.out.println(accounts.get(2).name);
        System.out.println(accounts.get(2).isValid());
        System.out.println(accounts.get(2).getBalance());
        System.out.println(Account.getAccountByName("Teszt2").getBalance());
        System.out.println(Account.getAccountByID(0).getBalance());

        System.out.println("=======================================");
        System.out.println("Please enter your bank username:");
        Scanner sc = new Scanner(System.in);

        String username = sc.nextLine();
        Account account = Account.getAccountByName(username);
        if (account != null) {
            System.out.println("Welcome, " + account.getName() + "!");
            System.out.println("Use 'B' for getting your Balance");
            System.out.println("Use 'C' for changing your Balance");
            System.out.println("Use 'P' for changing your Password");
            System.out.println("Use 'T' for transfer money to other Account");
            System.out.println("Use 'D' to deactivate your Account");
            System.out.println("Use 'L' to logout from your Account\n");
            System.out.println("Press enter the task ID below: ");

            char c = sc.next().charAt(0);

            switch (c) {
                case 'B':
                    System.out.println("Your balance is: " + account.getBalance());
                    break;
                case 'C':
                    System.out.println("Please enter below the new amount:");
                    //INNEN FOLYTASD!!!
                    break;
                case 'P':
                    break;
                case 'T':
                    break;
                case 'D':
                    break;
                case 'L':
                    break;
            }

            sc.close();
        } else {
            System.out.println("Account " + username + " doesn't exist in our database!");
        }
    }
}