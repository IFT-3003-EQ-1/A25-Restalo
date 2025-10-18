package ca.ulaval.glo2003.api.response.exceptions;

import ca.ulaval.glo2003.entities.exceptions.ForbiddenAccessException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ForbiddenAccessExceptionMapper extends Exception implements ExceptionMapper<ForbiddenAccessException> {

    @Override
    public Response toResponse(ForbiddenAccessException exception) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
