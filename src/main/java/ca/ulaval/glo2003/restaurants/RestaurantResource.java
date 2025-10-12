package ca.ulaval.glo2003.restaurants;

import ca.ulaval.glo2003.restaurants.domain.dtos.Hours;
import ca.ulaval.glo2003.restaurants.domain.dtos.Restaurant;
import ca.ulaval.glo2003.restaurants.domain.dtos.ValidationObject;
import ca.ulaval.glo2003.restaurants.domain.dtos.Validator;
import ca.ulaval.glo2003.restaurants.service.RestaurantService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        return Response.ok(toJson(restaurant)).build();
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

    // -----------------
    // Helpers internes
    // -----------------
    /** Formate la réponse sans exposer proprietaireId. */

    /** 400 BAD REQUEST au format attendu. */
    private Response badRequest(ValidationObject validationObject) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", validationObject.getCode(), "description", validationObject.getDescription()))
                .build();
    }

    private Map<String, Object> toJson(Restaurant r) {
        return Map.of(
                "id", r.getId(),
                "name", r.getName(),
                "capacity", r.getCapacity(),
                "hours", Map.of(
                        "open", r.getHours().getOpen(),
                        "close", r.getHours().getClose()
                )
        );
    }
}
