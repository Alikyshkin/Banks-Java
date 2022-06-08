package model;

import exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {

    private double money;
    private String name;
    private double debitInterest;
    private double creditLimit;
    private double creditCommission;
    private List<InterestPair> depositInterest = new ArrayList<>();
    private double maxLimitForDoubtfulClients;
    private List<BankAccount> accounts = new ArrayList<>();
    private UUID id;

    public Bank(String name, double money, double maxLimitForDoubtfulClients) {
        if (!isValid(name, money)) {
            throw new NotCorrectInputException("Not Correct Input");
        }

        this.money = money;
        this.name = name;
        this.maxLimitForDoubtfulClients = maxLimitForDoubtfulClients;
        this.id = UUID.randomUUID();
    }

    public void decreaseMoney(double summ) {
        money -= summ;
    }

    public void increaseMoney(double summ) {
        money += summ;
    }

    private boolean isValid(String name, double money) {
        if (name == null) return false;
        return money >= 0;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDebitInterest() {
        return debitInterest;
    }

    public void setDebitInterest(double debitInterest) {
        this.debitInterest = debitInterest;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getCreditCommission() {
        return creditCommission;
    }

    public void setCreditCommission(double creditCommission) {
        this.creditCommission = creditCommission;
    }

    public List<InterestPair> getDepositInterest() {
        return depositInterest;
    }

    public void setDepositInterest(List<InterestPair> depositInterest) {
        this.depositInterest = depositInterest;
    }

    public double getMaxLimitForDoubtfulClients() {
        return maxLimitForDoubtfulClients;
    }

    public void setMaxLimitForDoubtfulClients(double maxLimitForDoubtfulClients) {
        this.maxLimitForDoubtfulClients = maxLimitForDoubtfulClients;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
