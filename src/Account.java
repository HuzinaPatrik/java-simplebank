import java.util.ArrayList;
import java.util.HashMap;

public class Account {
    static ArrayList<Account> accounts = new ArrayList<Account>();
    static HashMap<Integer, String> accountNameFromID = new HashMap<Integer, String>();
    static HashMap<String, Integer> accountIDFromName = new HashMap<String, Integer>();

    Integer id;
    String name;
    String password;
    boolean needToRemoved = false;
    Integer balance;
    Integer oldBalance = balance;

    public Account(String name, String password, Integer balance) {
        this.id = accounts.size();
        this.name = name;
        this.password = password;
        this.balance = balance;

        accounts.add(this);
        accountNameFromID.put(this.id, name);
        accountIDFromName.put(name, this.id);
    }

    void remove() {
        this.needToRemoved = true;
    }

    static public ArrayList<Account> getAccounts() {
        return accounts;
    }

    static public String getAccountNameByID(Integer id) {
        return accountNameFromID.getOrDefault(id, "-1");
    }

    static public Integer getAccountIDByName(String name) {
        return accountIDFromName.getOrDefault(name, -1);
    }

    static public Account getAccountByID(Integer id) {
        if (id >= 0)
            return accounts.get(id);

        return null;
    }

    static public Account getAccountByName(String name) {
        Integer id = Account.getAccountIDByName(name);

        if (id >= 0)
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

    void setBalance(Integer balance) {
        this.oldBalance = this.balance;
        this.balance = balance;
    }

    Integer getBalance() {
        return this.balance;
    }

    Boolean isValid() {
        return !this.needToRemoved;
    }
}
