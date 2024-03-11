package day01.ex04;

public class Program {
    public static void main(String[] args) {
        User user1 = new User("Ivan", 15000);
        User user2 = new User("Pavel", 24000);
        TransactionsService service = new TransactionsService();
        service.addUser(user1);
        service.addUser(user2);

        service.executeTransaction(user1.getIdentifier(), user2.getIdentifier(), 5000);
        service.executeTransaction(user2.getIdentifier(), user1.getIdentifier(), 2000);
        service.executeTransaction(user1.getIdentifier(), user2.getIdentifier(), 2000000);
        System.out.println(service.getUserBalance(0));
        System.out.println(service.getUserBalance(1));

        for (Transaction entry: service.getTransactionsList(0)) {
            System.out.println(entry);
        }
        System.out.println("-------------------------------------");
        for (Transaction entry: service.getTransactionsList(1)) {
            System.out.println(entry);
        }

        System.out.println("-------------------------------------");
        Transaction[] trn = service.checkValidityOfTransactions();
        for (Transaction entry: trn) {
            System.out.println(entry);
            service.removeTransaction(entry.getIdentifier(), entry.getSender().getIdentifier());
        }
        System.out.println("-------------------------------------");
        trn = service.checkValidityOfTransactions();
        for (Transaction entry: trn) {
            System.out.println(entry);
        }
    }
}
