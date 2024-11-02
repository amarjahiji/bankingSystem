package org.bankingSystem.resources;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.Customer;
import org.bankingSystem.services.CustomerService;

import java.sql.SQLException;
import java.util.List;

@Path("customer")
public class CustomerResource {
    private final CustomerService CUSTOMER_SERVICE = new CustomerService();
    private final Gson GSON = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers() throws SQLException {
            List<Customer> customerList = CUSTOMER_SERVICE.getCustomers();
            String json = GSON.toJson(customerList);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") int customerId) throws SQLException {
            Customer customer = CUSTOMER_SERVICE.getCustomerById(customerId);
            if (customer == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
            }
            String json = GSON.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/accounts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersAccounts() throws SQLException {
            List<Customer> customers = CUSTOMER_SERVICE.getCustomersAccounts();
            String json = GSON.toJson(customers);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/accounts/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerAccountsById(@PathParam("id") int customerId) throws SQLException {
            List<Customer> customer = CUSTOMER_SERVICE.getCustomerAccountsById(customerId);
            String json = GSON.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/add/secured")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(String payload) throws SQLException {
        Customer customer = GSON.fromJson(payload, Customer.class);
            Customer customerModel = CUSTOMER_SERVICE.createCustomer(customer);
            String json = GSON.toJson(customerModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerById(@PathParam("id") int customerId, String payload) throws SQLException {
        Customer customer = GSON.fromJson(payload, Customer.class);
            Customer customerModel = CUSTOMER_SERVICE.updateCustomerById(customerId, customer);
            String json = GSON.toJson(customerModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/address/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerAddressById(@PathParam("id") int customerId, String payload) throws SQLException {
        Customer customer = GSON.fromJson(payload, Customer.class);
            Customer customerModel = CUSTOMER_SERVICE.updateCustomerAddressById(customerId, customer);
            String json = GSON.toJson(customerModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/email/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerEmailById(@PathParam("id") int customerId, String payload) throws SQLException {
        Customer customer = GSON.fromJson(payload, Customer.class);
            Customer customerModel = CUSTOMER_SERVICE.updateCustomerEmailById(customerId, customer);
            String json = GSON.toJson(customerModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/phonenumber/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerPhoneNumberById(@PathParam("id") int customerId, String payload) throws SQLException {
        Customer customer = GSON.fromJson(payload, Customer.class);
            Customer customerModel = CUSTOMER_SERVICE.updateCustomerPhoneNumberById(customerId, customer);
            String json = GSON.toJson(customerModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/delete/secured/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomerById(@PathParam("id") int customerId) throws SQLException {
            boolean isDeleted = CUSTOMER_SERVICE.deleteCustomerById(customerId);
            if (isDeleted) {
                return Response.ok("Customer deleted successfully.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
            }

    }

    //Additional that were not required
    @Path("/accounts/transactions/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerAccountsTransactionsById(@PathParam("id") int customerId) throws SQLException {
            List<Customer> customer = CUSTOMER_SERVICE.getCustomerAccountsTransactionsById(customerId);
            String json = GSON.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/accounts/transactions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersAccountsTransactions() throws SQLException {
            List<Customer> customer = CUSTOMER_SERVICE.getCustomersAccountsTransactions();
            String json = GSON.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/accounts/cards/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerAccountsCardsById(@PathParam("id") int customerId) throws SQLException {
            List<Customer> customer = CUSTOMER_SERVICE.getCustomerAccountsCardsById(customerId);
            String json = GSON.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/accounts/cards")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersAccountsCards() throws SQLException {
            List<Customer> customer = CUSTOMER_SERVICE.getCustomersAccountsCards();
            String json = GSON.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @Path("/firstname/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerFirstNameById(@PathParam("id") int customerId) throws SQLException {
            String customer = CUSTOMER_SERVICE.getCustomerFirstNameById(customerId);
            if (customer == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
            }
            String json = GSON.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}