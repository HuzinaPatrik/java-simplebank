import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void requestFunctions(Account account, Scanner sc, Boolean ignoreWelcomeText) {
        if (account.isValid()) {
            System.out.println("=======================================");
            if (!ignoreWelcomeText) {
                System.out.println("Welcome, " + account.getName() + "!");
            }
            System.out.println("Use 'B' for getting your Balance");
            System.out.println("Use 'C' for changing your Balance");
            System.out.println("Use 'P' for changing your Password");
            System.out.println("Use 'T' for transfer money to other Account");
            System.out.println("Use 'D' to deactivate your Account");
            System.out.println("Use 'L' to logout from your Account\n");
            System.out.println("Press enter the task ID below: ");
            System.out.println("=======================================");

            char c = sc.next().charAt(0);

            switch (c) {
                case 'B':
                    System.out.println("Your balance is: " + account.getBalance());
                    break;
                case 'C':
                    System.out.println("Please enter below the new amount:");

                    if (sc.hasNextInt()) {
                        int amount = sc.nextInt();

                        account.setBalance(amount);

                        System.out.println("Your new balance is: " + amount);
                    }

                    break;
                case 'P':
                    System.out.println("Please enter below the new password:");

                    sc.nextLine(); //Hotfix cuz sc.nextInt (https://www.educative.io/answers/what-is-scannernextline-in-java)

                    if (sc.hasNextLine()) {
                        String newPassword = sc.nextLine();

                        account.setPassword(newPassword);

                        String starPassword = newPassword.replaceAll(".", "*");

                        System.out.println("Your password changed to: " + starPassword + ", you need to relogin into your account!");

                        loginAccount(sc, true);

                        return;
                    }

                    break;
                case 'T':
                    System.out.println("Please enter below the amount:");

                    if (sc.hasNextInt()) {
                        int amount = sc.nextInt();

                        if (account.hasBalance(amount)) {
                            System.out.println("Please enter below the accountName/ID of who you wanna transfer to:");

                            sc.nextLine(); //Hotfix cuz sc.nextInt (https://www.educative.io/answers/what-is-scannernextline-in-java)

                            if (sc.hasNextLine()) {
                                String targetDetails = sc.nextLine();

                                if (account.transferMoney(targetDetails, amount)) {
                                    System.out.println("Successfully transferred " + amount + " money to: " + targetDetails + "!");

                                    System.out.println("Your new balance is: " + account.getBalance());
                                } else {
                                    System.out.println("Sorry but we couldn't find: " + targetDetails + "! (Or this account could be deactivated!)");
                                }
                            }
                        } else {
                            System.out.println("You don't have enough money for that.");
                        }
                    }
                    break;
                case 'D':
                    account.remove();

                    System.out.println("Your account successfully deactivated!");

                    loginAccount(sc, true);

                    return;
                case 'L':
                    System.out.println("You successfully logged out!");

                    loginAccount(sc, true);
                    return;
            }

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    requestFunctions(account, sc, true);
                }
            }, 2500L);
        } else {
            System.out.println("Sorry but this account is deactivated!");

            loginAccount(sc, true);
        }
    }

    public static void loginAccount(Scanner sc, Boolean refreshNextLine) {
        System.out.println("=======================================");
        System.out.println("Please enter your bank username:");

        if (refreshNextLine) {
            sc.nextLine(); //Hotfix cuz sc.nextInt (https://www.educative.io/answers/what-is-scannernextline-in-java)
        }

        if (sc.hasNextLine()) {
            String username = sc.nextLine();
            Account account = Account.getAccountByName(username);
            if (account != null) {
                System.out.println("Please enter your bank password:");
                if (sc.hasNextLine()) {
                    String password = sc.nextLine();

                    if (account.getPassword().equals(password)) {
                        requestFunctions(account, sc, false);
                    } else {
                        System.out.println("Sorry but you entered wrong password!");

                        loginAccount(sc, false);
                    }
                }
            } else {
                System.out.println("Account " + username + " doesn't exist in our database!");

                loginAccount(sc, false);
            }
        }
    }

    public static void main(String[] args) {
        Account newAccount = new Account("Teszt", "Teszt123", 5);
        Account newAccount2 = new Account("Teszt2", "Teszt123", 500);
        Account newAccount3 = new Account("Teszt3", "Teszt123", 0);

        ArrayList<Account> accounts = Account.getAccounts();

        System.out.println(accounts.get(2).isValid());
        System.out.println(accounts.get(2).getBalance());
        System.out.println(Account.getAccountByName("Teszt2").getBalance());
        System.out.println(Account.getAccountByID(2).getBalance());
        System.out.println(Account.getBiggestAccountID());

        loginAccount(new Scanner(System.in), false);
    }
}