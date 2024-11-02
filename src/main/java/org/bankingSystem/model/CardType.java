package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardType {
    private Integer cardTypeId;
    private String cardTypeName;

    public CardType(ResultSet resultSet) throws SQLException {
        this.cardTypeId = resultSet.getInt("card_type_id");
        this.cardTypeName = resultSet.getString("card_type_name");
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