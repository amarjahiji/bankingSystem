package org.bankingSystem.resources;

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
public class CustomerResource extends AbstractResource {
    private final CustomerService CUSTOMER_SERVICE = new CustomerService();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        try {
            List<Customer> customers = CUSTOMER_SERVICE.getAllCustomers();
            Integer totalNumber = customers.size();
            if (!customers.isEmpty()) {
                CountCustomer countCustomers = new CountCustomer(totalNumber, customers);
                return countCustomerToJson(countCustomers, 200);
            } else {
                return notFound();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong")
                    .build();
        }
    }

    @Path("/old")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOldCustomers() {
        try {
            List<Customer> customers = CUSTOMER_SERVICE.getOldCustomers();
            Integer totalNumber = customers.size();
            if (!customers.isEmpty()) {
                CountCustomer countCustomers = new CountCustomer(totalNumber, customers);
                return countCustomerToJson(countCustomers, 200);
            } else {
                return notFound();
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
            Integer totalNumber = customers.size();
            if (!customers.isEmpty()) {
                CountCustomer countCustomers = new CountCustomer(totalNumber, customers);
                return countCustomerToJson(countCustomers, 200);
            } else {
                return notFound();
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
            Integer totalNumber = customers.size();
            if (!customers.isEmpty()) {
                CountCustomer countCustomers = new CountCustomer(totalNumber, customers);
                return countCustomerToJson(countCustomers, 200);
            } else {
                return notFound();
            }
        } catch (SQLException e) {
            //TODO google about Jersey REST Exception Mapper
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
                return customerToJson(customer,200);
            } else {
                return notFound();
            }
        } catch (SQLException e) {
            //TODO remove all exceptions like this, since these wil be handled from exception mapper
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

                return customersToJson(customers, 200);
            } else {
                return notFound();
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
                return customersToJson(customers, 200);
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
    public Response createCustomer(String payload) {
        try {
            Customer customer = customerFromJson(payload);
            Customer createdCustomer = CUSTOMER_SERVICE.createCustomer(customer);
            if (createdCustomer != null) {
                return customerToJson(createdCustomer, 200);
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
    public Response updateCustomerById(@PathParam("id") UUID customerId, String payload) {
        try {
            Customer customer = customerFromJson(payload);
            Customer updatedCustomer = CUSTOMER_SERVICE.updateCustomerById(customerId, customer);
            if (updatedCustomer != null) {
               return customerToJson(updatedCustomer, 200);
            } else {
            return notFound();
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
            Customer customer = customerFromJson(payload);
            Customer updatedCustomer = CUSTOMER_SERVICE.updateCustomerAddressById(customerId, customer);
            if (updatedCustomer != null) {
                return customerToJson(updatedCustomer, 200);
            } else {
                return notFound();
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
            Customer customer = customerFromJson(payload);
            Customer updatedCustomer = CUSTOMER_SERVICE.updateCustomerEmailById(customerId, customer);
            if (updatedCustomer != null) {
                return customerToJson(updatedCustomer, 200);
            } else {
            return notFound();
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
            Customer customer = customerFromJson(payload);
            Customer updatedCustomer = CUSTOMER_SERVICE
                    .updateCustomerPhoneNumberById(customerId, customer);
            if (updatedCustomer != null) {
                return customerToJson(updatedCustomer, 200);
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
    public Response deleteCustomerById(@PathParam("id") UUID customerId) {
        try {
            boolean isDeleted = CUSTOMER_SERVICE.deleteCustomerById(customerId);
            if (isDeleted) {
                return Response.ok("Customer deleted successfully.").build();
            } else {
                return notFound();
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
            List<Customer> customers = CUSTOMER_SERVICE.getCustomerAccountsTransactionsById(customerId);
            if (!customers.isEmpty()) {
                return customersToJson(customers, 200);
            } else {
                return notFound();
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
                return customersToJson(customers, 200);
            } else {
                return notFound();
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
                return customersToJson(customer, 200);
            } else {
                return notFound();
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
                return customersToJson(customers, 200);
            } else {
                return notFound();
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
                return customerFirstNameToJson(customerFirstName, 200);
            } else {
                return notFound();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}