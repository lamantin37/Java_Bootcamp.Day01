package day01.ex05;


public class User {
    private final int identifier;
    private String name;
    private int balance;
    private final TransactionsLinkedList transactionsList;

    public User(String name, int balance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = Math.max(balance, 0);
        transactionsList = new TransactionsLinkedList();
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

    public TransactionsLinkedList getTransactionsList() {
        return transactionsList;
    }

}
