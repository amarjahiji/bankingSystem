package org.bankingSystem.resources;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Card;
import org.bankingSystem.services.CardService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("card")
public class CardResource {
    private final CardService cardService = new CardService();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCards() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            List<Card> cardModel = cardService.getCards(connection);
            String json = gson.toJson(cardModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardById(@PathParam("id") int id) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            Card cardModel = cardService.getCardById(connection, id);
            String json = gson.toJson(cardModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @Path("/cardtype")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardsCardType() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            List<Card> cards = cardService.getCardsCardType(connection);
            String json = gson.toJson(cards);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching cards").build();
        }
    }

    @Path("/cardtype/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardCardTypeById(@PathParam("id") int cardId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            List<Card> cards = cardService.getCardCardTypeById(connection, cardId);
            String json = gson.toJson(cards);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching cards").build();
        }
    }

    @Path("/create/secured")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCard(String payload) {
        Card cardModel = gson.fromJson(payload, Card.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Card createdCardModel = cardService.createCard(connection, cardModel);
            String json = gson.toJson(createdCardModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @Path("/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCardById(@PathParam("id") int cardId, String payload) {
        Card card = gson.fromJson(payload, Card.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Card updatedCardModel = cardService.updateCardById(connection, cardId, card);
            String json = gson.toJson(updatedCardModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @Path("/expiration/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCardExpiryDateById(@PathParam("id") int cardId, String payload) {
        Card cardExpiryDate = gson.fromJson(payload, Card.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Card updatedCardModel = cardService.updateCardExpiryDateById(connection, cardId, cardExpiryDate);
            String json = gson.toJson(updatedCardModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/delete/secured/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCardById(@PathParam("id") int cardId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            boolean isDeleted = CardService.deleteCardById(connection, cardId);
            if (isDeleted) {
                return Response.ok("Card deleted successfully.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting card: " + e.getMessage()).build();
        }
    }
}