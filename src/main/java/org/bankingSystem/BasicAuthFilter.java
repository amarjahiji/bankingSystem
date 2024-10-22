package org.bankingSystem;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.jersey.http.HttpHeaders;

import java.util.Base64;
import java.util.StringTokenizer;

@Provider
public class BasicAuthFilter implements ContainerRequestFilter {
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    private static final String SECURED_URL_PREFIX = "secured";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
            String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
                String encodedCredentials = authHeader.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
                String decodedCredentials = new String(Base64.getDecoder().decode(encodedCredentials));
                StringTokenizer tokenizer = new StringTokenizer(decodedCredentials, ":");
                String username = tokenizer.nextToken();
                String password = tokenizer.nextToken();
                if ("admin".equals(username) && "password".equals(password)) {
                    return;
                }
            }
            Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("User cannot access the resource.").build();
            requestContext.abortWith(unauthorizedStatus);
        }
    }
}
