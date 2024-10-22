package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class CardTypeResourceTest {

    @Test
    void getCardTypes() {
        Response response = new CardTypeResource().getCardTypes();
        System.out.println(response.getEntity());
    }
}