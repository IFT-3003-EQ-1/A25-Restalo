package ca.ulaval.glo2003.restaurants.utils;

import ca.ulaval.glo2003.restaurants.domain.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.regex.*;
import java.time.format.DateTimeParseException;

public class Validator {


    public static ValidationObject validateCreateRestaurantPayload(String ownerId, Restaurant restaurant) {
        ValidationObject validationObject = new ValidationObject(null, null);

        if (restaurant == null) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("Restaurant is required");
            return validationObject;
        }

        if (isEmpty(restaurant.getName())) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("restaurant name must not be blank");
            return validationObject;
        }

        if (restaurant.getHours() == null) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("hours is required");
            return validationObject;
        }
        if (isEmpty(restaurant.getHours().getOpen()) || isEmpty(restaurant.getHours().getClose())) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("name and hours is required");
            return validationObject;
        }

        if (isEmpty(ownerId)) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("Owner id is required");
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
        if (isEmpty(ownerId)) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("Owner's id is required");
            return validationObject;
        }

        if (isEmpty(restaurantId)) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("id is required");
            return validationObject;
        }

       return validationObject;
    }

    public  static ValidationObject validateListRestaurantsPayload(String ownerId) {
        ValidationObject validationObject = new ValidationObject(null, null);
        if (isEmpty(ownerId)) {
           validationObject.setCode("MISSING_PARAMETER");
           validationObject.setDescription("Owner's id is required");
        }
        return validationObject;
    }

    public static ValidationObject validateCreateReservationPayload(String restaurantId, CreateReservationDto reservation) {

        ValidationObject validationObject = new ValidationObject(null, null);
        if (isEmpty(restaurantId)) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("restaurant id is required");
            return validationObject;
        }

        if(reservation == null) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("reservation is required");
            return validationObject;
        }
        if(isEmpty(reservation.getDate())) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("reservation date is required");
            return validationObject;
        }
        if(isEmpty(reservation.getStartTime())) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("reservation start time is required");
            return validationObject;
        }
        if(reservation.getGroupSize() < 1) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("group size is required and mist be at least 1");
            return validationObject;
        }
        if(reservation.getCustomer() == null) {
            Customer customer = reservation.getCustomer();
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("customer is required");
            return validationObject;
        }

        Customer customer = reservation.getCustomer();
        if(isEmpty(customer.getName())) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("customer name is required");
            return validationObject;
        }

        if(!isValidEmail(customer.getEmail())) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("customer email is required to be x@y.z format");
            return validationObject;
        }

        if(isEmpty(customer.getPhoneNumber()) || customer.getPhoneNumber().length() != 10) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("customer phone number is required to be 10 digits");
            return validationObject;
        }
        return validationObject;
    }

    public static ValidationObject validateGetReservation(String reservationId) {
        ValidationObject validationObject = new ValidationObject(null, null);
        if(isEmpty(reservationId)) {
            validationObject.setCode("MISSING_PARAMETER");
            validationObject.setDescription("reservation id is required");
        }
        return validationObject;
    }

    private static boolean isEmpty(String value) {
        return value == null || value.isBlank();
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        Matcher matcher = Constant.EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
