package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardType {
    private int cardTypeId;
    private String cardTypeName;

    public CardType() {
    }

    public CardType(ResultSet resultSet) throws SQLException {
        this.cardTypeId = resultSet.getInt("CARD_TYPE_ID");
        this.cardTypeName = resultSet.getString("CARD_TYPE_NAME");
    }

    public void setCardTypeId(int cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public int getCardTypeId() {
        return this.cardTypeId;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getCardTypeName() {
        return this.cardTypeName;
    }
}