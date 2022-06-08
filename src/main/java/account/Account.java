package account;

import model.BankAccount;

public interface Account {
    void addTime(int days, BankAccount account);

    void transferMoney(BankAccount from, BankAccount to, double money);

    void takeMoneyFromAccount(BankAccount account, double money);
}
