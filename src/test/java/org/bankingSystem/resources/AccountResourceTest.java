package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class AccountResourceTest {

    @Test
    void getAccounts() {
        Response response = new AccountResource().getAccounts();
        System.out.println(response.getEntity());
    }

    @Test
    void getAccountById() {
        Response response = new AccountResource().getAccountById(1);
        System.out.println(response.getEntity());
    }

    @Test
    void createAccount() {
        String payload = "{\n" +
                "    \"accountNumber\": \"1234760912763907\",\n" +
                "    \"accountType\": \"Savings\",\n" +
                "    \"accountCurrentBalance\": 200.00,\n" +
                "    \"accountDateOpened\": \"2009-01-02\",\n" +
                "    \"accountDateClosed\": \"2022-02-02\",\n" +
                "    \"accountStatus\": \"Inactive\",\n" +
                "    \"customerId\": 1\n" +
                "}";
        Response response = new AccountResource().createAccount(payload);
        System.out.println(response.getEntity());
    }

    @Test
    void updateAccountById() {
        String payload = "{\n" +
                "    \"accountNumber\": \"1234760911113907\",\n" +
                "    \"accountType\": \"Savings\",\n" +
                "    \"accountCurrentBalance\": 200.00,\n" +
                "    \"accountDateOpened\": \"2009-01-02\",\n" +
                "    \"accountDateClosed\": \"2022-02-02\",\n" +
                "    \"accountStatus\": \"Inactive\",\n" +
                "    \"customerId\": 2\n" +
                "}";
        Response response = new AccountResource().updateAccountById(9, payload);
        System.out.println(response.getEntity());
    }

    @Test
    void updateAccountDateClosedById() {
        String payload = "{\n" +
                "  \"accountDateClosedById\": \"2022-0202\"\n" +
                "}";
        Response response = new AccountResource().updateAccountDateClosedById(3, payload);
        System.out.println(response.getEntity());
    }

    @Test
    void deleteAccountById() {
        Response response = new AccountResource().deleteAccountById(10);
        System.out.println(response.getEntity());
    }

    @Test
    void getAccountNumberById() {
        Response response = new AccountResource().getAccountNumberById(9);
        System.out.println(response.getEntity());
    }
}