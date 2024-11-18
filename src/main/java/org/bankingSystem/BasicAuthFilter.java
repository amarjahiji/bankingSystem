package org.bankingSystem;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.bankingSystem.queries.AdminSqlQueries;
import org.glassfish.jersey.http.HttpHeaders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.StringTokenizer;

@Provider
public class BasicAuthFilter implements ContainerRequestFilter {
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    private static final String SECURE_KEYWORD = "secured";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (requestContext.getUriInfo().getPath().contains(SECURE_KEYWORD)) {
            String authHeader = requestContext.getHeaderString
                    (HttpHeaders.AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
                String encodedCredentials = authHeader.
                        replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
                String decodedCredentials = new String(Base64.getDecoder().decode(encodedCredentials));
                StringTokenizer tokenizer = new StringTokenizer(decodedCredentials, ":");
                String username = tokenizer.nextToken();
                String password = tokenizer.nextToken();
                //TODO make this token based (JWT), passwords shouldn't be transported
                if (validateUserCredentials(username, password)) {
                    return;
                }
            }
            Response unauthorizedStatus = Response.status
                            (Response.Status.UNAUTHORIZED)
                    .entity("User cannot access the resource.")
                    .build();
            requestContext.abortWith(unauthorizedStatus);
        }
    }

    private boolean validateUserCredentials(String username, String password) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement ps = connection.prepareStatement
                    (AdminSqlQueries.GET_ADMINS);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Admin does not exist.", e);
        }
    }
}
