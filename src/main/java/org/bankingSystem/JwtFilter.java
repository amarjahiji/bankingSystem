package org.bankingSystem;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;


@Provider
public class JwtFilter implements ContainerRequestFilter {
    public static final String ENV_SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    public static final Key SECRET_KEY = Keys.hmacShaKeyFor(ENV_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    public static final String SECURE_KEYWORD = "secured";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (requestContext.getUriInfo().getPath().contains(SECURE_KEYWORD)) {
            String authorizationHeader = requestContext.getHeaderString("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring("Bearer".length()).trim();

                try {
                    Jwts.parser()
                            .setSigningKey(SECRET_KEY)
                            .parseClaimsJws(token);

                } catch (ExpiredJwtException e) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Token has expired").build());
                } catch (MalformedJwtException e) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Invalid token").build());
                }
            } else {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Authorization token missing").build());
            }
        }
    }
}
