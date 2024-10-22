package org.bankingSystem.resources;

import com.google.gson.Gson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.CardType;
import org.bankingSystem.services.CardTypeService;

import java.sql.Connection;
import java.util.List;

@Path("cardtype")
public class CardTypeResource {
    private final CardTypeService cardTypeService = new CardTypeService();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardTypes() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            List<CardType> cardTypeModels = cardTypeService.getCardTypes(connection);
            String json = gson.toJson(cardTypeModels);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}