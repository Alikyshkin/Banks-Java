package model;

import exception.*;
import account.*;

import java.util.ArrayList;
import java.util.List;

public class BanksService {
    private final List<Client> clients = new ArrayList<>();
    private final List<BankAccount> accounts = new ArrayList<>();

    public List<Client> getClientsList() {
        return clients;
    }

    public List<BankAccount> getBankAccounts() {
        return accounts;
    }

    public List<BankAccount> getClientsAccounts(Client client) {
        return client.getAccounts();
    }

    public Client addClient(String name, String surname, double money) {
        Client client = new Client(name, surname, money);
        clients.add(client);
        return client;
    }

    public boolean checkStatus(Client client) {
        return client.getAddress() != null && client.getPassport() != 0;
    }

    public void setClientAddress(Client client, String address) {
        if (address == null) {
            System.out.println("Address is null");
        } else {
            client.setAddress(address);
        }
    }

    public void consentToNotifications(Client client) {
        client.setAgreeForNotifications(true);
    }

    public void setClientPassport(Client client, int passport) {
        if (Integer.toString(passport).length() != 10) {
            System.out.println("Passport is invalid");
        } else {
            client.setPassport(passport);
        }
    }

    public BankAccount createDebitAccount(Client client, Bank bank, double money) {
        if (client.getMoney() < money) {
            throw new NotEnoughMoneyException("Client doesn't have enough money");
        }

        client.decreaseMoney(money);

        DebitAccount accountType = new DebitAccount();
        BankAccount account = new BankAccount(client, bank, accountType, AccountType.DEBIT);

        account.increaseMoney(money);
        accounts.add(account);
        bank.getAccounts().add(account);

        return account;
    }

    public BankAccount createCreditAccount(Client client, Bank bank) {
        if (bank.getMoney() < bank.getCreditLimit()) {
            throw new NotEnoughMoneyException("Bank doesn't have enough money");
        }

        bank.decreaseMoney(bank.getCreditLimit());

        CreditAccount accountType = new CreditAccount();
        BankAccount account = new BankAccount(client, bank, accountType, AccountType.CREDIT);

        accounts.add(account);
        bank.getAccounts().add(account);

        return account;
    }

    public BankAccount createDepositAccount(Client client, Bank bank, double money, int term) {
        if (client.getMoney() < money) {
            throw new NotEnoughMoneyException("Client doesn't have enough money");
        }

        client.decreaseMoney(money);

        DepositAccount accountType = new DepositAccount();
        BankAccount account = new BankAccount(client, bank, accountType, AccountType.DEPOSIT);

        account.increaseMoney(money);
        account.setTerm(term);
        accounts.add(account);
        bank.getAccounts().add(account);

        return account;
    }

    public void putMoneyIntoAccount(BankAccount account, double money) {
        if (account.getClient().getMoney() < money) {
            throw new NotEnoughMoneyException("Client doesn't have enough money");
        }

        account.getClient().decreaseMoney(money);
        account.increaseMoney(money);

        Transaction transaction = new Transaction(money, account.getAccountNumber(), TransactionType.REPLENISHMENT);
        account.getTransactions().add(transaction);
    }

    public void takeMoneyFromAccount(BankAccount account, double money) {
        account.getAccountType().takeMoneyFromAccount(account, money);
    }

    public void cancelLastTransaction(BankAccount account) {
        if (!account.getTransactions().isEmpty()) {
            Transaction lastTransaction = account.getTransactions().get(account.getTransactions().size() - 1);
            double summ = lastTransaction.getSumm();

            if (lastTransaction.getType() == TransactionType.WITHDRAWAL) {
                if (account.getClient().getMoney() < summ) {
                    throw new NotEnoughMoneyException("Client doesn't have enough money");
                }

                account.getClient().decreaseMoney(summ);
                account.increaseMoney(summ);
                account.getTransactions().remove(lastTransaction);
            } else if (lastTransaction.getType() == TransactionType.REPLENISHMENT) {
                if ((account.getMoney() < summ && account.getType() == AccountType.DEBIT)
                        || (account.getMoney() - summ < 0 && account.getType() == AccountType.CREDIT)) {
                    throw new NotEnoughMoneyException("Account doesn't have enough money");
                }

                account.getClient().increaseMoney(summ);
                account.decreaseMoney(summ);
                account.getTransactions().remove(lastTransaction);
            }
        } else {
            throw new NoTransactionsException("There is no transactions with current account");
        }
    }

    public void addTime(int days) {
        for (BankAccount account : accounts) {
            account.getAccountType().addTime(days, account);
        }
    }
}
