package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class TransactionResourceTest {

    @Test
    void getTransactions() throws SQLException {
        Response response = new TransactionResource().getTransactions();
        System.out.println(response.getEntity());
    }

    @Test
    void getTransactionById() throws SQLException {
        Response response = new TransactionResource().getTransactionById(1);
        System.out.println(response.getEntity());
    }

    @Test
    void createTransaction() throws SQLException {
        String payload = "{\n" +
                "    \"transactionType\": \"Withdrawal\",\n" +
                "    \"transactionAmount\": 250.00 ,\n" +
                "    \"transactionDate\": \"2024-01-10\",\n" +
                "    \"accountId\": 4\n" +
                "}";
    Response response = new TransactionResource().createTransaction(payload);
    System.out.println(response.getEntity());
    }
}