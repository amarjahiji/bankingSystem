package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Card {
    private UUID cardId;
    private String cardNumber;
    private String cardExpiryDate;
    private String cardHolderName;
    private String cardCvv;
    private Integer cardTypeId;
    private UUID accountId;
    private List<CardType> cardType;


    public Card() {
    }

    public Card(ResultSet rs) throws SQLException {
        this.cardId = UUID.fromString(rs.getString("card_id"));
        this.cardNumber = rs.getString("card_number");
        this.cardExpiryDate = rs.getString("card_expiry_date");
        this.cardHolderName = rs.getString("card_holder_name");
        this.cardCvv = rs.getString("card_cvv");
        this.cardTypeId = rs.getInt("card_type_id");
        this.accountId = UUID.fromString(rs.getString("account_id"));
    }

    public Card(List<CardType> cardType) {
        this.cardType = cardType;
    }

    public Card(CardType cardType) {
        this.cardType = new ArrayList<>();
    }

    public UUID getCardId() {
        return cardId;
    }

    public void setCardId(UUID cardId) {
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

    public Integer getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(Integer cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public List<CardType> getCardType() {
        if (cardType == null) {
            cardType = new ArrayList<>();
        }
        return cardType;
    }

    public void addCardType(CardType cardType) {
        getCardType().add(cardType);
    }

    public void setCardType(List<CardType> cardType) {
        this.cardType = cardType;
    }
}