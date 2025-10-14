package ca.ulaval.glo2003.restaurants;

import ca.ulaval.glo2003.restaurants.domain.Reservation;
import ca.ulaval.glo2003.restaurants.domain.Restaurant;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


final public class RestaurantRepository {

    private static final Map<String, Restaurant> restaurantsMap = new ConcurrentHashMap<>();
    private static final Map<String, Reservation> reservationMap = new ConcurrentHashMap<>();

    /** Enregistre (ou remplace) un restaurant en mémoire. */
    public static String save(Restaurant restaurant) {
        restaurantsMap.put(restaurant.getId(), restaurant);
        return restaurant.getId();
    }

    /** Retrouve un restaurant par identifiant. */
    public static Optional<Restaurant> findById(String identifiant) {
        return Optional.ofNullable(restaurantsMap.get(identifiant));
    }

    /** Liste les restaurants appartenant à un propriétaire donné. */
    public static List<Restaurant> listByOwner(String ownerId) {
        return restaurantsMap.values().stream()
                .filter(r -> Objects.equals(r.getOwnerId(), ownerId))
                .toList();
    }

    public static String addReservation(Reservation reservation) {
        reservationMap.put(reservation.getNumber(), reservation);
        return reservation.getNumber();
    }

    public static Reservation findReservationById(String reservationId) {
        return reservationMap.get(reservationId);
    }
}
