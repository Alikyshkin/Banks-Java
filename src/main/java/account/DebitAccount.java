package account;

import exception.*;
import model.*;

public class DebitAccount implements Account {

    @Override
    public void addTime(int days, BankAccount account) {
        final int daysInYear = 365;
        final int percentCount = 100;
        final int daysInMonth = 30;

        double interestPerDay = account.getBank().getDebitInterest() / percentCount / daysInYear;

        for (int i = days; i > 0; i--) {
            if (account.getCollectedTime() == daysInMonth) {
                account.increaseMoney(account.getCollected());
                account.setCollectedTime(0);
                account.resetCollected();
            }
            account.increaseCollected(account.getMoney() * interestPerDay);
            account.setCollectedTime(account.getCollectedTime() + 1);
        }
    }

    @Override
    public void transferMoney(BankAccount from, BankAccount to, double money) {
        if (money > from.getBank().getMaxLimitForDoubtfulClients() && from.getClient().getAddress() != null && from.getClient().getPassport() != 0) {
            throw new NotReliablePersonException("Client is not reliable for this transaction");
        }

        if (from.getMoney() < money) {
            throw new NotEnoughMoneyException("Client doesn't have enough money");
        }

        from.decreaseMoney(money);
        to.increaseMoney(money);

        Transaction transaction = new Transaction(money, from.getAccountNumber(), to.getAccountNumber(), TransactionType.TRANSFER);
        from.getTransactions().add(transaction);
        to.getTransactions().add(transaction);
    }

    @Override
    public void takeMoneyFromAccount(BankAccount account, double money) {
        if (account.getMoney() < money) {
            throw new NotEnoughMoneyException("Client doesn't have enough money");
        }

        account.getClient().increaseMoney(money);
        account.decreaseMoney(money);

        Transaction transaction = new Transaction(money, account.getAccountNumber(), TransactionType.WITHDRAWAL);
        account.getTransactions().add(transaction);
    }
}
