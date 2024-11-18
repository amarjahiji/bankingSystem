package org.bankingSystem.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.Account;
import org.bankingSystem.services.AccountService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Path("account")
public class AccountResource extends AbstractResource {
    private final AccountService ACCOUNT_SERVICE = new AccountService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccounts() {
        try {
            List<Account> accounts = ACCOUNT_SERVICE.getAccounts();
            if (!accounts.isEmpty()) {
                return accountToJson(accounts, 200);
            } else {
                return notFound();
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
    public Response getAccountById(@PathParam("id") UUID accountId) {
        try {
            Account account = ACCOUNT_SERVICE.getAccountById(accountId);
            if (account != null) {
                return accountToJson(account, 200);
            } else {
                return notFound();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/add/secured")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(String payload) {
        try {
            Account account = accountFromJson(payload);
            Account createdAccount = ACCOUNT_SERVICE.createAccount(account);
            if (createdAccount != null) {
                return accountToJson(createdAccount, 200);
            } else {
                return notFound();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountById(@PathParam("id") UUID accountId, String payload) {
        try {
            Account account = accountFromJson(payload);
            Account updatedAccount = ACCOUNT_SERVICE.updateAccountById(accountId, account);
            if (updatedAccount != null) {
                return accountToJson(updatedAccount, 200);
            } else {
                return notFound();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/dateclosed/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountDateClosedById(@PathParam("id") UUID accountId, String payload) {
        try {
            Account account = accountFromJson(payload);
            Account updatedAccount = ACCOUNT_SERVICE.updateAccountDateClosedById(accountId, account);
            if (updatedAccount != null) {
                return accountToJson(updatedAccount, 200);
            } else {
                return notFound();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/balance/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountCurrentBalanceById(@PathParam("id") UUID accountId, String payload) {
        try {
            Account account = accountFromJson(payload);
            Account updatedAccount = ACCOUNT_SERVICE.updateAccountCurrentBalanceById(accountId, account);
            if (updatedAccount != null) {
                return accountToJson(updatedAccount, 200);
            } else {
                return notFound();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/status/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountStatusById(@PathParam("id") UUID accountId, String payload) {
        try {
            Account account = accountFromJson(payload);
            Account updatedAccount = ACCOUNT_SERVICE.updateAccountStatusById(accountId, account);
            if (updatedAccount != null) {
                return accountToJson(updatedAccount, 200);
            } else {
                return notFound();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/delete/secured/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAccountById(@PathParam("id") UUID accountId) {
        try {
            boolean isDeleted = ACCOUNT_SERVICE.deleteAccountById(accountId);
            if (isDeleted) {
                return Response.ok("Account deleted successfully.").build();
            } else {
                return notFound();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    //Additional, not required
    @Path("accountnumber/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountNumberById(@PathParam("id") UUID accountId) {
        try {
            String accountNumber = ACCOUNT_SERVICE.getAccountNumberById(accountId);
            if (accountNumber != null) {
                return accountNumberToJson(accountNumber, 200);
            }
            return notFound();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}