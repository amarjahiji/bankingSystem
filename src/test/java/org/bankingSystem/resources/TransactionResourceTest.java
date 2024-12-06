package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

class TransactionResourceTest {

    @Test
    void getTransactions() throws SQLException {
        Response response = new TransactionResource().getTransactions();
        System.out.println(response.getEntity());
    }

    @Test
    void getTransactionById() throws SQLException {
        Response response = new TransactionResource().getTransactionById(UUID.fromString("f07a3b5-e29b-41d4-a716-446655440015"));
        System.out.println(response.getEntity());
    }

    @Test
    void createTransaction() throws SQLException {
        String payload = "{\n" +
                "    \"transactionType\": \"Withdrawal\",\n" +
                "    \"transactionAmount\": 250.00 ,\n" +
                "    \"transactionDate\": \"2024-01-10 \",\n" +
                "    \"accountId\": a8d7a3b5-e29b-41d4-a716-446655440007\n" +
                "    \"transactionDescription\": Details ...\n" +
                "}";
    Response response = new TransactionResource().createTransaction(payload);
    System.out.println(response.getEntity());
    }
}