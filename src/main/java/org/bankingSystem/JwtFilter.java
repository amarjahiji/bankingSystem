package org.bankingSystem;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class JwtFilter implements ContainerRequestFilter {
    private static final String SECURED_KEYWORD = "secured";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        if (path.contains(SECURED_KEYWORD)) {
            String authorizationHeader = requestContext.getHeaderString("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            String token = authorizationHeader.substring("Bearer ".length());
            try {
                String adminEmail = JwtUtil.validateToken(token);
            } catch (Exception e) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }
    }
}