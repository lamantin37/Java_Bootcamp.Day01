package day01.ex03;

public class Program {
    public static void main(String[] args) {
        User user1 = new User("Ivan", 50000);
        User user2 = new User("Pavel", 25000);
        TransactionsLinkedList TransactionsLinkedList = new TransactionsLinkedList();
        Transaction transaction1 = new Transaction(user1, user2, Transaction.transferType.DEBIT, 5000);
        TransactionsLinkedList.addTransaction(transaction1);
        Transaction transaction2 = new Transaction(user1, user2, Transaction.transferType.CREDIT, 5000);
        TransactionsLinkedList.addTransaction(transaction2);
        Transaction transaction3 = new Transaction(user1, user2, Transaction.transferType.DEBIT, 5000);
        TransactionsLinkedList.addTransaction(transaction3);
        Transaction transaction4 = new Transaction(user1, user2, Transaction.transferType.CREDIT, 5000);
        TransactionsLinkedList.addTransaction(transaction4);
        Transaction transaction5 = new Transaction(user1, user2, Transaction.transferType.DEBIT, 2500);
        TransactionsLinkedList.addTransaction(transaction5);
        Transaction transaction6 = new Transaction(user1, user2, Transaction.transferType.CREDIT, 2500);
        TransactionsLinkedList.addTransaction(transaction6);

        TransactionsLinkedList.removeTransactionById(transaction1.getIdentifier());
        TransactionsLinkedList.removeTransactionById(transaction2.getIdentifier());
        TransactionsLinkedList.removeTransactionById(transaction3.getIdentifier());
        TransactionsLinkedList.removeTransactionById(transaction4.getIdentifier());

        Transaction[] transactionArray = TransactionsLinkedList.toArray();
        for (Transaction entry: transactionArray) {
            System.out.println(entry);
        }
    }
}
