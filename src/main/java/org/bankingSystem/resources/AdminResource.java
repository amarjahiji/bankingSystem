package org.bankingSystem.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.Admin;
import org.bankingSystem.services.AdminService;

import java.sql.SQLException;


@Path("authentication")
public class AdminResource extends AbstractResource {
    private final AdminService LOGIN_SERVICE = new AdminService();

    @Path("/login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("email") String adminEmail, @QueryParam("password") String adminPassword) throws SQLException, IllegalArgumentException, NotFoundException {
        Admin admin = LOGIN_SERVICE.login(adminEmail, adminPassword);
        if (admin != null) {
            String token = LOGIN_SERVICE.generateJwtToken(admin);
            System.out.println("Token: " + token);
            return adminToJson(admin, token, 200);
        } else {
            return notFound();
        }
    }

    @Path("/signup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(String payload) throws SQLException, IllegalArgumentException {
        Admin admin = adminFromJson(payload);
        Admin createdAdmin = LOGIN_SERVICE.signup(admin);
        if (createdAdmin != null) {
            String token = LOGIN_SERVICE.generateJwtToken(createdAdmin);
            System.out.println("Token: " + token);
            return adminToJson(createdAdmin, token,200);
        } else {
            return notFound();
        }
    }
}