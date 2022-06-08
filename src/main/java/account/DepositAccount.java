package account;

import exception.*;
import model.*;

public class DepositAccount implements Account {

    @Override
    public void addTime(int days, BankAccount account) {
        final int daysInMonth = 30;
        final int daysInYear = 365;
        final int percentCount = 100;

        for (int i = days; i > 0; i--) {
            if (account.getTerm() != 0) {
                double currentInterest = 0;

                for (InterestPair interest : account.getBank().getDepositInterest()) {
                    if (account.getMoney() >= interest.getSum()) {
                        currentInterest = interest.getInterest();
                    }
                }

                double interestPerDay = currentInterest / percentCount / daysInYear;

                if (account.getCollectedTime() == daysInMonth) {
                    account.increaseMoney(account.getCollected());
                    account.setCollectedTime(0);
                    account.resetCollected();

                }
                account.increaseCollected(account.getMoney() * interestPerDay);
                account.setCollectedTime(account.getCollectedTime() + 1);
                account.setTerm(account.getTerm() - 1);
            }
        }
    }

    @Override
    public void transferMoney(BankAccount from, BankAccount to, double money) {
        if (money > from.getBank().getMaxLimitForDoubtfulClients() && from.getClient().getAddress() != null && from.getClient().getPassport() != 0) {
            throw new NotReliablePersonException("Client is not reliable for this transaction");
        }

        if (from.getTerm() > 0) {
            throw new NotExpiredTermException("The deposit term is not expired");
        }

        from.decreaseMoney(money);
        to.increaseMoney(money);

        Transaction transaction = new Transaction(money, from.getAccountNumber(), to.getAccountNumber(), TransactionType.TRANSFER);
        from.getTransactions().add(transaction);
        to.getTransactions().add(transaction);
    }

    @Override
    public void takeMoneyFromAccount(BankAccount account, double money) {
        if (account.getTerm() > 0) {
            throw new NotExpiredTermException("The deposit term is not expired");
        }

        account.getClient().increaseMoney(money);
        account.decreaseMoney(money);

        Transaction transaction = new Transaction(money, account.getAccountNumber(), TransactionType.WITHDRAWAL);
        account.getTransactions().add(transaction);
    }
}
