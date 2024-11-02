package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class CardTypeResourceTest {

    @Test
    void getCardTypes() throws SQLException {
        Response response = new CardTypeResource().getCardTypes();
        System.out.println(response.getEntity());
    }
}