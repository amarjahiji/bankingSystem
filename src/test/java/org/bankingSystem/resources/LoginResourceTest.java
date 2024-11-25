package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class LoginResourceTest {
    @Test
    void login() throws SQLException {
        Response response = new LoginResource().login("admin@gmail.com", "admin");
        System.out.println(response.getEntity());
    }

    @Test
    void signup() throws SQLException {
        String payload = "{\n" +
                "  \"adminFirstName\": \"John\"\n" +
                "  \"adminLastName\": \"Smith\"\n" +
                "  \"adminEmail\": \"johnsmith@gmail.com\"\n" +
                "  \"adminPassword\": \"smith123\"\n" +
                "  \"adminSecurityClearance\": \"Full\"\n" +
                "}";
        Response response = new LoginResource().signup(payload);
        System.out.println(response.getEntity());
    }
}
