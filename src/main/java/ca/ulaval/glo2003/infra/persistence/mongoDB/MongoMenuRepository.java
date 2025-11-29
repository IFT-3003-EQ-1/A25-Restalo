package ca.ulaval.glo2003.infra.persistence.mongoDB;

import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.menu.MenuRepository;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import dev.morphia.Datastore;

import java.util.Optional;

public class MongoMenuRepository implements MenuRepository {

    private final Datastore datastore;

    public MongoMenuRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public String save(Menu menu) {
        return "";
    }

    @Override
    public Optional<Menu> get(String id) {
        return null;
    }
}
