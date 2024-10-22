package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Card {
    private int cardId;
    private String cardNumber;
    private String cardExpiryDate;
    private String cardHolderName;
    private String cardCvv;
    private int cardTypeId;
    private int accountId;
    private List<CardType> cardType = new ArrayList<>();


    public Card() {
    }

    public Card(ResultSet resultSet) throws SQLException {
        this.cardId = resultSet.getInt("CARD_ID");
        this.cardNumber = resultSet.getString("CARD_NUMBER");
        this.cardExpiryDate = resultSet.getString("CARD_EXPIRY_DATE");
        this.cardHolderName = resultSet.getString("CARD_HOLDER_NAME");
        this.cardCvv = resultSet.getString("CARD_CVV");
        this.cardTypeId = resultSet.getInt("CARD_TYPE_ID");
        this.accountId = resultSet.getInt("ACCOUNT_ID");
    }

    public Card(List<CardType> cardType) {
        this.cardType = cardType;
    }
    public Card(CardType cardType) {
        this.cardType = new ArrayList<>();
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    public int getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(int cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public List<CardType> getCardType() {
        return cardType;
    }

    public void setAccount(List<CardType> cardType) {
        this.cardType = cardType;
    }
}