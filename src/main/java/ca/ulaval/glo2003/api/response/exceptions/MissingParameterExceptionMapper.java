package ca.ulaval.glo2003.api.response.exceptions;

import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class MissingParameterExceptionMapper extends Exception implements ExceptionMapper<MissingParameterException> {

    @Override
    public Response toResponse(MissingParameterException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", exception.code, "description", exception.getMessage()))
                .build();
    }
}
