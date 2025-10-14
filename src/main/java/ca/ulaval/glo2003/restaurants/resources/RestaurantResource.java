package ca.ulaval.glo2003.restaurants.resources;

import ca.ulaval.glo2003.restaurants.domain.CreateReservationDto;
import ca.ulaval.glo2003.restaurants.domain.Reservation;
import ca.ulaval.glo2003.restaurants.domain.Restaurant;
import ca.ulaval.glo2003.restaurants.utils.Constant;
import ca.ulaval.glo2003.restaurants.utils.ValidationObject;
import ca.ulaval.glo2003.restaurants.utils.Validator;
import ca.ulaval.glo2003.restaurants.service.RestaurantService;
import ca.ulaval.glo2003.restaurants.utils.JsonUtil;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.Map;

@Path("/restaurants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestaurantResource {

    // ------------------------------
    // POST /restaurants : créer
    // ------------------------------
    private final RestaurantService restaurantService;

    public RestaurantResource() {
        this.restaurantService = new RestaurantService();
    }

    @POST
    public Response createRestaurant(@HeaderParam("Owner") String ownerId,
                                    Restaurant restaurant,
                                    @Context UriInfo infosUri) {
        ValidationObject validationObject = Validator.validateCreateRestaurantPayload(ownerId,restaurant);
        if(validationObject.getCode() != null){
             return badRequest(validationObject);
        }

       Restaurant restaurnt = this.restaurantService.createRestaurant(ownerId, restaurant);
         URI location = infosUri.getBaseUriBuilder().path("restaurants").path(restaurnt.getId()).build();
        return Response.created(location).build();
    }

    // ---------------------------------------
    // GET /restaurants/{id} : obtenir par id du restaurateur
    // ---------------------------------------
    @GET
    @Path("/{id}")
    public Response getRestaurant(@HeaderParam("Owner") String ownerId,
                                      @PathParam("id") String id) {
        ValidationObject validationObject = Validator.validateGetRestaurantPayload(ownerId, id);
       if(validationObject.getCode() != null){
           return badRequest(validationObject);
       }

       Restaurant restaurant = this.restaurantService.getRestaurantByOwner(ownerId, id);
       if(restaurant == null){
           return Response.status(Response.Status.NOT_FOUND).build();
       }
        return Response.ok(JsonUtil.toJson(restaurant)).build();
    }

    // ---------------------------------------------------
    // GET /restaurants : lister pour un propriétaire
    // ---------------------------------------------------
    @GET
    public Response listRestaurants(@HeaderParam("Owner") String ownerId) {
     ValidationObject validationObject = Validator.validateListRestaurantsPayload(ownerId);
     if(validationObject.getCode() != null){
         return badRequest(validationObject);
     }
        return Response.ok(this.restaurantService.listRestaurantsByOwner(ownerId)).build();
    }

    @POST
    @Path("/{id}/reservations")
    public Response createReservation(@PathParam("id") String restaurantId,
                                     CreateReservationDto createReservation,
                                     @Context UriInfo infosUri) {

        System.out.println(createReservation.toString());
        ValidationObject validationObject = Validator.validateCreateReservationPayload(restaurantId,createReservation);
        System.out.println(validationObject.toString());
        if(validationObject.getCode() != null){
            return badRequest(validationObject);
        }

        String result = this.restaurantService.addReservation(restaurantId, createReservation);

        if(result.equals(Constant.RESTAURANT_NOT_FOUND) || result.equals(Constant.BAD_RESERVATION_TIME)){
            return badRequest(new ValidationObject("MISSING_PARAMETER",result));
        }
        URI location = infosUri.getBaseUriBuilder().path("reservation").path(result).build();
        return Response.created(location).build();
    }

    private Response badRequest(ValidationObject validationObject) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", validationObject.getCode(), "description", validationObject.getDescription()))
                .build();
    }
}
