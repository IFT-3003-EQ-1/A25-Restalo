package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.filters.Filter;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
    void save(Restaurant restaurant);

    Optional<Restaurant> get(String id);

    List<Restaurant> listByOwner(String ownerId);

    List<Restaurant> searchRestaurants(List<Filter<Restaurant>> filters);

    List<Restaurant> getAll();

    boolean delete(String id);
}
