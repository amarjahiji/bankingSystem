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
    public Response getCards() {
        try {
            List<Card> cards = CARD_SERVICE.getCards();
            if (cards.isEmpty()) {
                String json = GSON.toJson(cards);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Cards not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardById(@PathParam("id") UUID cardId) {
        try {
            Card card = CARD_SERVICE.getCardById(cardId);
            if (card != null) {
                String json = GSON.toJson(card);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Card with id: " + cardId + "not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/cardtype")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardsCardType() {
        try {
            List<Card> cards = CARD_SERVICE.getCardsCardType();
            if (!cards.isEmpty()) {
                String json = GSON.toJson(cards);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Cards not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }


    @Path("/cardtype/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardCardTypeById(@PathParam("id") UUID cardId) {
        try {
            List<Card> card = CARD_SERVICE.getCardCardTypeById(cardId);
            if (!card.isEmpty()) {
                String json = GSON.toJson(card);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/add/secured")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCard(String payload) {
        try {
            Card card = GSON.fromJson(payload, Card.class);
            Card createdCard = CARD_SERVICE.createCard(card);
            if (createdCard != null) {
                String json = GSON.toJson(createdCard);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NO_CONTENT).entity("Card not created").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCardById(@PathParam("id") UUID cardId, String payload) {
        try {
            Card card = GSON.fromJson(payload, Card.class);
            Card updatedCardModel = CARD_SERVICE.updateCardById(cardId, card);
            if (updatedCardModel != null) {
                String json = GSON.toJson(updatedCardModel);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NO_CONTENT).entity("Card with id: " + cardId + "not updated").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/expiration/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCardExpiryDateById(@PathParam("id") UUID cardId, String payload) {
        try {
            Card card = GSON.fromJson(payload, Card.class);
            Card updatedCard = CARD_SERVICE.updateCardExpiryDateById(cardId, card);
            if (updatedCard != null) {
                String json = GSON.toJson(updatedCard);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Card with id: " + cardId + "not updated").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/delete/secured/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCardById(@PathParam("id") UUID cardId) {
        try {
            boolean isDeleted = CARD_SERVICE.deleteCardById(cardId);
            if (isDeleted) {
                return Response.ok("Card deleted successfully.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Card not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}