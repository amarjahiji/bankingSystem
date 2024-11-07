package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Card;
import org.bankingSystem.model.CardType;
import org.bankingSystem.queries.CardSqlQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CardService {
    public List<Card> getCards() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Card> Cards = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement
                (CardSqlQueries.GET_CARDS);
             ResultSet rs = ps.executeQuery()) {
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

    public Card getCardById(UUID cardId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CardSqlQueries.GET_CARD_BY_ID)) {
            ps.setString(1, cardId.toString());
            ResultSet rs = ps.executeQuery();
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
        Map<UUID, Card> cardMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement
                (CardSqlQueries.GET_CARDS_CARD_TYPE)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UUID cardId = UUID.fromString(rs.getString("card_id"));
                cardMap.putIfAbsent(cardId, new Card(rs));
                if (rs.getString("card_type_id") != null  || !rs.getString("card_type").isEmpty()) {
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

    public List<Card> getCardCardTypeById(UUID cardId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<UUID, Card> cardMap = new HashMap<>();
        try (PreparedStatement st = connection.prepareStatement
                (CardSqlQueries.GET_CARD_CARD_TYPE_BY_ID)) {
            st.setString(1, cardId.toString());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                UUID cardIdd = UUID.fromString(rs.getString("card_id"));
                cardMap.putIfAbsent(cardIdd, new Card(rs));
                if (rs.getString("card_type_id") != null) {
                    CardType cardType = new CardType(rs);
                    cardMap.get(cardIdd).addCardType(cardType);
                }
            }
            System.out.println("Card card type retrieved successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get customers' accounts");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return new ArrayList<>(cardMap.values());
    }

    public Card createCard(Card card) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CardSqlQueries.CREATE_CARD)) {
            UUID uuid = UUID.randomUUID();
            ps.setString(1, uuid.toString());
            ps.setString(2, card.getCardNumber());
            ps.setString(3, card.getCardExpiryDate());
            ps.setString(4, card.getCardHolderName());
            ps.setString(5, card.getCardCvv());
            ps.setInt(6, card.getCardTypeId());
            ps.setString(7, card.getAccountId().toString());
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

    public Card updateCardById(UUID cardId, Card card) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CardSqlQueries.UPDATE_CARD_BY_ID)) {
            ps.setString(1, card.getCardNumber());
            ps.setString(2, card.getCardExpiryDate());
            ps.setString(3, card.getCardHolderName());
            ps.setString(4, card.getCardCvv());
            ps.setInt(5, card.getCardTypeId());
            ps.setString(6, card.getAccountId().toString());
            ps.setString(7, cardId.toString());
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

    public Card updateCardExpiryDateById(UUID cardId, Card cardExpiryDate) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CardSqlQueries.UPDATE_CARD_EXPIRY_DATE_BY_ID)) {
            ps.setString(1, cardExpiryDate.getCardExpiryDate());
            ps.setString(2, cardId.toString());
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

    public boolean deleteCardById(UUID cardId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CardSqlQueries.DELETE_CARD_BY_ID)) {
            ps.setString(1, cardId.toString());
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