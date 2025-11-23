package ca.ulaval.glo2003.api.requests;

import ca.ulaval.glo2003.api.response.exceptions.ForbiddenAccessExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.MissingParameterExceptionMapper;
import ca.ulaval.glo2003.domain.SecurityService;
import ca.ulaval.glo2003.entities.exceptions.ForbiddenAccessException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import com.google.common.base.Strings;
import jakarta.ws.rs.NameBinding;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.List;

@OwnerOnly
public class AutorizationRequestFilter implements ContainerRequestFilter {

    private final SecurityService securityService;

    public AutorizationRequestFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        List<String> ownerIdHeaderParam = requestContext.getHeaders().get("Owner");
        List<String> idRestaurantPathParam = requestContext.getUriInfo().getPathParameters().get("id");

        if (ownerIdHeaderParam == null || ownerIdHeaderParam.isEmpty())
            throw new MissingParameterException("Missing OwnerId");

        if (idRestaurantPathParam == null || idRestaurantPathParam.isEmpty())
            throw new MissingParameterException("Missing RestaurantId");


        String ownerId = ownerIdHeaderParam.getFirst();
        String idRestaurant = idRestaurantPathParam.getFirst();
        securityService.hasAccess(idRestaurant, ownerId);


    }

    private void abort(ContainerRequestContext requestContext, String msg) {
        requestContext.abortWith(new MissingParameterExceptionMapper().toResponse(new MissingParameterException(msg)));
    }
}

