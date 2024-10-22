package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class TransactionResourceTest {

    @Test
    void getTransactions() {
        Response response = new TransactionResource().getTransactions();
        System.out.println(response.getEntity());
    }

    @Test
    void getTransactionById() {
        Response response = new TransactionResource().getTransactionById(1);
        System.out.println(response.getEntity());
    }

    @Test
    void createTransaction() {
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