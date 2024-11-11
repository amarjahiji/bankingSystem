package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Card;
import org.bankingSystem.queries.CardSqlQueries;

import java.sql.*;
import java.util.*;

public class CardService {
    public List<Card> getCards() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Card> cards = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(CardSqlQueries.GET_CARDS)) {
            while (rs.next()) {
                Card CardModel = new Card(rs);
                cards.add(CardModel);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve cards." + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return cards;
    }

    public Card getCardById(UUID cardId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CardSqlQueries.GET_CARD_BY_ID)) {
            ps.setString(1, cardId.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Card(rs);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve card by id : " + cardId + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return null;
    }

    public Card createCard(Card card) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CardSqlQueries.CREATE_CARD)) {
            UUID uuid = UUID.randomUUID();
            ps.setString(1, uuid.toString());
            ps.setString(2, card.getCardNumber());
            ps.setString(3, card.getCardExpiryDate());
            ps.setString(5, card.getCardCvv());
            ps.setInt(6, card.getCardTypeId());
            ps.setString(7, card.getAccountId().toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to create a card.");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to create card : " + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return card;
    }

    public Card updateCardById(UUID cardId, Card card) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CardSqlQueries.UPDATE_CARD_BY_ID)) {
            ps.setString(1, card.getCardNumber());
            ps.setString(2, card.getCardExpiryDate());
            ps.setString(4, card.getCardCvv());
            ps.setInt(5, card.getCardTypeId());
            ps.setString(6, card.getAccountId().toString());
            ps.setString(7, cardId.toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update  card by id :" + cardId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update card by id: " + cardId + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return card;
    }

    public Card updateCardExpiryDateById(UUID cardId, Card cardExpiryDate) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CardSqlQueries.UPDATE_CARD_EXPIRY_DATE_BY_ID)) {
            ps.setString(1, cardExpiryDate.getCardExpiryDate());
            ps.setString(2, cardId.toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update card expiry date by card id :" + cardId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update card by id: " + cardId + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return cardExpiryDate;
    }

    public boolean deleteCardById(UUID cardId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CardSqlQueries.DELETE_CARD_BY_ID)) {
            ps.setString(1, cardId.toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to delete card by id :" + cardId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to delete card by id: " + cardId + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return true;
    }
}