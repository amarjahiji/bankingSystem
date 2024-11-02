package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;


class CardResourceTest {

    @Test
    void getCards() throws SQLException {
        Response response = new CardResource().getCards();
        System.out.println(response.getEntity());
    }

    @Test
    void getCardById() throws SQLException {
        Response response = new CardResource().getCardById(1);
        System.out.println(response.getEntity());
    }

    @Test
    void getCardsCardType() throws SQLException {
        Response response = new CardResource().getCardsCardType();
        System.out.println(response.getEntity());
    }

    @Test
    void getCardCardType() throws SQLException {
        Response response = new CardResource().getCardCardTypeById(1);
        System.out.println(response.getEntity());
    }

    @Test
    void createCard() throws SQLException {
        String payload = "{\n"
                + "  \"cardNumber\": \"1233217863748916\",\n"
                + "  \"cardExpiryDate\": \"2028-08-08\",\n"
                + "  \"cardHolderName\": \"Dan Doe\",\n"
                + "  \"cardCvv\": \"111\",\n"
                + "  \"cardTypeId\": 2,\n"
                + "  \"accountId\": 1\n"
                + "}";
        Response response = new CardResource().createCard(payload);
        System.out.println(response.getEntity());
    }

    @Test
    void updateCardById() throws SQLException {
        String payload = "{\n" +
                "  \"cardNumber\": \"1223417863748916\",\n" +
                "  \"cardExpiryDate\": \"2028-08-08\",\n" +
                "  \"cardHolderName\": \"Dan Doe\",\n" +
                "  \"cardCvv\": \"111\",\n" +
                "  \"cardTypeId\": 2,\n" +
                "  \"accountId\": 1\n" +
                "}";
        Response response = new CardResource().updateCardById(1, payload);
        System.out.println(response.getEntity());
    }

    @Test
    void updateCardExpiryDateById() throws SQLException {
        String payload = "{\n" +
                "  \"cardExpiryDate\": \"2028-02-02\"\n" +
                "}";
        Response response = new CardResource().updateCardExpiryDateById(3, payload);
        System.out.println(response.getEntity());
    }

    @Test
    void deleteCardById() throws SQLException {
        Response response = new CardResource().deleteCardById(9);
        System.out.println(response.getEntity());
    }
}