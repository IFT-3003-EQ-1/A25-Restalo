package ca.ulaval.glo2003.api.response.exceptions;

import io.sentry.Sentry;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SentryExceptionMapper implements ExceptionMapper<Throwable> {
    
    @Override
    public Response toResponse(Throwable exception) {
        // NE PAS rapporter les exceptions générées par Jersey (404, etc.)
        if (exception instanceof WebApplicationException webException) {
            return webException.getResponse();
        }
        
        // Rapporter toutes les autres erreurs à Sentry
        Sentry.captureException(exception);
        
        // Retourner une réponse HTTP 500
        return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("Internal Server Error")
            .build();
    }
}