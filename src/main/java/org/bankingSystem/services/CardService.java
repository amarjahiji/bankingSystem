package org.bankingSystem.services;

import org.bankingSystem.model.Card;
import org.bankingSystem.model.CardType;
import org.bankingSystem.queries.CardSqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardService {
    public List<Card> getCards(Connection connection) {
        List<Card> Cards = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(CardSqlQueries.GET_CARDS)) {
            while (resultSet.next()) {
                Card CardModel = new Card(resultSet);
                Cards.add(CardModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Cards;
    }

    public Card getCardById(Connection connection, int cardNumber) throws SQLException {
        Card CardModel = new Card();
        try (PreparedStatement statement = connection.prepareStatement(CardSqlQueries.GET_CARD_BY_ID)) {
            statement.setInt(1, cardNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Card(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return CardModel;
    }

    public List<Card> getCardsCardType(Connection connection) {
        Map<Integer, Card> cardMap = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(CardSqlQueries.GET_CARDS_CARD_TYPE)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer cardId = resultSet.getInt("CARD_ID");
                    cardMap.put(cardId, new Card(resultSet));
                    cardMap.get(cardId).getCardType().add(new CardType(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(cardMap.values());
    }

    public List<Card> getCardCardTypeById(Connection connection, int cardId) {
        Map<Integer, Card> cardMap = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(CardSqlQueries.GET_CARD_CARD_TYPE_BY_ID)) {
            statement.setInt(1, cardId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer customerIdd = resultSet.getInt("CARD_ID");
                    cardMap.putIfAbsent(customerIdd, new Card(resultSet));
                    cardMap.get(customerIdd).getCardType().add(new CardType(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(cardMap.values());
    }

    public Card createCard(Connection connection, Card card) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CardSqlQueries.CREATE_CARD)) {
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setString(2, card.getCardExpiryDate());
            preparedStatement.setString(3, card.getCardHolderName());
            preparedStatement.setString(4, card.getCardCvv());
            preparedStatement.setInt(5, card.getCardTypeId());
            preparedStatement.setInt(6, card.getAccountId());
            int rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }

    public Card updateCardById(Connection connection, int accountId, Card card) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CardSqlQueries.UPDATE_CARD_BY_ID)) {
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setString(2, card.getCardExpiryDate());
            preparedStatement.setString(3, card.getCardHolderName());
            preparedStatement.setString(4, card.getCardCvv());
            preparedStatement.setInt(5, card.getCardTypeId());
            preparedStatement.setInt(6, card.getAccountId());
            preparedStatement.setInt(7, accountId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
                return card;
            } else {
                throw new SQLException("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Card updateCardExpiryDateById(Connection connection, int accountId, Card cardExpiryDate) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CardSqlQueries.UPDATE_CARD_EXPIRY_DATE_BY_ID)) {
            preparedStatement.setString(1, cardExpiryDate.getCardExpiryDate());
            preparedStatement.setInt(2, accountId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
                return cardExpiryDate;
            } else {
                throw new SQLException("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static boolean deleteCardById(Connection connection, int cardId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CardSqlQueries.DELETE_CARD_BY_ID)) {
            preparedStatement.setInt(1, cardId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error deleting card with ID: " + cardId);
        }
    }
}