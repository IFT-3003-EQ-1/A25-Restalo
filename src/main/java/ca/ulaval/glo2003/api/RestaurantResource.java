package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.api.requests.OwnerOnly;
import ca.ulaval.glo2003.domain.MenuService;
import ca.ulaval.glo2003.domain.ReservationService;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.menu.MenuRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
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
    private final RestaurantService restaurantService;
    private final RestaurantDtoAssembler restaurantDtoAssembler;
    private final ReservationService reservationService;
    private final MenuService menuService;

    public RestaurantResource(RestaurantService restaurantService,
                              RestaurantDtoAssembler restaurantDtoAssembler,
                              ReservationService reservationService, MenuService menuService
    ) {
        this.restaurantService = restaurantService;
        this.restaurantDtoAssembler = restaurantDtoAssembler;
        this.reservationService = reservationService;
        this.menuService = menuService;
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
    @OwnerOnly
    public Response getRestaurant(@HeaderParam("Owner") String ownerId,
                                  @PathParam("id") String restaurantId,
                                  @Context ContainerRequestContext crc) {



        RestaurantDto restaurantDto = (RestaurantDto) crc.getProperty("restaurant");
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

    @GET
    @Path("/{id}/reservations")
    @OwnerOnly
    public Response searchReservation(
            @HeaderParam("Owner") String ownerId,
            @PathParam("id") String restaurantId,
            @QueryParam("date") String reservationData,
            @QueryParam("customerName") String customerName,
            @Context UriInfo infosUri,
            @Context ContainerRequestContext crc
    ) {
        RestaurantDto restaurantDto = (RestaurantDto) crc.getProperty("restaurant");
        List<ReservationDto> reservations = reservationService.findBySearchCriteria(restaurantDto, customerName, reservationData);
        return Response.ok(reservations).build();
    }

    @DELETE
    @Path("/{id}")
    @OwnerOnly
    public Response deleteRestaurant(@PathParam("id") String restaurantId,
                                     @Context UriInfo infosUri,
                                     @Context ContainerRequestContext crc,
                                     @HeaderParam("Owner") String ownerId) {
        RestaurantDto restaurantDto = (RestaurantDto) crc.getProperty("restaurant");
        boolean isRestaurantDeleted = restaurantService.deleteRestaurant(restaurantDto.id);
        return Response.noContent().build();
    }

    @POST
    @Path("/menus")
    @OwnerOnly
    public Response createMenu(@HeaderParam("Owner") String ownerId,
                               @Context UriInfo infosUri,
                               @Context ContainerRequestContext crc,
                               MenuDto menuDto) {
        URI location_menu = infosUri.getBaseUri(); // TODO : point the URI on corresponding GET
        RestaurantDto restaurantDto = (RestaurantDto) crc.getProperty("restaurant");
        menuService.createMenu(menuDto, restaurantDto);
        return Response.created(location_menu).build();
    }
}
