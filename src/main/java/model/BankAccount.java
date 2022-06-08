package model;

import exception.*;
import account.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class BankAccount {

    private UUID accountNumber;
    private double money;
    private double collected;
    private int collectedTime;
    private Account accountType;
    private AccountType type;
    private int term;
    private Client client;
    private Bank bank;
    private List<Transaction> transactions = new ArrayList<>();
    private LocalDateTime creationDate;

    public BankAccount(Client client, Bank bank, Account account, AccountType type) {
        this.client = client;
        this.bank = bank;
        this.accountType = account;
        this.type = type;
        this.money = bank.getCreditLimit();
        this.creationDate = LocalDateTime.now();
        this.collectedTime = 0;
        this.accountNumber = UUID.randomUUID();
    }

    public void increaseCollected(double summ) {
        collected += summ;
    }

    public void setTerm(int currentTerm) {
        if (currentTerm <= 0) {
            throw new NotCorrectInputException("Not Correct Input");
        }

        term = currentTerm;
    }

    public void resetCollected() {
        collected = 0;
    }

    public void increaseMoney(double summ) {
        if (summ < 0) {
            throw new NotCorrectInputException("Not Correct Input");
        }

        money += summ;
    }

    public void decreaseMoney(double summ) {
        money -= summ;
    }

    public UUID getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(UUID accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getCollected() {
        return collected;
    }

    public void setCollected(double collected) {
        this.collected = collected;
    }

    public int getCollectedTime() {
        return collectedTime;
    }

    public void setCollectedTime(int collectedTime) {
        this.collectedTime = collectedTime;
    }

    public Account getAccountType() {
        return accountType;
    }

    public void setAccountType(Account accountType) {
        this.accountType = accountType;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public int getTerm() {
        return term;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
