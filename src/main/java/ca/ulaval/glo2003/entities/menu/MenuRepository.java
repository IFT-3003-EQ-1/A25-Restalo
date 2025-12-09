package ca.ulaval.glo2003.entities.menu;

import java.util.Optional;

public interface MenuRepository {

    void save(Menu menu);

    Optional<Menu> getFromRestaurantId(String restaurantId);
}
