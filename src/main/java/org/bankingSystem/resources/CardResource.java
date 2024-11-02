package org.bankingSystem.resources;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.Card;
import org.bankingSystem.services.CardService;

import java.sql.SQLException;
import java.util.List;

@Path("card")
public class CardResource {
    private final CardService CARD_SERVICE = new CardService();
    private final Gson GSON = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCards() throws SQLException {
        List<Card> cardModel = CARD_SERVICE.getCards();
        String json = GSON.toJson(cardModel);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

@Path("/{id}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response getCardById(@PathParam("id") int id) throws SQLException {
        Card cardModel = CARD_SERVICE.getCardById(id);
        String json = GSON.toJson(cardModel);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }


@Path("/cardtype")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response getCardsCardType() throws SQLException {
        List<Card> cards = CARD_SERVICE.getCardsCardType();
        String json = GSON.toJson(cards);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }


@Path("/cardtype/{id}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response getCardCardTypeById(@PathParam("id") int cardId) throws SQLException {
        List<Card> cards = CARD_SERVICE.getCardCardTypeById(cardId);
        String json = GSON.toJson(cards);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
}

@Path("/add/secured")
@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response createCard(String payload) throws SQLException {
    Card cardModel = GSON.fromJson(payload, Card.class);
        Card createdCardModel = CARD_SERVICE.createCard(cardModel);
        String json = GSON.toJson(createdCardModel);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

@Path("/update/secured/{id}")
@PUT
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response updateCardById(@PathParam("id") int cardId, String payload) throws SQLException {
    Card card = GSON.fromJson(payload, Card.class);
        Card updatedCardModel = CARD_SERVICE.updateCardById(cardId, card);
        String json = GSON.toJson(updatedCardModel);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

@Path("/expiration/update/secured/{id}")
@PUT
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response updateCardExpiryDateById(@PathParam("id") int cardId, String payload) throws SQLException {
    Card cardExpiryDate = GSON.fromJson(payload, Card.class);
        Card updatedCardModel = CARD_SERVICE.updateCardExpiryDateById(cardId, cardExpiryDate);
        String json = GSON.toJson(updatedCardModel);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
}

@DELETE
@Path("/delete/secured/{id}")
@Produces(MediaType.APPLICATION_JSON)
public Response deleteCardById(@PathParam("id") int cardId) throws SQLException {
        boolean isDeleted = CARD_SERVICE.deleteCardById(cardId);
        if (isDeleted) {
            return Response.ok("Card deleted successfully.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }
}
}