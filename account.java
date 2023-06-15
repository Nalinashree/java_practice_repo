import java.util.ArrayList;
import java.util.List;

public class Account {
    private int id;
    private List<Transaction> transactions;

    public Account(int id) {
        this.id = id;
        this.transactions = new ArrayList<>();
    }

    public void sendMoneyToAccount(Account accountTo, double moneyAmount) {
        if (moneyAmount <= 0) {
            System.out.println("Invalid money amount. Please provide a positive value.");
            return;
        }

        if (this.id == accountTo.getId()) {
            System.out.println("Invalid transaction. Cannot send money to the same account.");
            return;
        }

        Transaction sendTransaction = new Transaction(this, accountTo, moneyAmount, StandardAccountOperations.MONEY_TRANSFER_SEND);
        Transaction receiveTransaction = new Transaction(this, accountTo, moneyAmount, StandardAccountOperations.MONEY_TRANSFER_RECEIVE);

        this.transactions.add(sendTransaction);
        accountTo.getTransactionsList().add(receiveTransaction);

        System.out.println("Money sent successfully.");
    }

    public void withdrawMoney(double moneyAmount) {
        if (moneyAmount <= 0) {
            System.out.println("Invalid money amount. Please provide a positive value.");
            return;
        }

        if (moneyAmount > getBalance()) {
            System.out.println("Insufficient funds. Cannot withdraw more than the available balance.");
            return;
        }

        Transaction withdrawTransaction = new Transaction(this, null, moneyAmount, StandardAccountOperations.WITHDRAW);
        this.transactions.add(withdrawTransaction);

        System.out.println("Money withdrawn successfully.");
    }

    public Transaction[] getTransactions() {
        return transactions.toArray(new Transaction[0]);
    }

    public int getId() {
        return id;
    }

    private List<Transaction> getTransactionsList() {
        return transactions;
    }

    public double getBalance() {
        double balance = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getOperation() == StandardAccountOperations.MONEY_TRANSFER_RECEIVE ||
                    transaction.getOperation() == StandardAccountOperations.WITHDRAW) {
                balance += transaction.getMoneyAmount();
            } else if (transaction.getOperation() == StandardAccountOperations.MONEY_TRANSFER_SEND) {
                balance -= transaction.getMoneyAmount();
            }
        }

        return balance;
    }

    public static class Transaction {
        private Account accountFrom;
        private Account accountTo;
        private double moneyAmount;
        private StandardAccountOperations operation;

        public Transaction(Account accountFrom, Account accountTo, double moneyAmount, StandardAccountOperations operation) {
            this.accountFrom = accountFrom;
            this.accountTo = accountTo;
            this.moneyAmount = moneyAmount;
            this.operation = operation;
        }

        public Account getAccountFrom() {
            return accountFrom;
        }

        public Account getAccountTo() {
            return accountTo;
        }

        public double getMoneyAmount() {
            return moneyAmount;
        }

        public StandardAccountOperations getOperation() {
            return operation;
        }
    }
}
