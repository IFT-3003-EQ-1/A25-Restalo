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
    public String save(Menu menu) {
        datastore.save(menu);
        return menu.getId();
    }

    @Override
    public Optional<Menu> get(String id) {
        return Optional.ofNullable(datastore.find(Menu.class).filter(Filters.eq("id", id)).first());
    }
}
