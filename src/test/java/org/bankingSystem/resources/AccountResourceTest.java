package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

class AccountResourceTest {

    @Test
    void getAccounts() throws SQLException {
        Response response = new AccountResource().getAccounts();
        System.out.println(response.getEntity());
    }

    @Test
    void getAccountById() throws SQLException {
        Response response = new AccountResource().getAccountById(UUID.fromString("12d29625-da9f-42b9-8523-4adc8ec7a187"));
        System.out.println(response.getEntity());
    }

    @Test
    void createAccount() throws SQLException {
        String payload = "{\n" +
                "    \"accountNumber\": \"100018\",\n" +
                "    \"accountType\": \"Savings\",\n" +
                "    \"accountCurrentBalance\": 200.00,\n" +
                "    \"accountDateOpened\": \"2009-01-02 00:27:40\",\n" +
                "    \"accountDateClosed\": \"2022-02-02 00:29:50\",\n" +
                "    \"accountStatus\": \"Inactive\",\n" +
                "    \"customerId\": 00ee5f22-6877-407a-b519-4063f517ed69\n" +
                "}";
        Response response = new AccountResource().createAccount(payload);
        System.out.println(response.getEntity());
    }

    @Test
    void updateAccountById() throws SQLException {
        String payload = "{\n" +
                "    \"accountNumber\": \"100018\",\n" +
                "    \"accountType\": \"Savings\",\n" +
                "    \"accountCurrentBalance\": 200.00,\n" +
                "    \"accountDateOpened\": \"2009-01-02 00:27:40\",\n" +
                "    \"accountDateClosed\": \"2022-02-02 02:37:00\",\n" +
                "    \"accountStatus\": \"Inactive\",\n" +
                "    \"customerId\": 00ee5f22-6877-407a-b519-4063f517ed69\n" +
                "}";
        Response response = new AccountResource().updateAccountById(UUID.fromString("12d29625-da9f-42b9-8523-4adc8ec7a187"), payload);
        System.out.println(response.getEntity());
    }

    @Test
    void updateAccountDateClosedById() throws SQLException {
        String payload = "{\n" +
                "  \"accountDateClosedById\": \"2022-02-02 02:00:00\"\n" +
                "}";
        Response response = new AccountResource().updateAccountDateClosedById(UUID.fromString("a1d7a3b5-e29b-41d4-a716-446655440000"), payload);
        System.out.println(response.getEntity());
    }

    @Test
    void deleteAccountById() throws SQLException {
        Response response = new AccountResource().deleteAccountById(UUID.fromString("a1d7a3b5-e29b-41d4-a716-446655440000"));
        System.out.println(response.getEntity());
    }

    @Test
    void getAccountNumberById() throws SQLException {
        Response response = new AccountResource().getAccountNumberById(UUID.fromString("a1d7a3b5-e29b-41d4-a716-446655440000"));
        System.out.println(response.getEntity());
    }
}