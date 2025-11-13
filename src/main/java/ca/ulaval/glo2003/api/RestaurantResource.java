package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.ReservationService;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/restaurants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestaurantResource {
    private  final RestaurantService restaurantService;
    private final RestaurantDtoAssembler restaurantDtoAssembler;
    private final ReservationService reservationService;

    public RestaurantResource(RestaurantService restaurantService,
                              RestaurantDtoAssembler restaurantDtoAssembler,
                              ReservationService reservationService
                               ) {
        this.restaurantService = restaurantService;
        this.restaurantDtoAssembler = restaurantDtoAssembler;
        this.reservationService = reservationService;
    }

    @POST
    public Response createRestaurant(@HeaderParam("Owner") String ownerId,
                                     RestaurantDto restaurantDto,
                                     @Context UriInfo infosUri) {

        OwnerDto ownerDto = new OwnerDto();
        ownerDto.id = ownerId;
        String restaurantId = restaurantService.createRestaurant(ownerDto, restaurantDto);

        URI location = infosUri.getBaseUriBuilder().path("restaurants").path(restaurantId).build();
        return Response.created(location).build();
    }

    @GET
    @Path("/{id}")
    public Response getRestaurant(@HeaderParam("Owner") String ownerId,
                                  @PathParam("id") String restaurantId) {


        RestaurantDto restaurantDto = restaurantService.getRestaurant(restaurantId, ownerId);


        return Response.ok(restaurantDtoAssembler.toJson(restaurantDto)).build();
    }

    @GET
    public Response listRestaurants(@HeaderParam("Owner") String ownerId) {

        List<RestaurantDto> restaurantDtos = restaurantService.getRestaurants(ownerId);

        List<Map<String, Object>> restaurants = restaurantDtos
                .stream()
                .map(restaurantDtoAssembler::toJson)
                .toList();
        return Response.ok(restaurants).build();
    }

    @POST
    @Path("/{id}/reservations")
    public Response createReservation(@PathParam("id") String restaurantId,
                                      ReservationDto createReservation,
                                      @Context UriInfo infosUri) {
        String reservationId = reservationService.addReservation(restaurantId, createReservation);
        URI location = infosUri.getBaseUriBuilder().path("reservations").path(reservationId).build();
        return Response.created(location).build();
    }
}
