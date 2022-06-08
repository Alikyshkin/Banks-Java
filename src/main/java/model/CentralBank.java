package model;

import java.util.*;
import java.util.stream.Collectors;

public class CentralBank {

    private final List<Bank> banks = new ArrayList<>();

    public List<Bank> getBanks() {
        return banks;
    }

    public Bank addBank(String name, double money, double maxLimitForDoubtfulClients) {
        Bank bank = new Bank(name, money, maxLimitForDoubtfulClients);
        banks.add(bank);
        return bank;
    }

    public void addDebitToBank(Bank bank, double debitInterest) {
        bank.setDebitInterest(debitInterest);

        for (BankAccount account : bank.getAccounts()) {
            if (account.getClient().isAgreeForNotifications()) {
                account.getClient().getNotifications().add("Changed conditions in bank " + bank.getName() + " for Debit");
            }
        }
    }

    public void addCreditToBank(Bank bank, double creditLimit, double creditCommission) {
        bank.setCreditLimit(creditLimit);
        bank.setCreditCommission(creditCommission);

        for (BankAccount account : bank.getAccounts()) {
            if (account.getClient().isAgreeForNotifications()) {
                account.getClient().getNotifications().add("Changed conditions in bank " + bank.getName() + " for Credit");
            }
        }
    }

    public void addDepositToBank(Bank bank, List<InterestPair> depositInterest) {
        deleteDeposit(bank);
        bank.setDepositInterest(depositInterest);
        List<InterestPair> sortedInterest = bank.getDepositInterest()
                .stream()
                .sorted(Comparator.comparing(InterestPair::getSum))
                .collect(Collectors.toList());

        for (BankAccount account : bank.getAccounts()) {
            if (account.getClient().isAgreeForNotifications()) {
                account.getClient().getNotifications().add("Changed conditions in bank " + bank.getName() + " for Deposit");
            }
        }
    }

    public void transferMoney(BankAccount from, BankAccount to, double money) {
        from.getAccountType().transferMoney(from, to, money);
    }

    private void deleteDeposit(Bank bank) {
        bank.setDepositInterest(null);
    }
}
