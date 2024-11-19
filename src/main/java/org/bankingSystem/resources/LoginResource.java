package org.bankingSystem.resources;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.JwtFilter;
import org.bankingSystem.model.Admin;
import org.bankingSystem.services.LoginService;

import java.sql.SQLException;
import java.util.Date;


@Path("authentication")
public class LoginResource extends AbstractResource {
    private final LoginService LOGIN_SERVICE = new LoginService();

    @Path("/login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("email") String adminEmail, @QueryParam("password") String adminPassword) throws SQLException {
        Admin adminCredentials = LOGIN_SERVICE.login(adminEmail, adminPassword);
        if (adminCredentials != null) {
            String token = generateJwtToken(adminCredentials);
            System.out.println("Token: " + token);
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
            String token = generateJwtToken(createdAdmin);
            System.out.println("Token: " + token);
            return adminToJson(createdAdmin, 200);
        } else {
            return notFound();
        }
    }


    private String generateJwtToken(Admin adminCredentials) {
        return Jwts.builder()
                .setSubject(adminCredentials.getAdminEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, JwtFilter.SECRET_KEY)
                .compact();
    }
}

