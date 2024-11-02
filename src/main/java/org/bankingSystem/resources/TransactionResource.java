package org.bankingSystem.resources;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.Transaction;
import org.bankingSystem.services.TransactionService;

import java.sql.SQLException;
import java.util.List;

@Path("transaction")
public class TransactionResource {
    private final TransactionService TRANSACTION_SERVICE = new TransactionService();
    private final Gson GSON = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactions() throws SQLException {
            List<Transaction> transactionList = TRANSACTION_SERVICE.getTransactions();
            String json = GSON.toJson(transactionList);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();

    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactionById(@PathParam("id") int transactionId) throws SQLException {
            Transaction transaction = TRANSACTION_SERVICE.getTransactionById(transactionId);
            String json = GSON.toJson(transaction);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/add/secured/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransaction(String payload) throws SQLException {
        Transaction transaction = GSON.fromJson(payload, Transaction.class);
            Transaction transactions = TRANSACTION_SERVICE.createTransaction(transaction);
            String json = GSON.toJson(transactions);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}