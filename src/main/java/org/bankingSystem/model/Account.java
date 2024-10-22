package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private int accountId;
    private String accountNumber;
    private String accountType;
    private Double accountCurrentBalance;
    private String accountDateOpened;
    private String accountDateClosed;
    private String accountStatus;
    private Integer customerId;
    private List<Transaction> transactions = new ArrayList<>();
    private List<Card> cards = new ArrayList<>();

    public Account() {
    }

    public Account(ResultSet resultSet) throws SQLException {
        this.accountId = resultSet.getInt("ACCOUNT_ID");
        this.accountNumber = resultSet.getString("ACCOUNT_NUMBER");
        this.accountType = resultSet.getString("ACCOUNT_TYPE");
        this.accountCurrentBalance = resultSet.getDouble("ACCOUNT_CURRENT_BALANCE");
        this.accountDateOpened = resultSet.getString("ACCOUNT_DATE_OPENED");
        this.accountDateClosed = resultSet.getString("ACCOUNT_DATE_CLOSED");
        this.accountStatus = resultSet.getString("ACCOUNT_STATUS");
        this.customerId = resultSet.getInt("CUSTOMER_ID");
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int id) {
        this.accountId = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getAccountCurrentBalance() {
        return accountCurrentBalance;
    }

    public void setAccountCurrentBalance(Double currentBalance) {
        this.accountCurrentBalance = currentBalance;
    }

    public String getAccountDateOpened() {
        return accountDateOpened;
    }

    public void setAccountDateOpened(String dateOpened) {
        this.accountDateOpened = dateOpened;
    }

    public String getAccountDateClosed() {
        return accountDateClosed;
    }

    public void setAccountDateClosed(String dateClosed) {
        this.accountDateClosed = dateClosed;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransaction(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}