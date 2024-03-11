package day01.ex05;


import java.util.LinkedList;
import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private final LinkedList<Transaction> transactionsList;

    TransactionsLinkedList(){ transactionsList = new LinkedList<>(); }

    @Override
    public void addTransaction(Transaction transaction) {
        this.transactionsList.addLast(transaction);
    }

    @Override
    public void removeTransactionById(UUID id) {
        this.transactionsList.removeIf(transaction -> transaction.getIdentifier() == id);
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[this.transactionsList.size()];
        int index = 0;
        for (Transaction entry: this.transactionsList) { transactions[index++] = entry; }
        return transactions;
    }
}
