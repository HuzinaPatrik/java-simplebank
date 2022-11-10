import java.util.ArrayList;

public class Bank {
    static Integer registeredBanks = -1; //I'm using this instead of accounts.size(); cuz the garbagecollector will delete the null value from the list and from that point the list length will say 2 while our highest index is 3; (Example)
    static ArrayList<Bank> banks = new ArrayList<Bank>();

    private final Integer id;
    private String name;
    private String owner;
    private Double money;
    private ArrayList<Account> accounts;
    private Double chargeRate;
    private Double interestRate;

    private Boolean needToRemoved;

    public Bank(String name, String owner, Double money, ArrayList<Account> accounts, Double chargeRate, Double interestRate) {
        this.id = ++registeredBanks;
        this.name = name;
        this.owner = owner;
        this.money = money;
        this.accounts = accounts;
        this.needToRemoved = false;
        this.chargeRate = chargeRate;
        this.interestRate = interestRate;

        banks.add(this);
    }

    public void remove() {
        banks.set(this.id, null);

        this.needToRemoved = true;
    }

    public Boolean isValid() {
        return !this.needToRemoved;
    }

    public boolean AccountMemberOfBank(Account account) {
        return this.accounts.contains(account);
    }

    public Double getGlobalMoney() {
        return this.money;
    }

    public boolean hasGlobalMoney(Double amount) {
        return this.money >= amount;
    }

    public void setGlobalMoney(Double money) {
        this.money = money;
    }

    public void takeGlobalMoney(Double amount) {
        if (hasGlobalMoney(amount)) {
            this.money = this.money - amount;
        }
    }

    public Double getInterestRate() {
        return this.interestRate;
    }

    public Double getChargeRate() {
        return this.chargeRate;
    }

    public String getBankOwner() {
        return this.owner;
    }

    public String getBankName() {
        return this.name;
    }

    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public void createNewAccount(String name, String password, Double balance) {
        Account newAcc = new Account(name, password, balance, this);
        addAccount(newAcc);
    }

    public boolean removeAccount(Account account) {
        if (this.accounts.contains(account)) {
            this.accounts.remove(account);
            return true;
        }

        return false;
    }

    public boolean equals(Bank e) {
        return this == e;
    }
}
