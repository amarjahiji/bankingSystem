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
public class CardTypeResource extends AbstractResource {
    private final CardTypeService CARD_TYPE_SERVICE = new CardTypeService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardTypes() throws SQLException {
            List<CardType> cardTypes = CARD_TYPE_SERVICE.getCardTypes();
            if (!cardTypes.isEmpty()) {
                return cardTypesToJson(cardTypes, 200);
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No card Types found").build();
            }
    }
}