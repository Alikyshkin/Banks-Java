package account;

import exception.*;
import model.*;

public class CreditAccount implements Account {

    @Override
    public void addTime(int days, BankAccount account) {
        final int daysInYear = 365;
        final int percentCount = 100;
        final int minValue = 0;
        final int daysInMonth = 30;

        double creditLimit = account.getBank().getCreditLimit();
        double creditComission = account.getBank().getCreditCommission() / percentCount / daysInYear;

        if (!(account.getMoney() >= creditLimit)) {
            for (int i = days; i > 0; i--) {
                double toPay = (creditLimit - account.getMoney()) * creditComission;

                if (account.getCollectedTime() == daysInMonth) {
                    if (account.getClient().getMoney() - account.getCollected() < minValue) {
                        throw new NotEnoughMoneyException("bankrupt client");
                    }

                    account.getClient().decreaseMoney(account.getCollected());
                    account.setCollectedTime(0);
                    account.resetCollected();

                }
                account.increaseCollected(toPay);
                account.setCollectedTime(account.getCollectedTime() + 1);
            }
        }
    }

    @Override
    public void transferMoney(BankAccount from, BankAccount to, double money) {
        if (money > from.getBank().getMaxLimitForDoubtfulClients() && from.getClient().getAddress() != null && from.getClient().getPassport() != 0) {
            throw new NotReliablePersonException("Client is not reliable for this transaction");
        }

        if (from.getMoney() - money < 0) {
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
        if (account.getMoney() - money < 0) {
            throw new NotEnoughMoneyException("Client doesn't have enough money");
        }

        if (money > account.getBank().getMaxLimitForDoubtfulClients() && account.getClient().getAddress() != null && account.getClient().getPassport() != 0) {
            throw new NotReliablePersonException("Client is not reliable for this transaction");
        }

        account.getClient().increaseMoney(money);
        account.decreaseMoney(money);

        Transaction transaction = new Transaction(money, account.getAccountNumber(), TransactionType.WITHDRAWAL);
        account.getTransactions().add(transaction);
    }
}
