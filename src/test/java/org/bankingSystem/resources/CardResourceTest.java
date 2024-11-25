package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;


class CardResourceTest {

    @Test
    void getCards() throws SQLException {
        Response response = new CardResource().getCards();
        System.out.println(response.getEntity());
    }

    @Test
    void getCardById() throws SQLException {
        Response response = new CardResource().getCardById(UUID.fromString("d1e7a3b5-e29b-41d4-a716-446655440000"));
        System.out.println(response.getEntity());
    }

    @Test
    void createCard() throws SQLException {
        String payload = "{\n"
                + "  \"cardId\": \"d1e7a3b5-e29b-41d4-a716-446655440000\",\n"
                + "  \"cardNumber\": \"1233217863748916\",\n"
                + "  \"cardExpiryDate\": \"2028-08-08 00:00:00\",\n"
                + "  \"cardHolderName\": \"Dan Doe\",\n"
                + "  \"cardCvv\": \"111\",\n"
                + "  \"cardTypeId\": 2,\n"
                + "  \"accountId\": a9d7a3b5-e29b-41d4-a716-446655440008\n"
                + "}";

        Response response = new CardResource().createCard(payload);
        System.out.println(response.getEntity());
    }

    @Test
    void updateCardById() throws SQLException {
        String payload = "{\n" +
                "  \"cardNumber\": \"1223417863748916\",\n" +
                "  \"cardExpiryDate\": \"2028-08-08 02:04:04\",\n" +
                "  \"cardHolderName\": \"Dan Doe\",\n" +
                "  \"cardCvv\": \"111\",\n" +
                "  \"cardTypeId\": 2,\n" +
                "  \"accountId\": a9d7a3b5-e29b-41d4-a716-446655440008\n" +
                "}";
        Response response = new CardResource().updateCardById(UUID.fromString("d1e7a3b5-e29b-41d4-a716-446655440000"), payload);
        System.out.println(response.getEntity());
    }

    @Test
    void updateCardExpiryDateById() throws SQLException {
        String payload = "{\n" +
                "  \"cardExpiryDate\": \"2028-02-02\"\n" +
                "}";
        Response response = new CardResource().updateCardExpiryDateById(UUID.fromString("d1e7a3b5-e29b-41d4-a716-446655440000"), payload);
        System.out.println(response.getEntity());
    }

    @Test
    void deleteCardById() throws SQLException {
        Response response = new CardResource().deleteCardById(UUID.fromString("d1e7a3b5-e29b-41d4-a716-446655440000"));
        System.out.println(response.getEntity());
    }
}