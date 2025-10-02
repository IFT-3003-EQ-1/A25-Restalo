package ca.ulaval.glo2003.api.response.exceptions;

import ca.ulaval.glo2003.entities.exceptions.AccessInterditException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AccessInterditExceptionMapper extends Exception implements ExceptionMapper<AccessInterditException> {

    @Override
    public Response toResponse(AccessInterditException exception) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
