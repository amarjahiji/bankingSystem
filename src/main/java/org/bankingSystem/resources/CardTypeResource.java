package org.bankingSystem.resources;

import com.google.gson.Gson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.CardType;
import org.bankingSystem.services.CardTypeService;

import java.sql.SQLException;
import java.util.List;

@Path("cardtype")
public class CardTypeResource {
    private final CardTypeService CARD_TYPE_SERVICE = new CardTypeService();
    private final Gson GSON = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardTypes() throws SQLException {
            List<CardType> cardTypeModels = CARD_TYPE_SERVICE.getCardTypes();
            String json = GSON.toJson(cardTypeModels);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}