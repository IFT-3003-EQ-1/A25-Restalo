package ca.ulaval.glo2003.restaurants.utils;

import ca.ulaval.glo2003.restaurants.domain.Hours;
import ca.ulaval.glo2003.restaurants.domain.Restaurant;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Validator {
    public static ValidationObject validateCreateRestaurantPayload(String ownerId, Restaurant restaurant) {
        ValidationObject validationObject = new ValidationObject(null, null);

        if (restaurant == null) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("Restaurant is required");
            return validationObject;
        }

        if (restaurant.getName() == null || restaurant.getHours() == null) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("name and hours is required");
            return validationObject;
        }
        if (restaurant.getHours().getOpen() == null || restaurant.getHours().getClose() == null) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("name and hours is required");
            return validationObject;
        }

        if (ownerId == null || ownerId.isBlank()) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("Owner id is required");
            return validationObject;
        }

        if (restaurant.getName().isBlank()) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("name must not be blank");
            return validationObject;
        }
        if (restaurant.getCapacity() < 1) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("capacity is required to be at least 1");
            return validationObject;
        }

        LocalTime open, close;
        Hours hours;
        try {
            open = LocalTime.parse(restaurant.getHours().getOpen());   // "HH:mm:ss"
            close = LocalTime.parse(restaurant.getHours().getClose());
            hours = new Hours(open.toString(), close.toString());
        } catch (DateTimeParseException e) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("`hours.*` must be  HH:mm:ss");
            return validationObject;
        }

        LocalTime min = LocalTime.MIDNIGHT; // 00:00:00
        LocalTime max = LocalTime.of(23, 59, 59); // 23:59:59
        if (!open.isBefore(close)) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("open` must be before `close`");
            return validationObject;
        }
        // Le restaurant ne peut pas ouvrir avant minuit (minimum 00:00:00)
        // Le restaurant doit fermer avant minuit (maximum 23:59:59)
        if (open.isBefore(min) || close.isAfter(max)) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("hours must be between 00:00:00 et 23:59:59");
            return validationObject;
        }
        // Doit être ouvert pendant au moins 1 heure
        if (Duration.between(open, close).toMinutes() < 60) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("The restaurant duration must be at least 1 hour");
            return validationObject;
        }
        return validationObject;
    }

    public static ValidationObject validateGetRestaurantPayload(String ownerId, String restaurantId) {
        ValidationObject validationObject = new ValidationObject(null, null);
        if (ownerId == null || ownerId.isBlank()) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("Owner's id is required");
            return validationObject;
        }

        if (restaurantId == null || restaurantId.isBlank()) {
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
