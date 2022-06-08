package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private double summ;
    private TransactionType type;
    private LocalDateTime date;
    private UUID from;
    private UUID to;
    private UUID account;

    public Transaction(double summ, UUID from, UUID to, TransactionType type) {
        this.summ = summ;
        this.type = type;
        this.from = from;
        this.to = to;
        this.date = LocalDateTime.now();
    }

    public Transaction(double summ, UUID account, TransactionType type) {
        this.summ = summ;
        this.type = type;
        this.account = account;
        this.date = LocalDateTime.now();
    }

    public double getSumm() {
        return summ;
    }

    public void setSumm(double summ) {
        this.summ = summ;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UUID getFrom() {
        return from;
    }

    public void setFrom(UUID from) {
        this.from = from;
    }

    public UUID getTo() {
        return to;
    }

    public void setTo(UUID to) {
        this.to = to;
    }

    public UUID getAccount() {
        return account;
    }

    public void setAccount(UUID account) {
        this.account = account;
    }
}
