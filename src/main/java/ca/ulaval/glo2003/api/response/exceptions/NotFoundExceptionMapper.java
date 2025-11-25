package ca.ulaval.glo2003.api.response.exceptions;


import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class NotFoundExceptionMapper extends RuntimeException implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("error", Response.Status.NOT_FOUND, "description", exception.getMessage()))
                .build();
    }
}
