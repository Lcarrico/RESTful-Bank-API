package dev.carrico.entities;

import java.util.HashSet;
import java.util.Set;

public class Account {
    private int accountId;
    private int balance;
    private int clientId;

    public Account(int clientId, int balance) {
        this.accountId = 0;
        this.balance = balance;
        this.clientId = clientId;
    }

    public Account(int clientId, int accountId, int balance) {
        this.accountId = accountId;
        this.balance = balance;
        this.clientId = clientId;
    }

    public Account(int clientId) {
        this.accountId = 0;
        this.balance = 0;
        this.clientId = clientId;
    }

    public Account() {
        this.accountId = 0;
        this.balance = 0;
        this.clientId = 0;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", clientId=" + clientId +
                '}';
    }

}
