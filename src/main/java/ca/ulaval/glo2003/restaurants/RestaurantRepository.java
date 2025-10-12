package ca.ulaval.glo2003.restaurants;

import ca.ulaval.glo2003.restaurants.domain.dtos.Restaurant;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


final class RestaurantRepository {

    private static final Map<String, Restaurant> restaurantsMap = new ConcurrentHashMap<>();

    /** Enregistre (ou remplace) un restaurant en mémoire. */
    static String save(Restaurant restaurant) {
        restaurantsMap.put(restaurant.getId(), restaurant);
        return restaurant.getId();
    }

    /** Retrouve un restaurant par identifiant. */
    static Optional<Restaurant> findById(String identifiant) {
        return Optional.ofNullable(restaurantsMap.get(identifiant));
    }

    /** Liste les restaurants appartenant à un propriétaire donné. */
    static List<Restaurant> listByOwner(String ownerId) {
        return restaurantsMap.values().stream()
                .filter(r -> Objects.equals(r.getOwnerId(), ownerId))
                .toList();
    }

}
