package day01.ex05;

import java.util.Scanner;
import java.util.UUID;

public class Menu {

    private final TransactionsService service;
    private final Scanner scanner;

    public Menu() {
        service = new TransactionsService();
        scanner = new Scanner(System.in);
    }

    public void run(boolean devMode) {
        System.out.println();
        while (true) {
            selectionMenu(devMode);
            choiceMenu(getAnswer(devMode), devMode);
        }
    }

    public int getAnswer(boolean devMode) {
        try {
            int answer = Integer.parseInt(scanner.nextLine().trim());
            if (answer <= 0 || (devMode && answer > 7 || !devMode && answer > 5)) {
                throw new RuntimeException("Invalid action. Enter a valid number: ");
            }
            return answer;
        } catch (RuntimeException e) {
            System.out.println(e);
            return getAnswer(devMode);
        }
    }

    public void selectionMenu(boolean devMode) {
        System.out.println("1. Add a user\n2. View user balances\n3. Perform a transfer\n" +
                "4. View all transactions for a specific user");
        if (devMode) {
            System.out.println("5. DEV – remove a transfer by ID\n6. DEV – check transfer validity");
        } else {
            System.out.println("5. Finish execution");
        }
    }

    public void choiceMenu(int answer, boolean devMode) {
        switch (answer) {
            case 1:
                addUser();
                break;
            case 2:
                viewUserBalance();
                break;
            case 3:
                performTransfer();
                break;
            case 4:
                viewAllTransactionsOfUser();
                break;
            case 5:
            {
                if (!devMode) {
                    scanner.close();
                    System.exit(0);
                } else if (devMode) {
                    removeTransferById();
                }
                break;
            }
            case 6:
                checkTransferValidity();
                break;
            case 7:
            {
                scanner.close();
                System.exit(0);
            }
        }
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");
        try {
            String[] inputArr = scanner.nextLine().trim().split("\\s+");
            if (inputArr.length != 2) {
                throw new RuntimeException("Invalid data. Try again");
            }
            User user = new User(inputArr[0], Integer.parseInt(inputArr[1]));
            service.addUser(user);
            System.out.println("User with id = " + user.getIdentifier() + " is added");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private void viewUserBalance() {
        System.out.println("Enter a user ID");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            int balance = service.getUserBalance(id);
            System.out.println(service.usersList.getUserById(id).getName() + " - " + balance);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        try {
            String[] inputArr = scanner.nextLine().trim().split("\\s+");
            if (inputArr.length != 3) {
                throw new RuntimeException("Invalid data. Try again");
            }
            int senderId = Integer.parseInt(inputArr[0]);
            int recipientId = Integer.parseInt(inputArr[1]);
            int amount = Integer.parseInt(inputArr[2]);
            service.executeTransaction(senderId, recipientId, amount);
            System.out.println("The transfer is completed");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private void viewAllTransactionsOfUser() {
        System.out.println("Enter a user ID");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Transaction[] transactions = service.getTransactionsList(id);
            if (transactions == null) {
                throw new RuntimeException("User with ID = " + id + " hasn't any transactions");
            }
            for (Transaction item : transactions) {
                String category = (item.getTransferCategory() == Transaction.transferType.DEBIT) ? "From " : "To ";
                User user = (item.getTransferCategory() == Transaction.transferType.DEBIT) ? item.getSender() : item.getRecipient();
                System.out.println(category + user.getName() + "(id = " + user.getIdentifier() + ") " +
                        item.getTransferAmount() + " with id = " + item.getIdentifier());
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private Transaction hgetTransaction(Transaction[] transactions, UUID transactionId) {
        if (transactions == null) {
            throw new RuntimeException("Transaction with id = " + transactionId + " not found");
        }
        for (Transaction item : transactions) {
            if (item.getIdentifier().equals(transactionId)) {
                return item;
            }
        }
        return null;
    }

    private void removeTransferById() {
        System.out.println("Enter a user ID and a transfer ID");
        try {
            String[] inputArr = scanner.nextLine().trim().split("\\s+");
            if (inputArr.length != 2) {
                throw new RuntimeException("Invalid data. Try again");
            }
            int userId = Integer.parseInt(inputArr[0]);
            UUID transactionId = UUID.fromString(inputArr[1]);

            Transaction transaction = hgetTransaction(service.getTransactionsList(userId), transactionId);
            if (transaction == null) {
                throw new RuntimeException("Transaction with id = " + transactionId + " not found");
            }
            service.removeTransaction(transactionId, userId);
            User user = (transaction.getTransferCategory() == Transaction.transferType.DEBIT) ? transaction.getSender() : transaction.getRecipient();
            String category = (transaction.getTransferCategory() == Transaction.transferType.DEBIT) ? "From " : "To ";
            System.out.println("Transfer " + category + " " + user.getName() +
                    "(id = " + user.getIdentifier() + ") " + transaction.getTransferAmount() + " removed");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private User getUserHolder(Transaction transaction) {
        UsersArrayList listUsers = service.usersList;

        for (int i = 0; i < listUsers.getNumberOfUsers(); i++) {
            Transaction[] listTrans = listUsers.getUserByIndex(i).getTransactionsList().toArray();
            for (int j = 0; listTrans != null && j < listTrans.length; j++) {
                if (listTrans[j].getIdentifier().equals(transaction.getIdentifier())) {
                    return listUsers.getUserByIndex(i);
                }
            }
        }
        return null;
    }

    private void checkTransferValidity() {
        System.out.println("Check results:");
        Transaction[] transactions = service.checkValidityOfTransactions();
        if (transactions != null) {
            for (Transaction item : transactions) {
                User userHolder = getUserHolder(item);
                User userSender = (item.getTransferCategory() == Transaction.transferType.DEBIT) ? item.getSender() : item.getRecipient();
                UUID transactionId = item.getIdentifier();
                int amount = item.getTransferAmount();
                assert userHolder != null;
                System.out.println(userHolder.getName() + "(id = " + userHolder.getIdentifier() +
                        ") has an unacknowledged transfer id = " + transactionId + " from " +
                        userSender.getName() + "(id = " + userSender.getIdentifier() +
                        ") for " + amount);
            }
            System.out.println("---------------------------------------------------------");
            return;
        }
        System.out.println("There are no unpaired transactions");
        System.out.println("---------------------------------------------------------");
    }
}
