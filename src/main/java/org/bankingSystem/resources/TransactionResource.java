package org.bankingSystem.resources;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.Transaction;
import org.bankingSystem.services.TransactionService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Path("transaction")
public class TransactionResource {
    private final TransactionService TRANSACTION_SERVICE = new TransactionService();
    private final Gson GSON = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactions() {
        try {
            List<Transaction> transactions = TRANSACTION_SERVICE.getTransactions();
            if (!transactions.isEmpty()) {
                String json = GSON.toJson(transactions);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No transactions found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactionById(@PathParam("id") UUID transactionId) {
        try {
            Transaction transaction = TRANSACTION_SERVICE.getTransactionById(transactionId);
            if (transaction != null) {
                String json = GSON.toJson(transaction);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No transaction found with id: " + transactionId).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/add/secured/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransaction(String payload) {
        try {
            Transaction transaction = GSON.fromJson(payload, Transaction.class);
            Transaction createdTransaction = TRANSACTION_SERVICE.createTransaction(transaction);
            if (createdTransaction != null) {
                String json = GSON.toJson(createdTransaction);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NO_CONTENT).entity("Transaction not created").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}