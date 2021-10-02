package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private BigDecimal balance;
    private int accountId;
    private int userId;

    public BigDecimal getBalance() {return balance;}

    public void setBalance(BigDecimal balance) {this.balance = balance;}

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
