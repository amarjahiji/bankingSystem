package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.CardType;
import org.bankingSystem.queries.CardTypeSqlQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardTypeService {
    public List<CardType> getCardTypes() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<CardType> cardTypes = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement
                (CardTypeSqlQueries.GET_CARD_TYPES)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CardType newCardTypes = new CardType(rs);
                cardTypes.add(newCardTypes);
            }
            System.out.println("Card retrieved successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get the card types");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return cardTypes;
    }
}
