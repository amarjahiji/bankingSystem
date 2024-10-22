package org.bankingSystem.resources;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Account;
import org.bankingSystem.services.AccountService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("account")
public class AccountResource {
    private final AccountService accountService = new AccountService();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccounts() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            List<Account> accountList = accountService.getAccounts(connection);
            String json = gson.toJson(accountList);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching accounts").build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountById(@PathParam("id") int accountId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            Account account = accountService.getAccountById(connection, accountId);
            if (account == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
            }
            String json = gson.toJson(account);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching account").build();
        }
    }


    @Path("/add/secured")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(String payload) {
        Account account = gson.fromJson(payload, Account.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Account updatedAccountModel = AccountService.createAccount(connection, account);
            String json = gson.toJson(updatedAccountModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Error creating account").build();
        }
    }

    @Path("/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountById(@PathParam("id") int accountId, String payload) {
        Account account = gson.fromJson(payload, Account.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Account accountModel = accountService.updateAccountById(connection, accountId, account);
            String json = gson.toJson(accountModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Error updating account").build();
        }
    }

    @Path("/dateclosed/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountDateClosedById(@PathParam("id") int accountId, String payload) {
        Account account = gson.fromJson(payload, Account.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Account AccountModel = AccountService.updateAccountDateClosedById(connection, accountId, account);
            String json = gson.toJson(AccountModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Error updating date closed").build();
        }
    }

    @Path("/balance/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountCurrentBalanceById(@PathParam("id") int accountId, String payload) {
        Account account = gson.fromJson(payload, Account.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Account AccountModel = AccountService.updateAccountCurrentBalanceById(connection, accountId, account);
            String json = gson.toJson(AccountModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Error updating balance").build();
        }
    }

    @Path("/status/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountStatusById(@PathParam("id") int accountId, String payload) {
        Account account = gson.fromJson(payload, Account.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Account AccountModel = AccountService.updateAccountStatusById(connection, accountId, account);
            String json = gson.toJson(AccountModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Error updating account status").build();
        }
    }

    @DELETE
    @Path("/delete/secured/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAccountById(@PathParam("id") int accountId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            boolean isDeleted = AccountService.deleteAccountById(connection, accountId);
            if (isDeleted) {
                return Response.ok("Account deleted successfully.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting account: " + e.getMessage()).build();
        }
    }

    //Additional, not required
    @Path("accountNumber/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountNumberById(@PathParam("id") int accountId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String accountNumber = accountService.getAccountNumberById(connection, accountId);
            if (accountNumber == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No Account Number found").build();
            }
            String json = gson.toJson(accountNumber);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching account number").build();
        }
    }
}