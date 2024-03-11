package day01.ex04;


import java.util.UUID;
public class Transaction {
    enum transferType {
        DEBIT,
        CREDIT,
    }

    private final UUID identifier;
    private final User sender;
    private final User recipient;
    private final transferType transferCategory;
    private int transferAmount;

    public Transaction(User sender, User recipient, transferType type, int amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.transferCategory = type;
        this.identifier = UUID.randomUUID();
        checkBalance(amount);
    }

    private Transaction(Transaction originalTransaction) {
        this.identifier = originalTransaction.getIdentifier();
        this.sender = originalTransaction.sender;
        this.recipient = originalTransaction.recipient;
        this.transferCategory = originalTransaction.transferCategory == transferType.DEBIT
                ? transferType.CREDIT: transferType.DEBIT;
        this.transferAmount = originalTransaction.transferAmount;
    }

    public void checkBalance(int transferAmount) {
        if (transferCategory == transferType.CREDIT &&
                (transferAmount < 0 || sender.getBalance() < transferAmount)) {
            this.transferAmount = 0;
            sender.getTransactionsList().addTransaction(this);
        } else if (transferCategory == transferType.DEBIT &&
                (transferAmount < 0 || recipient.getBalance() < transferAmount)) {
            this.transferAmount = 0;
            recipient.getTransactionsList().addTransaction(this);
        } else {
            this.transferAmount = transferAmount;
            changeUsersBalance();
        }
    }

    public void changeUsersBalance() {
        if (transferCategory == transferType.CREDIT) {
            sender.setBalance(sender.getBalance() - transferAmount);
            sender.getTransactionsList().addTransaction(this);
            recipient.setBalance(recipient.getBalance() + transferAmount);
            recipient.getTransactionsList().addTransaction(new Transaction(this));
        } else if (transferCategory == transferType.DEBIT) {
            recipient.setBalance(recipient.getBalance() - transferAmount);
            recipient.getTransactionsList().addTransaction(this);
            sender.setBalance(sender.getBalance() + transferAmount);
            sender.getTransactionsList().addTransaction(new Transaction(this));
        }
    }

    public UUID getIdentifier() { return identifier; }
    public User getRecipient() { return recipient; }
    public User getSender() { return sender; }
    public transferType getTransferCategory() { return transferCategory; }
    public int getTransferAmount() { return transferAmount; }

    public String toString() {
        return "Transaction:" +
                "\nidentifier = " + identifier +
                "\nrecipient = " + recipient.getName() +
                "\nsender = " + sender.getName() +
                "\ntransferCategory = " + transferCategory +
                "\ntransferAmount = " + transferAmount + "\n";
    }
}
