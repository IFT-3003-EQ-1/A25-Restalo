package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.entities.Restaurant;
import ca.ulaval.glo2003.entities.filtres.Filtre;

import java.util.*;

public class InMemoryRestaurantRepository implements RestaurantRepository {
    private final Map<String, Restaurant> database;

    public InMemoryRestaurantRepository() {
        database = new HashMap<>();
    }

    public InMemoryRestaurantRepository(Map<String, Restaurant> database) {
        this.database = database;
    }

    @Override
    public void save(Restaurant restaurant) {
        this.database.put(restaurant.getId(), restaurant);
    }

    @Override
    public Optional<Restaurant> get(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Restaurant> listParProprietaire(String proprietaireId) {
        return database.values().stream()
                .filter(r -> Objects.equals(r.getProprietaire().getId(), proprietaireId))
                .toList();
    }

    @Override
    public List<Restaurant> searchRestaurants(List<Filtre<Restaurant>> filtres) {
        List<Restaurant> restaurants = new ArrayList<>();
        database.values().forEach(r -> {
            boolean valide = true;
            for (Filtre<Restaurant> filtre : filtres) {
                if(!filtre.filtrer(r)) {
                    valide = false;
                }
            }

            if (valide) {
                restaurants.add(r);
            }
        });
        return restaurants;
    }

    @Override
    public List<Restaurant> getAll() {
        return database.values().stream().toList();
    }
}
