package ca.ulaval.glo2003.api.response.exceptions;

import ca.ulaval.glo2003.entities.exceptions.ParametreManquantException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.Map;

public class ParametreManquantExceptionMapper extends Exception implements ExceptionMapper<ParametreManquantException> {

    @Override
    public Response toResponse(ParametreManquantException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", exception.code, "description", exception.getMessage()))
                .build();
    }
}
