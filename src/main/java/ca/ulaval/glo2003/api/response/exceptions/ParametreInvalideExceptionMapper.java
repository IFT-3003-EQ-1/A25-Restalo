package ca.ulaval.glo2003.api.response.exceptions;

import ca.ulaval.glo2003.entities.exceptions.ParametreInvalideException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class ParametreInvalideExceptionMapper extends Exception implements ExceptionMapper<ParametreInvalideException> {

    @Override
    public Response toResponse(ParametreInvalideException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", exception.code, "description", exception.getMessage()))
                .build();
    }
}
