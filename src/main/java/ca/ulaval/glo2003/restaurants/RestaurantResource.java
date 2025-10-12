package ca.ulaval.glo2003.restaurants;

import ca.ulaval.glo2003.restaurants.domain.dtos.Hours;
import ca.ulaval.glo2003.restaurants.domain.dtos.Restaurant;
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
    @POST
    public Response createRestaurant(@HeaderParam("Owner") String ownerId,
                                    Restaurant restaurant,
                                    @Context UriInfo infosUri) {
        // Champs obligatoires
        if (ownerId == null || ownerId.isBlank()) {
            return badRequest("MISSING_PARAMETER", "`Owner` header is required");
        }
        if (restaurant == null) {
            return badRequest("MISSING_PARAMETER", "The body is required");
        }
        if (restaurant.getName() == null || restaurant.getHours() == null) {
            return badRequest("MISSING_PARAMETER", "name and hours are required");
        }
        if (restaurant.getHours().getOpen() == null || restaurant.getHours().getClose() == null) {
            return badRequest("MISSING_PARAMETER", "`hours.open` et `hours.close` are required");
        }

        // Valeurs invalides
        // Le nom ne peut pas être vide
        if (restaurant.getName().isBlank()) {
            return badRequest("INVALID_PARAMETER", "The name must not be empty");
        }
        // Capacité minimale de 1 personne
        if (restaurant.getCapacity() < 1) {
            return badRequest("INVALID_PARAMETER", "Capacity must be greater than zero");
        }

        // Validation des heures (format + bornes + ordre + durée >= 1h)
        LocalTime open, close;
        Hours hours;
        try {
            open = LocalTime.parse(restaurant.getHours().getOpen());   // "HH:mm:ss"
            close = LocalTime.parse(restaurant.getHours().getClose());
            hours = new Hours(open.toString(), close.toString());
        } catch (DateTimeParseException e) {
            return badRequest("INVALID_PARAMETER", "`hours.*` must be  HH:mm:ss");
        }

        LocalTime min = LocalTime.MIDNIGHT;           // 00:00:00
        LocalTime max = LocalTime.of(23, 59, 59);     // 23:59:59
        if (!open.isBefore(close)) {
            return badRequest("INVALID_PARAMETER", "`open` must be before `close`");
        }
        // Le restaurant ne peut pas ouvrir avant minuit (minimum 00:00:00)
        // Le restaurant doit fermer avant minuit (maximum 23:59:59)
        if (open.isBefore(min) || close.isAfter(max)) {
            return badRequest("INVALID_PARAMETER", "hours must be between 00:00:00 et 23:59:59");
        }
        // Doit être ouvert pendant au moins 1 heure
        if (Duration.between(open, close).toMinutes() < 60) {
            return badRequest("INVALID_PARAMETER", "The restaurant duration must be at least 1 hour");
        }

        // Création + persistance en mémoire
        String restaurantId = UUID.randomUUID().toString().replace("-", ""); // L'id doit être unique
        Restaurant resto = new Restaurant();
        resto.setId(restaurantId);
        resto.setOwnerId(ownerId);
        resto.setName(restaurant.getName());
        resto.setCapacity(restaurant.getCapacity());
        resto.setHours(hours);
        RestaurantRepository.save(resto);

        // 201 + Location: <host>/restaurants/<id>
        URI location = infosUri.getBaseUriBuilder().path("restaurants").path(restaurantId).build();
        return Response.created(location).build();
    }

    // ---------------------------------------
    // GET /restaurants/{id} : obtenir par id du restaurateur
    // ---------------------------------------
    @GET
    @Path("/{id}")
    public Response getRestaurant(@HeaderParam("Owner") String ownerId,
                                      @PathParam("id") String id) {
        if (ownerId == null || ownerId.isBlank()) {
            return badRequest("MISSING_PARAMETER", "`Owner` header is required");
        }

        var opt = RestaurantRepository.findById(id);
        if (opt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var resto = opt.get();
        // Accès réservé au propriétaire (sinon 404, on ne révèle pas l'existence)
        if (!ownerId.equals(resto.getOwnerId())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(toJson(resto)).build(); // ne pas exposer proprietaireId
    }

    // ---------------------------------------------------
    // GET /restaurants : lister pour un propriétaire
    // ---------------------------------------------------
    @GET
    public Response listRestaurants(@HeaderParam("Owner") String ownerId) {
        if (ownerId == null || ownerId.isBlank()) {
            return badRequest("MISSING_PARAMETER", "`Owner` header is required");
        }

        List<Map<String, Object>> sortie = RestaurantRepository
                .listByOwner(ownerId)
                .stream()
                .map(this::toJson) // masque proprietaireId
                .toList();

        return Response.ok(sortie).build();
    }

    // -----------------
    // Helpers internes
    // -----------------
    /** Formate la réponse sans exposer proprietaireId. */
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

    /** 400 BAD REQUEST au format attendu. */
    private Response badRequest(String code, String description) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", code, "description", description))
                .build();
    }
}
