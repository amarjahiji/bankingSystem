package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.CardType;
import org.bankingSystem.queries.CardTypeSqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardTypeService {
    public List<CardType> getCardTypes() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<CardType> cardTypes = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(CardTypeSqlQueries.GET_CARD_TYPES)){
            while (rs.next()) {
                CardType newCardTypes = new CardType(rs);
                cardTypes.add(newCardTypes);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving card types" + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return cardTypes;
    }
}
