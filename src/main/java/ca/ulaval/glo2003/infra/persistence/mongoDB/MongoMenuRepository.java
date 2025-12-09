package ca.ulaval.glo2003.infra.persistence.mongoDB;

import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.menu.MenuRepository;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.util.Optional;

public class MongoMenuRepository implements MenuRepository {

    private final Datastore datastore;

    public MongoMenuRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public void save(Menu menu) {
        datastore.save(menu);
    }

    @Override
    public Optional<Menu> getFromRestaurantId(String restaurantId) {
        return Optional.ofNullable(datastore.find(Menu.class).filter(Filters.eq("restaurant.id", restaurantId)).first());
    }
}
