package org.bankingSystem.resources;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.Card;
import org.bankingSystem.services.CardService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Path("card")
public class CardResource {
    private final CardService CARD_SERVICE = new CardService();
    private final Gson GSON = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCards() throws SQLException {
        List<Card> cards = CARD_SERVICE
                .getCards();
        String json = GSON.toJson(cards);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardById(@PathParam("id") UUID cardId) throws SQLException {
        Card card = CARD_SERVICE
                .getCardById(cardId);
        String json = GSON.toJson(card);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    //TODO : fix error
    @Path("/cardtype")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardsCardType() throws SQLException {
        List<Card> cards = CARD_SERVICE
                .getCardsCardType();
        String json = GSON.toJson(cards);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }


    @Path("/cardtype/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardCardTypeById(@PathParam("id") UUID cardId) throws SQLException {
        List<Card> card = CARD_SERVICE.
                getCardCardTypeById(cardId);
        String json = GSON.toJson(card);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/add/secured")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCard(String payload) throws SQLException {
        Card card = GSON.fromJson(payload, Card.class);
        Card createdCard = CARD_SERVICE
                .createCard(card);
        String json = GSON.toJson(createdCard);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCardById(@PathParam("id") UUID cardId, String payload) throws SQLException {
        Card card = GSON.fromJson(payload, Card.class);
        Card updatedCardModel = CARD_SERVICE
                .updateCardById(cardId, card);
        String json = GSON.toJson(updatedCardModel);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/expiration/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCardExpiryDateById(@PathParam("id") UUID cardId, String payload) throws SQLException {
        Card card = GSON.fromJson(payload, Card.class);
        Card updatedCard = CARD_SERVICE
                .updateCardExpiryDateById(cardId, card);
        String json = GSON.toJson(updatedCard);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/delete/secured/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCardById(@PathParam("id") UUID cardId) throws SQLException {
        boolean isDeleted = CARD_SERVICE
                .deleteCardById(cardId);
        if (isDeleted) {
            return Response.ok("Card deleted successfully.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Card not found").build();
        }
    }
}