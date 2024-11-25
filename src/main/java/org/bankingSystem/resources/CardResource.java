package org.bankingSystem.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.Card;
import org.bankingSystem.services.CardService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Path("card")
public class CardResource extends AbstractResource {
    private final CardService CARD_SERVICE = new CardService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCards() throws SQLException {
        List<Card> cards = CARD_SERVICE.getCards();
        if (!cards.isEmpty()) {
            return cardsToJson(cards, 200);
        } else {
            return notFound();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardById(@PathParam("id") UUID cardId) throws SQLException {
        Card card = CARD_SERVICE.getCardById(cardId);
        if (card != null) {
            return cardToJson(card, 200);
        } else {
            return notFound();
        }
    }

    @Path("/add/secured")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCard(String payload) throws SQLException {
        Card card = cardFromJson(payload);
        Card createdCard = CARD_SERVICE.createCard(card);
        if (createdCard != null) {
            return cardToJson(createdCard, 200);
        } else {
            return notFound();
        }
    }

    @Path("/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCardById(@PathParam("id") UUID cardId, String payload) throws SQLException {
        Card card = cardFromJson(payload);
        Card updatedCardModel = CARD_SERVICE.updateCardById(cardId, card);
        if (updatedCardModel != null) {
            return cardToJson(updatedCardModel, 200);
        } else {
            return notFound();
        }
    }

    @Path("/expiration/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCardExpiryDateById(@PathParam("id") UUID cardId, String payload) throws SQLException {
        Card card = cardFromJson(payload);
        Card updatedCard = CARD_SERVICE.updateCardExpiryDateById(cardId, card);
        if (updatedCard != null) {
            return cardToJson(updatedCard, 200);
        } else {
            return notFound();
        }
    }

    @DELETE
    @Path("/delete/secured/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCardById(@PathParam("id") UUID cardId) throws SQLException {
        boolean isDeleted = CARD_SERVICE.deleteCardById(cardId);
        if (isDeleted) {
            return Response.ok("Card deleted successfully.").build();
        } else {
            return notFound();
        }
    }
}