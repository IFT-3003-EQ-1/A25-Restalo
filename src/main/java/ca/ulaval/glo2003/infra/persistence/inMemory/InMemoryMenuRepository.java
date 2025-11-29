package ca.ulaval.glo2003.infra.persistence.inMemory;

import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.menu.MenuRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryMenuRepository implements MenuRepository {

    private final Map<String, Menu> database;

    public InMemoryMenuRepository() {
        database = new HashMap<>();
    }

    public InMemoryMenuRepository(Map<String, Menu> database) {
        this.database = database;
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
