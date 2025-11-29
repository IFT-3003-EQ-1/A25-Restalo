package ca.ulaval.glo2003.entities.menu;

import java.util.Optional;

public interface MenuRepository {

    public String save(Menu menu);

    public Optional<Menu> get(String id);
}
