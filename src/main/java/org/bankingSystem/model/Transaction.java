package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private Integer transactionId;
    private String transactionType;
    private Float transactionAmount;
    private String transactionDate;
    private Integer accountId;
    private List<CardType> cardType = new ArrayList<>();

    public Transaction(ResultSet resultSet) throws SQLException {
        this.transactionId = resultSet.getInt("transaction_id");
        this.transactionType = resultSet.getString("transaction_type");
        this.transactionAmount = resultSet.getFloat("transaction_amount");
        this.transactionDate = resultSet.getString("transaction_date");
        this.accountId = resultSet.getInt("account_id");
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
