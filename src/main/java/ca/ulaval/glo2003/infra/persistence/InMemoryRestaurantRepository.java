package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.filters.Filter;

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
    public List<Restaurant> listByOwner(String ownerId) {
        return database.values().stream()
                .filter(r -> Objects.equals(r.getOwner().getId(), ownerId))
                .toList();
    }

    @Override
    public List<Restaurant> searchRestaurants(List<Filter<Restaurant>> filters) {
        List<Restaurant> restaurants = new ArrayList<>();
        database.values().forEach(r -> {
            boolean isValide = true;
            for (Filter<Restaurant> filter : filters) {
                if(!filter.filter(r)) {
                    isValide = false;
                }
            }

            if (isValide) {
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
