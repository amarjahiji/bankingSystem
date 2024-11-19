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
                try {
                    if (validateUserCredentials(username, password)) {
                        return;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            Response unauthorizedStatus = Response.status
                            (Response.Status.UNAUTHORIZED)
                    .entity("User cannot access the resource.")
                    .build();
            requestContext.abortWith(unauthorizedStatus);
        }
    }

    private boolean validateUserCredentials(String email, String password) throws SQLException {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            try{
            ps = connection.prepareStatement(AdminSqlQueries.GET_ADMINS);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, "Full");
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Admin does not exist.", e);
        }
    }
}
