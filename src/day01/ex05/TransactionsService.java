package day01.ex05;

import java.util.UUID;

public class TransactionsService {
    UsersArrayList usersList = new UsersArrayList();

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public int getUserBalance(int id) {
        return usersList.getUserById(id).getBalance();
    }

    public int getUserBalance(User user) {
        return getUserBalance(user.getIdentifier());
    }

    public void executeTransaction(int senderId, int recipientId, int amount) {
        new Transaction(usersList.getUserById(senderId), usersList.getUserById(recipientId),
                Transaction.transferType.CREDIT, amount);
    }

    public Transaction[] getTransactionsList(int userId) {
        return usersList.getUserById(userId).getTransactionsList().toArray();
    }

    public void removeTransaction(UUID transactionId, int userId) {
        usersList.getUserById(userId).getTransactionsList().removeTransactionById(transactionId);
    }

    public Transaction[] checkValidityOfTransactions() {
        TransactionsList transactionsList = getAllTransactions();
        TransactionsLinkedList result = new TransactionsLinkedList();

        for (Transaction transaction : transactionsList.toArray()) {
            if (countOccurrences(transaction.getIdentifier(), transactionsList.toArray()) != 2) {
                result.addTransaction(transaction);
            }
        }

        return result.toArray();
    }

    private int countOccurrences(UUID identifier, Transaction[] transactions) {
        int count = 0;
        for (Transaction transaction : transactions) {
            if (identifier.equals(transaction.getIdentifier())) {
                count++;
            }
        }
        return count;
    }

    private TransactionsList getAllTransactions() {
        TransactionsList transactions = new TransactionsLinkedList();

        for (int i = 0; i != usersList.getNumberOfUsers(); ++i) {
            for (Transaction entry : usersList.getUserById(i).getTransactionsList().toArray()) {
                transactions.addTransaction(entry);
            }
        }

        return transactions;
    }

}
