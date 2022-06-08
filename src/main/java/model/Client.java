package model;

import exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client {

    private String name;
    private String surname;
    private String address;
    private int passport;
    private double money;
    private boolean agreeForNotifications;
    private List<BankAccount> accounts = new ArrayList<>();
    private List<String> notifications = new ArrayList<>();
    private UUID id;

    public Client(String name, String surname, double money) {
        if (!isValid(name, money)) {
            throw new NotCorrectInputException("Not Correct Input");
        }

        this.name = name;
        this.surname = surname;
        this.money = money;
        this.id = UUID.randomUUID();
        this.agreeForNotifications = false;
    }

    public void addNotifications(String notification) {
        notifications.add(notification);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPassport() {
        return passport;
    }

    public void setPassport(int passport) {
        this.passport = passport;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isAgreeForNotifications() {
        return agreeForNotifications;
    }

    public void setAgreeForNotifications(boolean agreeForNotifications) {
        this.agreeForNotifications = agreeForNotifications;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
