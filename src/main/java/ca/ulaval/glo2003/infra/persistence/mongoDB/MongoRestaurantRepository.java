package ca.ulaval.glo2003.infra.persistence.mongoDB;

import ca.ulaval.glo2003.entities.filters.Filter;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.RestaurantRepository;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.util.List;
import java.util.Optional;

public class MongoRestaurantRepository implements RestaurantRepository {
    private final Datastore datastore;

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
        // Apply each filter in sequence
        return datastore.find(Restaurant.class).stream()
                .filter(restaurant -> filters.stream().allMatch(
                        filter -> filter.filter(restaurant)
                        )
                )
                .toList();
    }

    @Override
    public List<Restaurant> getAll() {
        return datastore.find(Restaurant.class)
                .iterator()
                .toList();
    }

    @Override
    public boolean delete(String id) {
        return datastore.find(Restaurant.class)
                .filter(Filters.eq("id", id))
                .delete()
                .getDeletedCount() != 0;
    }
}
