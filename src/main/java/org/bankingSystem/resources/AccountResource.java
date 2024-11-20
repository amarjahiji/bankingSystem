package org.bankingSystem.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.Account;
import org.bankingSystem.services.AccountService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Path("account/secured")
public class AccountResource extends AbstractResource {
    private final AccountService ACCOUNT_SERVICE = new AccountService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccounts() throws SQLException {
        List<Account> accounts = ACCOUNT_SERVICE.getAccounts();
        if (!accounts.isEmpty()) {
            return accountToJson(accounts, 200);
        } else {
            return notFound();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountById(@PathParam("id") UUID accountId) throws SQLException {
        Account account = ACCOUNT_SERVICE.getAccountById(accountId);
        if (account != null) {
            return accountToJson(account, 200);
        } else {
            return notFound();
        }
    }

    @Path("/add/secured")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(String payload) throws SQLException {
        Account account = accountFromJson(payload);
        Account createdAccount = ACCOUNT_SERVICE.createAccount(account);
        if (createdAccount != null) {
            return accountToJson(createdAccount, 200);
        } else {
            return notFound();
        }
    }

    @Path("/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountById(@PathParam("id") UUID accountId, String payload) throws SQLException {
        Account account = accountFromJson(payload);
        Account updatedAccount = ACCOUNT_SERVICE.updateAccountById(accountId, account);
        if (updatedAccount != null) {
            return accountToJson(updatedAccount, 200);
        } else {
            return notFound();
        }
    }

    @Path("/dateclosed/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountDateClosedById(@PathParam("id") UUID accountId, String payload) throws SQLException {
        Account account = accountFromJson(payload);
        Account updatedAccount = ACCOUNT_SERVICE.updateAccountDateClosedById(accountId, account);
        if (updatedAccount != null) {
            return accountToJson(updatedAccount, 200);
        } else {
            return notFound();
        }
    }

    @Path("/balance/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountCurrentBalanceById(@PathParam("id") UUID accountId, String payload) throws SQLException {
        Account account = accountFromJson(payload);
        Account updatedAccount = ACCOUNT_SERVICE.updateAccountCurrentBalanceById(accountId, account);
        if (updatedAccount != null) {
            return accountToJson(updatedAccount, 200);
        } else {
            return notFound();
        }
    }

    @Path("/status/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountStatusById(@PathParam("id") UUID accountId, String payload) throws SQLException {
        Account account = accountFromJson(payload);
        Account updatedAccount = ACCOUNT_SERVICE.updateAccountStatusById(accountId, account);
        if (updatedAccount != null) {
            return accountToJson(updatedAccount, 200);
        } else {
            return notFound();
        }
    }

    @DELETE
    @Path("/delete/secured/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAccountById(@PathParam("id") UUID accountId) throws SQLException {
        boolean isDeleted = ACCOUNT_SERVICE.deleteAccountById(accountId);
        if (isDeleted) {
            return Response.ok("Account deleted successfully.").build();
        } else {
            return notFound();
        }
    }

    //Additional, not required
    @Path("accountnumber/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountNumberById(@PathParam("id") UUID accountId) throws SQLException {
        String accountNumber = ACCOUNT_SERVICE.getAccountNumberById(accountId);
        if (accountNumber != null) {
            return accountNumberToJson(accountNumber, 200);
        }
        return notFound();
    }
}