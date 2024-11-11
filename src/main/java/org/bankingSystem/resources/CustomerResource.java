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
    public Response getAllCustomers() {
        try {
            List<Customer> customers = CUSTOMER_SERVICE.getAllCustomers();
            Integer totalNumber = CUSTOMER_SERVICE.getTotalNumberOfAllCustomers();
            if (!customers.isEmpty()) {
                CountCustomer countCustomers = new CountCustomer(totalNumber, customers);
                String json = GSON.toJson(countCustomers);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No customers found")
                        .build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/old")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOldCustomers() {
        try {
            List<Customer> customers = CUSTOMER_SERVICE.getOldCustomers();
            Integer totalNumber = CUSTOMER_SERVICE.getTotalNumberOfOldCustomers();
            if (!customers.isEmpty()) {
                CountCustomer countCustomers = new CountCustomer(totalNumber, customers);
                String json = GSON.toJson(countCustomers);
                return Response.ok(json, MediaType.APPLICATION_JSON)
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No old customer found")
                        .build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/young")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getYoungCustomers() {
        try {
            List<Customer> customers = CUSTOMER_SERVICE.getYoungCustomers();
            Integer totalNumber = CUSTOMER_SERVICE.getTotalNumberOfYoungCustomers();
            if (!customers.isEmpty()) {
                CountCustomer countCustomers = new CountCustomer(totalNumber, customers);
                String json = GSON.toJson(countCustomers);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No old customer found")
                        .build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/age/{first}/{second}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersOfCertainAge(@PathParam("first") String lowerBound, @PathParam("second") String upperBound) {
        try {
            List<Customer> customers = CUSTOMER_SERVICE.getCustomersOfCertainAge(lowerBound, upperBound);
            Integer totalNumber = CUSTOMER_SERVICE.getTotalNumberOfCertainAgeCustomers(lowerBound , upperBound);
            if (!customers.isEmpty()) {
                CountCustomer countCustomers = new CountCustomer(totalNumber, customers);
                String json = GSON.toJson(countCustomers);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No customer of such age limits found")
                        .build();
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
    public Response getCustomerById(@PathParam("id") UUID customerId) {
        try {
            Customer customer = CUSTOMER_SERVICE.getCustomerById(customerId);
            if (customer != null) {
                String json = GSON.toJson(customer);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No customer found with id " + customerId)
                        .build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();

        }
    }

    @Path("/accounts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersAccounts() {
        try {
            List<Customer> customers = CUSTOMER_SERVICE.getCustomersAccounts();
            if (!customers.isEmpty()) {
                String json = GSON.toJson(customers);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/accounts/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerAccountsById(@PathParam("id") UUID customerId) {
        try {
            List<Customer> customers = CUSTOMER_SERVICE.getCustomerAccountsById(customerId);
            if (!customers.isEmpty()) {
                String json = GSON.toJson(customers);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
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
    public Response createCustomer(String payload) {
        try {
            Customer customer = GSON.fromJson(payload, Customer.class);
            Customer createdCustomer = CUSTOMER_SERVICE.createCustomer(customer);
            if (createdCustomer != null) {
                String json = GSON.toJson(createdCustomer);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NO_CONTENT).entity("Customer not created").build();
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
    public Response updateCustomerById(@PathParam("id") UUID customerId, String payload) {
        try {
            Customer customer = GSON.fromJson(payload, Customer.class);
            Customer updatedCustomer = CUSTOMER_SERVICE.updateCustomerById(customerId, customer);
            if (updatedCustomer != null) {
                String json = GSON.toJson(updatedCustomer);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NO_CONTENT).entity("Customer with id: " + customerId + "did not get updated").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/address/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerAddressById(@PathParam("id") UUID customerId, String payload) {
        try {
            Customer customer = GSON.fromJson(payload, Customer.class);
            Customer updatedCustomer = CUSTOMER_SERVICE.updateCustomerAddressById(customerId, customer);
            if (updatedCustomer != null) {
                String json = GSON.toJson(updatedCustomer);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/email/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerEmailById(@PathParam("id") UUID customerId, String payload) {
        try {
            Customer customer = GSON.fromJson(payload, Customer.class);
            Customer updatedCustomer = CUSTOMER_SERVICE.updateCustomerEmailById(customerId, customer);
            if (updatedCustomer != null) {
                String json = GSON.toJson(updatedCustomer);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/phonenumber/update/secured/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerPhoneNumberById(@PathParam("id") UUID customerId, String payload) {
        try {
            Customer customer = GSON.fromJson(payload, Customer.class);
            Customer updatedCustomer = CUSTOMER_SERVICE
                    .updateCustomerPhoneNumberById(customerId, customer);
            if (updatedCustomer != null) {
                String json = GSON.toJson(updatedCustomer);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
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
    public Response deleteCustomerById(@PathParam("id") UUID customerId) {
        try {
            boolean isDeleted = CUSTOMER_SERVICE.deleteCustomerById(customerId);
            if (isDeleted) {
                return Response.ok("Customer deleted successfully.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Customer not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    //Additional that were not required
    @Path("/accounts/transactions/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerAccountsTransactionsById(@PathParam("id") UUID customerId) {
        try {
            List<Customer> customer = CUSTOMER_SERVICE.getCustomerAccountsTransactionsById(customerId);
            if (!customer.isEmpty()) {
                String json = GSON.toJson(customer);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/accounts/transactions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersAccountsTransactions() {
        try {
            List<Customer> customers = CUSTOMER_SERVICE.getCustomersAccountsTransactions();
            if (!customers.isEmpty()) {
                String json = GSON.toJson(customers);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/accounts/cards/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerAccountsCardsById(@PathParam("id") UUID customerId) {
        try {
            List<Customer> customer = CUSTOMER_SERVICE.getCustomerAccountsCardsById(customerId);
            if (!customer.isEmpty()) {
                String json = GSON.toJson(customer);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/accounts/cards")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersAccountsCards() {
        try {
            List<Customer> customers = CUSTOMER_SERVICE.getCustomersAccountsCards();
            if (!customers.isEmpty()) {
                String json = GSON.toJson(customers);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Path("/firstname/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerFirstNameById(@PathParam("id") UUID customerId) {
        try {
            String customerFirstName = CUSTOMER_SERVICE.getCustomerFirstNameById(customerId);
            if (customerFirstName != null) {
                String json = GSON.toJson(customerFirstName);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}