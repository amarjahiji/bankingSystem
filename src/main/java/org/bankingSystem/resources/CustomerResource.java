package org.bankingSystem.resources;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.CountCustomer;
import org.bankingSystem.model.Customer;
import org.bankingSystem.services.CustomerService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Path("customer")
public class CustomerResource {
    private final CustomerService CUSTOMER_SERVICE = new CustomerService();
    private final Gson GSON = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers() throws SQLException {
        List<Customer> customers = CUSTOMER_SERVICE
                .getCustomers();
        Integer totalNumber = CUSTOMER_SERVICE
                .getTotalNumberOfCustomers();
        CountCustomer countCustomers = new CountCustomer(totalNumber, customers);
        String json = GSON.toJson(countCustomers);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/old")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOldCustomers() throws SQLException {
        List<Customer> customers = CUSTOMER_SERVICE
                .getOldCustomers();
        Integer totalNumber = CUSTOMER_SERVICE
                .getTotalNumberOfOldCustomers();
        CountCustomer countCustomers = new CountCustomer(totalNumber, customers);
        String json = GSON.toJson(countCustomers);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/young")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getYoungCustomers() throws SQLException {
        List<Customer> customers = CUSTOMER_SERVICE
                .getYoungCustomers();
        Integer totalNumber = CUSTOMER_SERVICE
                .getTotalNumberOfYoungCustomers();
        CountCustomer countCustomers = new CountCustomer(totalNumber, customers);
        String json = GSON.toJson(countCustomers);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") UUID customerId) throws SQLException {
        Customer customer = CUSTOMER_SERVICE
                .getCustomerById(customerId);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found").build();
        }
        String json = GSON.toJson(customer);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/accounts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersAccounts() throws SQLException {
        List<Customer> customers = CUSTOMER_SERVICE
                .getCustomersAccounts();
        String json = GSON.toJson(customers);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    //TODO : check uuid on db if they are ok

    @Path("/accounts/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerAccountsById(@PathParam("id") UUID customerId) throws SQLException {
        List<Customer> customers = CUSTOMER_SERVICE
                .getCustomerAccountsById(customerId);
        String json = GSON.toJson(customers);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/add/secured")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(String payload) throws SQLException {
        Customer customer = GSON.fromJson(payload, Customer.class);
        Customer createdCustomer = CUSTOMER_SERVICE
                .createCustomer(customer);
        String json = GSON.toJson(createdCustomer);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerById(@PathParam("id") UUID customerId, String payload) throws SQLException {
        Customer customer = GSON.fromJson(payload, Customer.class);
        Customer updatedCustomer = CUSTOMER_SERVICE
                .updateCustomerById(customerId, customer);
        String json = GSON.toJson(updatedCustomer);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/address/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerAddressById(@PathParam("id") UUID customerId, String payload) throws SQLException {
        Customer customer = GSON.fromJson(payload, Customer.class);
        Customer updatedCustomer = CUSTOMER_SERVICE
                .updateCustomerAddressById(customerId, customer);
        String json = GSON.toJson(updatedCustomer);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/email/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerEmailById(@PathParam("id") UUID customerId, String payload) throws SQLException {
        Customer customer = GSON.fromJson(payload, Customer.class);
        Customer updatedCustomer = CUSTOMER_SERVICE
                .updateCustomerEmailById(customerId, customer);
        String json = GSON.toJson(updatedCustomer);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/phonenumber/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerPhoneNumberById(@PathParam("id") UUID customerId, String payload) throws SQLException {
        Customer customer = GSON.fromJson(payload, Customer.class);
        Customer updatedCustomer = CUSTOMER_SERVICE
                .updateCustomerPhoneNumberById(customerId, customer);
        String json = GSON.toJson(updatedCustomer);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/delete/secured/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomerById(@PathParam("id") UUID customerId) throws SQLException {
        boolean isDeleted = CUSTOMER_SERVICE
                .deleteCustomerById(customerId);
        if (isDeleted) {
            return Response.ok("Customer deleted successfully.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found").build();
        }

    }

    //Additional that were not required
    @Path("/accounts/transactions/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerAccountsTransactionsById(@PathParam("id") UUID customerId) throws SQLException {
        List<Customer> customer = CUSTOMER_SERVICE
                .getCustomerAccountsTransactionsById(customerId);
        String json = GSON.toJson(customer);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/accounts/transactions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersAccountsTransactions() throws SQLException {
        List<Customer> customers = CUSTOMER_SERVICE
                .getCustomersAccountsTransactions();
        String json = GSON.toJson(customers);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/accounts/cards/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerAccountsCardsById(@PathParam("id") UUID customerId) throws SQLException {
        List<Customer> customer = CUSTOMER_SERVICE
                .getCustomerAccountsCardsById(customerId);
        String json = GSON.toJson(customer);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/accounts/cards")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersAccountsCards() throws SQLException {
        List<Customer> customers = CUSTOMER_SERVICE
                .getCustomersAccountsCards();
        String json = GSON.toJson(customers);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/firstname/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerFirstNameById(@PathParam("id") UUID customerId) throws SQLException {
        String customerFirstName = CUSTOMER_SERVICE
                .getCustomerFirstNameById(customerId);
        if (customerFirstName == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }
        String json = GSON.toJson(customerFirstName);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}