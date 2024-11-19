package org.bankingSystem.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.Admin;
import org.bankingSystem.services.LoginService;

import java.sql.SQLException;

@Path("authentication")
public class LoginResource extends AbstractResource {
    private final LoginService LOGIN_SERVICE = new LoginService();

    @Path("/login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("email") String adminEmail, @QueryParam("password") String adminPassword) throws SQLException {
        Admin adminCredentials = LOGIN_SERVICE.login(adminEmail, adminPassword);
        if (adminCredentials != null) {
            return adminToJson(adminCredentials, 200);
        } else {
            return notFound();
        }
    }

    @Path("/signup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(String payload) throws SQLException {
        Admin admin = adminFromJson(payload);
        Admin createdAdmin = LOGIN_SERVICE.signup(admin);
        if (createdAdmin != null) {
            return adminToJson(createdAdmin, 200);
        } else {
            return notFound();
        }
    }
}

