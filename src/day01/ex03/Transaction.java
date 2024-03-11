package day01.ex03;


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

    private Transaction() {
        throw new UnsupportedOperationException("Cannot create Transaction without parameters.");
    }
    public Transaction(User sender, User recipient, transferType type, int amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.transferCategory = type;
        checkBalance(amount);
        this.identifier = UUID.randomUUID();
    }

    public void checkBalance(int transferAmount) {
        if (transferCategory == transferType.CREDIT && (transferAmount < 0 || sender.getBalance() < transferAmount)) {
            this.transferAmount = 0;
        } else if (transferCategory == transferType.DEBIT && (transferAmount < 0 || recipient.getBalance() < transferAmount)) {
            this.transferAmount = 0;
        } else {
            this.transferAmount = transferAmount;
            changeUsersBalance();
        }
    }

    public void changeUsersBalance() {
        if (transferCategory == transferType.CREDIT) {
            sender.setBalance(sender.getBalance() - transferAmount);
            recipient.setBalance(recipient.getBalance() + transferAmount);
        } else if (transferCategory == transferType.DEBIT) {
            sender.setBalance(sender.getBalance() + transferAmount);
            recipient.setBalance(recipient.getBalance() - transferAmount);
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
