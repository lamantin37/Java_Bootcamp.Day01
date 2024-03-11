package day01.ex01;


public class User {
    private final int identifier;
    private String name;
    private int balance;

    public User(String name, int balance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = Math.max(balance, 0);
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = Math.max(balance, 0);
    }

    @Override
    public String toString() {
        return "User:" +
                "\nidentifier = " + identifier +
                "\nname = " + name +
                "\nbalance = " + balance + "\n";
    }
}
