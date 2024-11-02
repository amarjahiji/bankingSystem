package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Card {
    private Integer cardId;
    private String cardNumber;
    private String cardExpiryDate;
    private String cardHolderName;
    private String cardCvv;
    private Integer cardTypeId;
    private Integer accountId;
    private List<CardType> cardType;


    public Card() {
    }

    public Card(ResultSet resultSet) throws SQLException {
        this.cardId = resultSet.getInt("card_id");
        this.cardNumber = resultSet.getString("card_number");
        this.cardExpiryDate = resultSet.getString("card_expiry_date");
        this.cardHolderName = resultSet.getString("card_holder_name");
        this.cardCvv = resultSet.getString("card_cvv");
        this.cardTypeId = resultSet.getInt("card_type_id");
        this.accountId = resultSet.getInt("account_id");
    }

    public Card(List<CardType> cardType) {
        this.cardType = cardType;
    }
    public Card(CardType cardType) {
        this.cardType = new ArrayList<>();
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
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