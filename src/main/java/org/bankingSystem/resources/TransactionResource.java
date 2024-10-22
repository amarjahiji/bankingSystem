package org.bankingSystem.resources;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Transaction;
import org.bankingSystem.services.TransactionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("transaction")
public class TransactionResource {
    private final TransactionService transactionService = new TransactionService();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactions() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            List<Transaction> transactionList = transactionService.getTransactions(connection);
            String json = gson.toJson(transactionList);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching customers").build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactionById(@PathParam("id") int transactionId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            Transaction transaction = TransactionService.getTransactionById(connection, transactionId);
            String json = gson.toJson(transaction);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching customers").build();
        }
    }

    @Path("/create/secured/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransaction(String payload) {
        Transaction transaction = gson.fromJson(payload, Transaction.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Transaction transactions = TransactionService.createTransaction(connection, transaction);
            String json = gson.toJson(transactions);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating customer").build();
        }
    }
}