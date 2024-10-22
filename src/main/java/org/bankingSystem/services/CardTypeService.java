package org.bankingSystem.services;

import org.bankingSystem.model.CardType;
import org.bankingSystem.queries.CardTypeSqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardTypeService {
    public List<CardType> getCardTypes(Connection connection) {
        List<CardType> cardTypes = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(CardTypeSqlQueries.GET_CARD_TYPES);
            while (resultSet.next()) {
                CardType newCardTypes = new CardType(resultSet);
                cardTypes.add(newCardTypes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardTypes;
    }
}
