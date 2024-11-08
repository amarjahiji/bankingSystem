package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Transaction {
    private UUID transactionId;
    private String transactionType;
    private Float transactionAmount;
    private String transactionDate;
    private UUID accountId;

    public Transaction(ResultSet rs) throws SQLException {
        this.transactionId = UUID.fromString(rs.getString("transaction_id"));
        this.transactionType = rs.getString("transaction_type");
        this.transactionAmount = rs.getFloat("transaction_amount");
        this.transactionDate = rs.getString("transaction_date");
        this.accountId = UUID.fromString(rs.getString("account_id"));
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
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

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }
}
