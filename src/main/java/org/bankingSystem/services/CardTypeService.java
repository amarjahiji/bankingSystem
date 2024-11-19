package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.CardType;
import org.bankingSystem.queries.CardTypeSqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardTypeService extends AbstractService {
    public List<CardType> getCardTypes() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<CardType> cardTypes = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = connection.createStatement();
             rs = st.executeQuery(CardTypeSqlQueries.GET_CARD_TYPES);
            while (rs.next()) {
                CardType newCardTypes = new CardType(rs);
                cardTypes.add(newCardTypes);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving card types" + e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closeStatements(st);
        }
        return cardTypes;
    }
}
