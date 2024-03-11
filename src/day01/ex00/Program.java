package day01.ex00;


public class Program {
    public static void main(String[] args) {
        User user1 = new User(1000, "Ivan 1", 50000);
        User user2 = new User(3000, "Ivan 2", 15000);

        System.out.println(user1);
        System.out.println(user2);

        Transaction transaction1 = new Transaction(user1, user2, Transaction.transferType.DEBIT, 5000);
        System.out.println(transaction1);
        Transaction transaction2 = new Transaction(user1, user2, Transaction.transferType.CREDIT, 1500);
        System.out.println(transaction2);
        Transaction transaction3 = new Transaction(user2, user1, Transaction.transferType.DEBIT, 1000);
        System.out.println(transaction3);
        Transaction transaction4 = new Transaction(user2, user1, Transaction.transferType.CREDIT, 1500);
        System.out.println(transaction4);

        System.out.println(user1);
        System.out.println(user2);

    }
}
