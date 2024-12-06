package org.bankingSystem.resources;

import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bankingSystem.services.CommonService.GSON;

public abstract class AbstractResource {
    protected Response countCustomerToJson(CountCustomer countCustomers, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(countCustomers)).build();
    }

    protected Response customerToJson(Customer customer, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(customer)).build();
    }

    protected Response customersToJson(List<Customer> customers, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(customers)).build();
    }

    protected Response customerFirstNameToJson(String customerFirstName, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(customerFirstName)).build();
    }

    protected Customer customerFromJson(String payload) {
        return GSON.fromJson(payload, Customer.class);
    }

    protected Response accountsToJson(List<Account> accounts, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(accounts)).build();
    }

    protected Response accountToJson(Account account, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(account)).build();
    }

    protected Response accountNumberToJson(String accountNumber, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(accountNumber)).build();
    }

    protected Account accountFromJson(String payload) {
        return GSON.fromJson(payload, Account.class);
    }

    protected Response cardsToJson(List<Card> cards, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(cards)).build();
    }

    protected Response cardToJson(Card card, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(card)).build();
    }

    protected Card cardFromJson(String payload) {
        return GSON.fromJson(payload, Card.class);
    }

    protected Response cardTypesToJson(List<CardType> cardType, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(cardType)).build();
    }

    protected Response notFound() {
        return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
    }

    protected Response transactionsToJson(List<Transaction> transactions, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(transactions)).build();
    }

    protected Response transactionToJson(Transaction transaction, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(transaction)).build();
    }

    protected Transaction transactionFromJson(String payload) {
        return GSON.fromJson(payload, Transaction.class);
    }

    protected Response adminToJson(Admin admin, String token, int statusCode) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("admin: ", admin);
        responseMap.put("token: ", token);
        return Response.status(statusCode).entity(GSON.toJson(responseMap)).build();
    }

    protected Admin adminFromJson(String payload) {
        return GSON.fromJson(payload, Admin.class);
    }

    protected Response transfersToJson(List<Transfer> transfers, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(transfers)).build();
    }

    protected Response transferToJson(Transfer transfer, int statusCode) {
        return Response.status(statusCode).entity(GSON.toJson(transfer)).build();
    }

    protected Transfer transferFromJson(String payload) {
        return GSON.fromJson(payload, Transfer.class);
    }
}
