import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void loginAccount() {
        System.out.println("=======================================");
        System.out.println("Please enter your bank username:");
        Scanner sc = new Scanner(System.in);

        String username = sc.nextLine();
        Account account = Account.getAccountByName(username);
        if (account != null) {
            System.out.println("Please enter your bank password:");
            String password = sc.nextLine();

            if (account.getPassword().equals(password)) {
                if (account.isValid()) {
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

                                loginAccount();
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
                                            System.out.println("Sorry but we couldn't find: " + targetDetails + "!");
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

                            loginAccount();

                            break;
                        case 'L':
                            System.out.println("You successfully logged out!");

                            loginAccount();
                            break;
                    }

                    sc.close();
                } else {
                    System.out.println("Sorry but this account is deactivated!");

                    loginAccount();
                }
            } else {
                System.out.println("Sorry but you entered wrong password!");

                loginAccount();
            }
        } else {
            System.out.println("Account " + username + " doesn't exist in our database!");

            loginAccount();
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
        System.out.println(Account.getAccountByID(0).getBalance());

        loginAccount();
    }
}