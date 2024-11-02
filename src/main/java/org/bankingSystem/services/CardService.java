package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Card;
import org.bankingSystem.model.CardType;
import org.bankingSystem.queries.CardSqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardService {
    public List<Card> getCards() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Card> Cards = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(CardSqlQueries.GET_CARDS)) {
            while (rs.next()) {
                Card CardModel = new Card(rs);
                Cards.add(CardModel);
            }
            System.out.println("Cards retrieved successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get cards");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return Cards;
    }

    public Card getCardById(int cardId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement st = connection.prepareStatement(CardSqlQueries.GET_CARD_BY_ID)) {
            st.setInt(1, cardId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Card(rs);
            }
            System.out.println("Card retrieved successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get card by id" + cardId);
            throw e;
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return null;
    }

    public List<Card> getCardsCardType() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<Integer, Card> cardMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(CardSqlQueries.GET_CARDS_CARD_TYPE)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer cardId = rs.getInt("card_id");
                cardMap.putIfAbsent(cardId, new Card(rs));
                if (rs.getInt("card_type_id") != 0) {
                    CardType cardType = new CardType(rs);
                    cardMap.get(cardId).addCardType(cardType);
                }
            }
            System.out.println("Cards retrieved succcessfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve cards");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return new ArrayList<>(cardMap.values());
    }

    public List<Card> getCardCardTypeById(int cardId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<Integer, Card> cardMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(CardSqlQueries.GET_CARD_CARD_TYPE_BY_ID)) {
            ps.setInt(1, cardId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer customerIdd = rs.getInt("card_id");
                cardMap.putIfAbsent(customerIdd, new Card(rs));
                if (rs.getInt("card_type_id") != 0) {
                    CardType cardType = new CardType(rs);
                    cardMap.get(cardId).addCardType(cardType);
                }
            }
            System.out.println("Card retreived successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve card");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return new ArrayList<>(cardMap.values());
    }

    public Card createCard(Card card) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CardSqlQueries.CREATE_CARD)) {
            ps.setString(1, card.getCardNumber());
            ps.setString(2, card.getCardExpiryDate());
            ps.setString(3, card.getCardHolderName());
            ps.setString(4, card.getCardCvv());
            ps.setInt(5, card.getCardTypeId());
            ps.setInt(6, card.getAccountId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Card created successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create card");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return card;
    }

    public Card updateCardById(int accountId, Card card) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CardSqlQueries.UPDATE_CARD_BY_ID)) {
            ps.setString(1, card.getCardNumber());
            ps.setString(2, card.getCardExpiryDate());
            ps.setString(3, card.getCardHolderName());
            ps.setString(4, card.getCardCvv());
            ps.setInt(5, card.getCardTypeId());
            ps.setInt(6, card.getAccountId());
            ps.setInt(7, accountId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return card;
    }

    public Card updateCardExpiryDateById(int accountId, Card cardExpiryDate) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CardSqlQueries.UPDATE_CARD_EXPIRY_DATE_BY_ID)) {
            ps.setString(1, cardExpiryDate.getCardExpiryDate());
            ps.setInt(2, accountId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return cardExpiryDate;
    }

    public boolean deleteCardById(int cardId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CardSqlQueries.DELETE_CARD_BY_ID)) {
            ps.setInt(1, cardId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Deleted Card successful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting card with ID: " + cardId);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return true;
    }
}