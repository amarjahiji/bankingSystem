package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private int transactionId;
    private String transactionType;
    private float transactionAmount;
    private String transactionDate;
    private int accountId;
    private List<CardType> cardType = new ArrayList<>();

    public Transaction() {
    }

    public Transaction(ResultSet resultSet) throws SQLException {
        this.transactionId = resultSet.getInt("TRANSACTION_ID");
        this.transactionType = resultSet.getString("TRANSACTION_TYPE");
        this.transactionAmount = resultSet.getFloat("TRANSACTION_AMOUNT");
        this.transactionDate = resultSet.getString("TRANSACTION_DATE");
        this.accountId = resultSet.getInt("ACCOUNT_ID");
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
