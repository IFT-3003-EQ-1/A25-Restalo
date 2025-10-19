package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchRessource {

    private final RestaurantService restaurantService;

    private final RestaurantDtoAssembler restaurantDtoAssembler;

    public SearchRessource(RestaurantService restaurantService, RestaurantDtoAssembler restaurantDtoAssembler) {
        this.restaurantService = restaurantService;
        this.restaurantDtoAssembler = restaurantDtoAssembler;
    }


    @POST
    @Path("/restaurants")
    public Response searchRestaurants(RestaurantDto searchParameters) {
        if (searchParameters == null) {
            searchParameters = new  RestaurantDto();
        }

        List<RestaurantDto> restaurantDtos = restaurantService.searchRestaurants(searchParameters);
        return Response.ok(restaurantDtos.stream().map(restaurantDtoAssembler::versJson).toList()).build();
    }
}
