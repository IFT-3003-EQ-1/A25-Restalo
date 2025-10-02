package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.entities.Restaurant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryRestaurantRepository implements RestaurantRepository {
    private final Map<String, Restaurant> database;

    public InMemoryRestaurantRepository() {
        database = new HashMap<>();
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
}
