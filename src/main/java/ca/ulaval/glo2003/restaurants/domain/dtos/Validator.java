package ca.ulaval.glo2003.restaurants.domain.dtos;

import ca.ulaval.glo2003.restaurants.RestaurantRepository;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Validator {
    public static ValidationObject validateCreateRestaurantPayload(String ownerId, Restaurant restaurant) {
        ValidationObject validationObject = new ValidationObject(null, null);
        // Champs obligatoires

        if (restaurant == null) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("Restaurant is required");
            return validationObject;
        }

        if (restaurant.getName() == null || restaurant.getHours() == null) {
            //return badRequest("MISSING_PARAMETER", "name and hours are required");
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("name and hours is required");
            return validationObject;
        }
        if (restaurant.getHours().getOpen() == null || restaurant.getHours().getClose() == null) {
            //return badRequest("MISSING_PARAMETER", "`hours.open` et `hours.close` are required");
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("name and hours is required");
            return validationObject;
        }

        if (ownerId == null || ownerId.isBlank()) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("Owner id is required");
            return validationObject;
        }
        // Valeurs invalides
        // Le nom ne peut pas être vide
        if (restaurant.getName().isBlank()) {
            //return badRequest("INVALID_PARAMETER", "The name must not be empty");
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("name must not be blank");
            return validationObject;
        }
        // Capacité minimale de 1 personne
        if (restaurant.getCapacity() < 1) {
            //return badRequest("INVALID_PARAMETER", "Capacity must be greater than zero");
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("capacity is required to be at least 1");
            return validationObject;
        }

        // Validation des heures (format + bornes + ordre + durée >= 1h)
        LocalTime open, close;
        Hours hours;
        try {
            open = LocalTime.parse(restaurant.getHours().getOpen());   // "HH:mm:ss"
            close = LocalTime.parse(restaurant.getHours().getClose());
            hours = new Hours(open.toString(), close.toString());
        } catch (DateTimeParseException e) {
            //return badRequest("INVALID_PARAMETER", "`hours.*` must be  HH:mm:ss");
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("`hours.*` must be  HH:mm:ss");
            return validationObject;
        }

        LocalTime min = LocalTime.MIDNIGHT;           // 00:00:00
        LocalTime max = LocalTime.of(23, 59, 59);     // 23:59:59
        if (!open.isBefore(close)) {
                // return badRequest("INVALID_PARAMETER", "`open` must be before `close`");
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("open` must be before `close`");
            return validationObject;
        }
        // Le restaurant ne peut pas ouvrir avant minuit (minimum 00:00:00)
        // Le restaurant doit fermer avant minuit (maximum 23:59:59)
        if (open.isBefore(min) || close.isAfter(max)) {
            //return badRequest("INVALID_PARAMETER", "hours must be between 00:00:00 et 23:59:59");
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("hours must be between 00:00:00 et 23:59:59");
            return validationObject;
        }
        // Doit être ouvert pendant au moins 1 heure
        if (Duration.between(open, close).toMinutes() < 60) {
            //return badRequest("INVALID_PARAMETER", "The restaurant duration must be at least 1 hour");
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("The restaurant duration must be at least 1 hour");
            return validationObject;
        }
        return validationObject;
    }

    public static ValidationObject validateGetRestaurantPayload(String ownerId, String restaurantId) {
        ValidationObject validationObject = new ValidationObject(null, null);
        if (ownerId == null || ownerId.isBlank()) {
            //return badRequest("MISSING_PARAMETER", "`Owner` header is required");
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("Owner's id is required");
            return validationObject;
        }

        if (restaurantId == null || restaurantId.isBlank()) {
            //return badRequest("MISSING_PARAMETER", "`Owner` header is required");
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("id is required");
            return validationObject;
        }

       return validationObject;
    }

    public  static ValidationObject validateListRestaurantsPayload(String ownerId) {
        ValidationObject validationObject = new ValidationObject(null, null);
        if (ownerId == null || ownerId.isBlank()) {
           validationObject.setCode("MISSING_PARAMETER");
           validationObject.setDescription("Owner's id is required");
        }
        return validationObject;
    }
}
