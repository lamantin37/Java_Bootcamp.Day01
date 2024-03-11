package day01.ex03;


import java.util.LinkedList;
import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private LinkedList<Transaction> transactionsList;

    boolean checkListExist() {
        return this.transactionsList == null;
    }
    @Override
    public void addTransaction(Transaction transaction) {
        if (checkListExist()) { this.transactionsList = new LinkedList<>(); }
        this.transactionsList.addLast(transaction);
    }

    @Override
    public void removeTransactionById(UUID id) {
        if (!this.transactionsList.isEmpty()) {
            this.transactionsList.removeIf(transaction -> transaction.getIdentifier() == id);
        }
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[this.transactionsList.size()];
        int index = 0;
        for (Transaction entry: this.transactionsList) { transactions[index++] = entry; }
        return transactions;
    }

}
