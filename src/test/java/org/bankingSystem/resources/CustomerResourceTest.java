package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

class CustomerResourceTest {


    @Test
    void getCustomers() throws SQLException {
        Response response = new CustomerResource().getCustomers();
        System.out.println(response.getEntity());
    }

    @Test
    void getCustomerById() throws SQLException {
        Response response = new CustomerResource().getCustomerById(UUID.fromString("7e9d3c3f-bc62-4be3-92dd-3a8ae89e3aa9"));
        System.out.println(response.getEntity());
    }

    @Test
    void getCustomersAccounts() throws SQLException {
        Response response = new CustomerResource().getCustomersAccounts();
        System.out.println(response.getEntity());
    }

    @Test
    void getCustomerAccountsById() throws SQLException {
        Response response = new CustomerResource().getCustomerAccountsById(UUID.fromString("7e9d3c3f-bc62-4be3-92dd-3a8ae89e3aa9"));
        System.out.println(response.getEntity());
    }

    @Test
    void createCustomer() throws SQLException {
        String payload = "{\n" +
                "  \"customerId\": \"7e9d3c3f-bc62-4be3-92dd-3a8ae89e3aa9\",\n" +
                "  \"customerFirstName\": \"Jack\",\n" +
                "  \"customerLastName\": \"House\",\n" +
                "  \"customerDateOfBirth\": \"2004-05-01\",\n" +
                "  \"customerEmail\": \"jackh@gmail.com\",\n" +
                "  \"customerPhoneNumber\": \"932-223-0204\",\n" +
                "  \"customerAddress\": \"South Jersey, 203 B2\"\n" +
                "}";
        Response response = new CustomerResource().createCustomer(payload);
        System.out.println(response.getEntity());

    }

    @Test
    void updateCustomerById() throws SQLException {
        String payload = "{\n" +
                "  \"customerFirstName\": \"Jack\",\n" +
                "  \"customerLastName\": \"House\",\n" +
                "  \"customerDateOfBirth\": \"2004-05-01\",\n" +
                "  \"customerEmail\": \"jackh@gmail.com\",\n" +
                "  \"customerPhoneNumber\": \"932-223-0204\",\n" +
                "  \"customerAddress\": \"South Jersey, 203 B2\"\n" +
                "}";
        Response response = new CustomerResource().updateCustomerById(UUID.fromString("7e9d3c3f-bc62-4be3-92dd-3a8ae89e3aa9"), payload);
        System.out.println(response.getEntity());
    }

    @Test
    void updateCustomerAddressById() throws SQLException {
        String payload = "{\n" +
                "  \"customerAddress\": \"South Jersey, 203 B2\"\n" +
                "}";
        Response response = new CustomerResource().updateCustomerAddressById(UUID.fromString("7e9d3c3f-bc62-4be3-92dd-3a8ae89e3aa9"), payload);
        System.out.println(response.getEntity());
    }

    @Test
    void deleteCustomerById() throws SQLException {
        Response response = new CustomerResource().deleteCustomerById(UUID.fromString("7e9d3c3f-bc62-4be3-92dd-3a8ae89e3aa9"));
        System.out.println(response.getEntity());
    }

    @Test
    void getCustomerAccountsTransactionsById() throws SQLException {
        Response response = new CustomerResource().getCustomerAccountsTransactionsById(UUID.fromString("7e9d3c3f-bc62-4be3-92dd-3a8ae89e3aa9"));
        System.out.println(response.getEntity());
    }

    @Test
    void getCustomersAccountsTransactions() throws SQLException {
        Response response = new CustomerResource().getCustomersAccountsTransactions();
        System.out.println(response.getEntity());
    }

    @Test
    void getCustomerAccountsCardsById() throws SQLException {
        Response response = new CustomerResource().getCustomerAccountsCardsById(UUID.fromString("7e9d3c3f-bc62-4be3-92dd-3a8ae89e3aa9"));
        System.out.println(response.getEntity());
    }

    @Test
    void getCustomersAccountsCards() throws SQLException {
        Response response = new CustomerResource().getCustomersAccountsCards();
        System.out.println(response.getEntity());
    }

    @Test
    void getCustomerFirstNameById() throws SQLException {
        Response response = new CustomerResource().getCustomerFirstNameById(UUID.fromString("7e9d3c3f-bc62-4be3-92dd-3a8ae89e3aa9"));
        System.out.println(response.getEntity());
    }
}
