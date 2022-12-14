import java.util.ArrayList;
import java.util.HashMap;

public class Account {
    static Integer registeredAccounts = -1; //I'm using this instead of accounts.size(); cuz the garbagecollector will delete the null value from the list and from that point the list length will say 2 while our highest index is 3; (Example)
    static ArrayList<Account> accounts = new ArrayList<Account>();
    static HashMap<Integer, String> accountNameFromID = new HashMap<Integer, String>();
    static HashMap<String, Integer> accountIDFromName = new HashMap<String, Integer>();

    private final Integer id;
    private String name;
    private String password;
    private boolean needToRemoved = false;
    private Double balance;
    private Double oldBalance = balance;

    private Bank bank;

    public Account(String name, String password, Double balance, Bank bank) {
        this.id = ++registeredAccounts;
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.bank = bank;

        accounts.add(this);
        accountNameFromID.put(this.id, name);
        accountIDFromName.put(name, this.id);
    }

    void remove() {
        accounts.set(this.id, null);

        this.needToRemoved = true;
    }

    static public ArrayList<Account> getAccounts() {
        return accounts;
    }

    static public Integer getBiggestAccountID() {
        return registeredAccounts;
    }

    static public String getAccountNameByID(Integer id) {
        return accountNameFromID.getOrDefault(id, "-1");
    }

    static public Integer getAccountIDByName(String name) {
        return accountIDFromName.getOrDefault(name, -1);
    }

    static public Account getAccountByID(Integer id) {
        if (id >= 0 && id <= accounts.size())
            return accounts.get(id);

        return null;
    }

    static public Account getAccountByName(String name) {
        Integer id = Account.getAccountIDByName(name);

        if (id >= 0 && id <= accounts.size())
            return accounts.get(id);

        return null;
    }

    void setPassword(String password) {
        this.password = password;
    }

    String getPassword() {
        return this.password;
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }

    Integer getID() {
        return this.id;
    }

    void setBalance(Double balance) {
        this.oldBalance = this.balance;

        this.balance = balance;
    }

    boolean takeBalance(Double amount) {
        double balance = getBalance();
        double newBalance = balance - amount;

        if (this.getAccountBank().hasGlobalMoney(amount)) {
            if (hasBalance(amount)) {
                this.getAccountBank().takeGlobalMoney(amount);
                setBalance(newBalance);

                return true;
            }
        }

        return false;
    }

    void giveBalance(Double amount) {
        double balance = getBalance();
        double newBalance = balance + amount;

        setBalance(newBalance);
    }

    Double getBalance() {
        return this.balance;
    }

    boolean hasBalance(Double amount) {
        return this.balance >= amount;
    }

    boolean transferMoney(String target, Double amount) {
        Account targetAccount = Account.getAccountByName(target);

        if (targetAccount == null) {
            try {
                int id = Integer.parseInt(target);

                targetAccount = Account.getAccountByID(id);
            } catch (NumberFormatException ignored) {

            }
        }

        if (targetAccount != null) {
            if (targetAccount.getAccountBank().equals(this.getAccountBank())) {
                if (hasBalance(amount)) {
                    if (this.getAccountBank().hasGlobalMoney(amount)) {
                        if (targetAccount.isValid()) {
                            this.getAccountBank().takeGlobalMoney(amount);
                            takeBalance(amount);
                            targetAccount.giveBalance(amount);

                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    Boolean isValid() {
        return !this.needToRemoved;
    }

    public Bank getAccountBank() {
        return this.bank;
    }

    public void setAccountBank(Bank bank) {
        this.bank = bank;
    }
}
