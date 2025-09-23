package ca.ulaval.glo2003.restaurants;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


final class DepotRestaurant {

    private static final Map<String, Restaurant> base = new ConcurrentHashMap<>();

    /** Enregistre (ou remplace) un restaurant en mémoire. */
    static String enregistrer(Restaurant restaurant) {
        base.put(restaurant.identifiant, restaurant);
        return restaurant.identifiant;
    }

    /** Retrouve un restaurant par identifiant. */
    static Optional<Restaurant> trouver(String identifiant) {
        return Optional.ofNullable(base.get(identifiant));
    }

    /** Liste les restaurants appartenant à un propriétaire donné. */
    static List<Restaurant> listerParProprietaire(String proprietaireId) {
        return base.values().stream()
                .filter(r -> Objects.equals(r.proprietaireId, proprietaireId))
                .toList();
    }

}
