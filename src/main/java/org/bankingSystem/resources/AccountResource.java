package org.bankingSystem.resources;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.Account;
import org.bankingSystem.services.AccountService;
import java.sql.SQLException;
import java.util.List;

@Path("account")
public class AccountResource {
    private final AccountService ACCOUNT_SERVICE = new AccountService();
    private final Gson GSON = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccounts() throws SQLException {
        List<Account> accountList = ACCOUNT_SERVICE.getAccounts();
        String json = GSON.toJson(accountList);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountById(@PathParam("id") int accountId) throws SQLException {
        Account account = ACCOUNT_SERVICE.getAccountById(accountId);
        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
        }
        String json = GSON.toJson(account);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }


    @Path("/add/secured")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(String payload) throws SQLException {
        Account account = GSON.fromJson(payload, Account.class);
        Account updatedAccountModel = ACCOUNT_SERVICE.createAccount(account);
        String json = GSON.toJson(updatedAccountModel);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountById(@PathParam("id") int accountId, String payload) throws SQLException {
        Account account = GSON.fromJson(payload, Account.class);
        Account accountModel = ACCOUNT_SERVICE.updateAccountById(accountId, account);
        String json = GSON.toJson(accountModel);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/dateclosed/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountDateClosedById(@PathParam("id") int accountId, String payload) throws SQLException {
        Account account = GSON.fromJson(payload, Account.class);
        Account AccountModel = ACCOUNT_SERVICE.updateAccountDateClosedById(accountId, account);
        String json = GSON.toJson(AccountModel);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/balance/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountCurrentBalanceById(@PathParam("id") int accountId, String payload) throws SQLException {
        Account account = GSON.fromJson(payload, Account.class);
        Account accountModel = ACCOUNT_SERVICE.updateAccountCurrentBalanceById(accountId, account);
        String json = GSON.toJson(accountModel);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/status/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountStatusById(@PathParam("id") int accountId, String payload) throws SQLException {
        Account account = GSON.fromJson(payload, Account.class);
        Account AccountModel = ACCOUNT_SERVICE.updateAccountStatusById(accountId, account);
        String json = GSON.toJson(AccountModel);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/delete/secured/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAccountById(@PathParam("id") int accountId) throws SQLException {
        boolean isDeleted = ACCOUNT_SERVICE.deleteAccountById(accountId);
        if (isDeleted) {
            return Response.ok("Account deleted successfully.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
        }
    }

    //Additional, not required
    @Path("accountnumber/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountNumberById(@PathParam("id") int accountId) throws SQLException {
        String accountNumber = ACCOUNT_SERVICE.getAccountNumberById(accountId);
        if (accountNumber == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No Account Number found").build();
        }
        String json = GSON.toJson(accountNumber);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}