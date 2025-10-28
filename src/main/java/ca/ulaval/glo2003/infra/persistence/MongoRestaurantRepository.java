package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.entities.filters.Filter;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.util.List;
import java.util.Optional;

public class MongoRestaurantRepository implements RestaurantRepository {
    private final Datastore datastore;

    public MongoRestaurantRepository() {
        this.datastore = MongoDBConnection.getInstance().getDatastore();
    }

    public MongoRestaurantRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public void save(Restaurant restaurant) {
        datastore.save(restaurant);
    }

    @Override
    public Optional<Restaurant> get(String id) {
        Restaurant restaurant = datastore.find(Restaurant.class)
                .filter(Filters.eq("_id", id))
                .first();
        return Optional.ofNullable(restaurant);
    }

    @Override
    public List<Restaurant> listByOwner(String ownerId) {
        return datastore.find(Restaurant.class)
                .filter(Filters.eq("owner.id", ownerId))
                .iterator()
                .toList();
    }

    @Override
    public List<Restaurant> searchRestaurants(List<Filter<Restaurant>> filters) {
        // Get all restaurants first
        List<Restaurant> allRestaurants = getAll();

        // Apply each filter in sequence
        return allRestaurants.stream()
                .filter(restaurant -> filters.stream()
                        .allMatch(filter -> filter.filter(restaurant)))
                .toList();
    }

    @Override
    public List<Restaurant> getAll() {
        return datastore.find(Restaurant.class)
                .iterator()
                .toList();
    }
}
