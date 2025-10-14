package ca.ulaval.glo2003.restaurants.utils;

import java.util.regex.Pattern;

public  final class Constant {
    public static  final  String RESTAURANT_NOT_FOUND = "Restaurant not found";
    public static final String BAD_RESERVATION_TIME ="Bad Reservation Time";
    public static final String EMAIL_REGEX = "^[^@]+@[^@]+\\.[^@]+$";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
}
