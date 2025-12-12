package ca.ulaval.glo2003.api.requests;

import ca.ulaval.glo2003.domain.SecurityService;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;

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

        RestaurantDto dto = securityService.getRestaurant(idRestaurant, ownerId);

        requestContext.setProperty("restaurant", dto);

    }

}

