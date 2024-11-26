package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.CardType;
import org.bankingSystem.queries.CardTypeSqlQueries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CardTypeService extends CommonService {
    public List<CardType> getCardTypes() throws SQLException {
        List<CardType> cardTypes = new ArrayList<>();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnector.getConnection();
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
