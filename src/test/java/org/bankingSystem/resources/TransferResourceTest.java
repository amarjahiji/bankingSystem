package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

class TransferResourceTest {


    @Test
    void getAllTransfers() throws SQLException {
        Response response = new TransferResource().getAllTransfers();
        System.out.println(response.getEntity());
    }

    @Test
    void getCurrentMonthsTransfers() throws SQLException {
        Response response = new TransferResource().getCurrentMonthsTransfers();
    }

    @Test
    void getTransferById() throws SQLException {
        Response response = new TransferResource().getTransferById(UUID.fromString("f07a3b5-e29b-41d4-a716-446655440015"));
        System.out.println(response.getEntity());
    }

    @Test
    void getTransfersFilteredByMonth() throws SQLException {
        Response response = new TransferResource().getTransfersFilteredByMonth(2, 2024);
        System.out.println(response.getEntity());
    }

    @Test
    void getTransfersBySender() throws SQLException {
        Response response = new TransferResource().getTransfersBySender(UUID.fromString("f07a3b5-e29b-41d4-a716-446655440015"));
        System.out.println(response.getEntity());
    }

    @Test
    void getTransfersByReceiver() throws SQLException {
        Response response = new TransferResource().getTransfersByReceiver(UUID.fromString("f07a3b5-e29b-41d4-a716-446655440015"));
        System.out.println(response.getEntity());
    }

    @Test
    void createTransfer() {
        String payload = "{\n" +
                "    \"sender_account_id\": \"a8d7a3b5-e29b-41d4-a716-446655440007\",\n" +
                "    \"receiver_account_id\": \"f07a3b5-e29b-41d4-a716-446655440015\",\n" +
                "    \"transfer_amount\": \"250.50 \",\n" +
                "    \"transfer_date\": \"2024-01-10 \", \n" +
                "}";
    }
}