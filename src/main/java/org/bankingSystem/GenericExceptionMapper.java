package org.bankingSystem;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.sql.SQLException;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override

    public Response toResponse(Throwable exception) {
        if (exception instanceof SQLException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        if (exception instanceof JWTVerificationException) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if (exception instanceof JWTDecodeException) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (exception instanceof NotAuthorizedException) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if (exception instanceof ForbiddenException) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if (exception instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (exception instanceof Exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
