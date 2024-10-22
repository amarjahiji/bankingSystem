package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;


class CardResourceTest {

    @Test
    void getCards() {
        Response response = new CardResource().getCards();
        System.out.println(response.getEntity());
    }

    @Test
    void getCardById() {
        Response response = new CardResource().getCardById(1);
        System.out.println(response.getEntity());
    }

    @Test
    void getCardsCardType() {
        Response response = new CardResource().getCardsCardType();
        System.out.println(response.getEntity());
    }

    @Test
    void getCardCardType() {
        Response response = new CardResource().getCardCardTypeById(1);
        System.out.println(response.getEntity());
    }

    @Test
    void createCard() {
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
    void updateCardById() {
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
    void updateCardExpiryDateById() {
        String payload = "{\n" +
                "  \"cardExpiryDate\": \"2028-02-02\"\n" +
                "}";
        Response response = new CardResource().updateCardExpiryDateById(3, payload);
        System.out.println(response.getEntity());
    }

    @Test
    void deleteCardById() {
        Response response = new CardResource().deleteCardById(9);
        System.out.println(response.getEntity());
    }
}