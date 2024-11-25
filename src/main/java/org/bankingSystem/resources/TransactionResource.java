package org.bankingSystem.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.Transaction;
import org.bankingSystem.services.TransactionService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Path("transaction")
public class TransactionResource extends AbstractResource {
    private final TransactionService TRANSACTION_SERVICE = new TransactionService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactions() throws SQLException {
        List<Transaction> transactions = TRANSACTION_SERVICE.getTransactions();
        if (!transactions.isEmpty()) {
            return transactionsToJson(transactions, 200);
        } else {
            return notFound();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactionById(@PathParam("id") UUID transactionId) throws SQLException {
        Transaction transaction = TRANSACTION_SERVICE.getTransactionById(transactionId);
        if (transaction != null) {
            return transactionToJson(transaction, 200);
        } else {
            return notFound();
        }
    }

    @Path("/add/secured/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransaction(String payload) throws SQLException {
        Transaction transaction = transactionFromJson(payload);
        Transaction createdTransaction = TRANSACTION_SERVICE.createTransaction(transaction);
        if (createdTransaction != null) {
            return transactionToJson(createdTransaction, 200);
        } else {
            return notFound();
        }
    }
}