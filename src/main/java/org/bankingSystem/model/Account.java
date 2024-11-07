package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Account {
    private UUID accountId;
    private String accountNumber;
    private String accountType;
    private Double accountCurrentBalance;
    private String accountDateOpened;
    private String accountDateClosed;
    private String accountStatus;
    private UUID customerId;
    private List<Transaction> transactions;
    private List<Card> cards;

    public Account(ResultSet rs) throws SQLException {
        this.accountId = UUID.fromString(rs.getString("account_id"));
        this.accountNumber = rs.getString("account_number");
        this.accountType = rs.getString("account_type");
        this.accountCurrentBalance = rs.getDouble("account_current_balance");
        this.accountDateOpened = rs.getString("account_date_opened");
        this.accountDateClosed = rs.getString("account_date_closed");
        this.accountStatus = rs.getString("account_status");
        this.customerId = UUID.fromString(rs.getString("customer_id"));
        this.transactions = null;
        this.cards = null;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
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

    public void setAccountCurrentBalance(Double accountCurrentBalance) {
        this.accountCurrentBalance = accountCurrentBalance;
    }

    public String getAccountDateOpened() {
        return accountDateOpened;
    }

    public void setAccountDateOpened(String accountDateOpened) {
        this.accountDateOpened = accountDateOpened;
    }

    public String getAccountDateClosed() {
        return accountDateClosed;
    }

    public void setAccountDateClosed(String accountDateClosed) {
        this.accountDateClosed = accountDateClosed;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public List<Transaction> getTransactions() {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        getTransactions().add(transaction);
    }

    public void setTransaction(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Card> getCards() {
        if (cards == null) {
            cards = new ArrayList<>();
        }
        return cards;
    }

    public void addCards(Card card) {
        getCards().add(card);
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}