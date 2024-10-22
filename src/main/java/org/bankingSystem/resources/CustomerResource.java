package org.bankingSystem.resources;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Customer;
import org.bankingSystem.services.CustomerService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("customer")
public class CustomerResource {
    private final CustomerService customerService = new CustomerService();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            List<Customer> customerList = customerService.getCustomers(connection);
            String json = gson.toJson(customerList);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching customers").build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") int customerId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            Customer customer = CustomerService.getCustomerById(connection, customerId);
            if (customer == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
            }
            String json = gson.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching customer").build();
        }
    }

    @Path("/accounts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersAccounts() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            List<Customer> customers = customerService.getCustomersAccounts(connection);
            String json = gson.toJson(customers);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching customers").build();
        }
    }

    @Path("/accounts/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerAccountsById(@PathParam("id") int customerId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            List<Customer> customer = customerService.getCustomerAccountsById(connection, customerId);
            String json = gson.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching customer").build();
        }
    }

    @Path("/create/secured")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(String payload) {
        Customer customer = gson.fromJson(payload, Customer.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Customer customerModel = CustomerService.createCustomer(connection, customer);
            String json = gson.toJson(customerModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating customer").build();
        }
    }

    @Path("/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerById(@PathParam("id") int customerId, String payload) {
        Customer customer = gson.fromJson(payload, Customer.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Customer customerModel = customerService.updateCustomerById(connection, customerId, customer);
            String json = gson.toJson(customerModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @Path("/address/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerAddressById(@PathParam("id") int customerId, String payload) {
        Customer customer = gson.fromJson(payload, Customer.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Customer customerModel = customerService.updateCustomerAddressById(connection, customerId, customer);
            String json = gson.toJson(customerModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @Path("/email/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerEmailById(@PathParam("id") int customerId, String payload) {
        Customer customer = gson.fromJson(payload, Customer.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Customer customerModel = customerService.updateCustomerEmailById(connection, customerId, customer);
            String json = gson.toJson(customerModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @Path("/phonenumber/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerPhoneNumberById(@PathParam("id") int customerId, String payload) {
        Customer customer = gson.fromJson(payload, Customer.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            Customer customerModel = customerService.updateCustomerPhoneNumberById(connection, customerId, customer);
            String json = gson.toJson(customerModel);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/delete/secured/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomerById(@PathParam("id") int customerId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            boolean isDeleted = CustomerService.deleteCustomerById(connection, customerId);
            if (isDeleted) {
                return Response.ok("Customer deleted successfully.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting customer: " + e.getMessage()).build();
        }
    }

    //Additional that were not required
    @Path("/accounts/transactions/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerAccountsTransactionsById(@PathParam("id") int customerId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            List<Customer> customer = customerService.getCustomerAccountsTransactionsById(connection, customerId);
            String json = gson.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching customer").build();
        }
    }

    @Path("/accounts/transactions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersAccountsTransactions() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            List<Customer> customer = customerService.getCustomersAccountsTransactions(connection);
            String json = gson.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching customer").build();
        }
    }

    @Path("/accounts/cards/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerAccountsCardsById(@PathParam("id") int customerId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            List<Customer> customer = customerService.getCustomerAccountsCardsById(connection, customerId);
            String json = gson.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching customer").build();
        }
    }

    @Path("/accounts/cards")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersAccountsCards() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            List<Customer> customer = customerService.getCustomersAccountsCards(connection);
            String json = gson.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching customer").build();
        }
    }

    @Path("/firstname/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerFirstNameById(@PathParam("id") int customerId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String customer = CustomerService.getCustomerFirstNameById(connection, customerId);
            if (customer == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
            }
            String json = gson.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}