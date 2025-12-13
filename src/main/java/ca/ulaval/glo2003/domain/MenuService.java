package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.menu.MenuFactory;
import ca.ulaval.glo2003.entities.menu.MenuRepository;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;
import io.sentry.Sentry;

import java.util.Optional;

public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuFactory menuFactory;
    private final RestaurantFactory restaurantFactory;

    public MenuService(MenuRepository menuRepository, MenuFactory menuFactory, RestaurantFactory restaurantFactory) {
        this.menuRepository = menuRepository;
        this.menuFactory = menuFactory;
        this.restaurantFactory = restaurantFactory;
    }

    public String createMenu(MenuDto menuDto, RestaurantDto restaurantDto) {
        Sentry.setExtra("operation", "createMenu");
        Sentry.setExtra("restaurantId", restaurantDto.id);
        Sentry.setExtra("restaurantName", restaurantDto.name);
        Sentry.setExtra("menuItemsCount", String.valueOf(menuDto.items != null ? menuDto.items.size() : 0));

        Restaurant restaurant = restaurantFactory.fromDto(restaurantDto);
        Menu menu = menuFactory.createMenu(menuDto, restaurant);
        menuRepository.save(menu);

        return menu.getRestaurant().getId();
    }

    public MenuDto getMenu(String restaurantId) {
        Sentry.setExtra("operation", "getMenu");
        Sentry.setExtra("restaurantId", restaurantId);

        Optional<Menu> retval = menuRepository.getFromRestaurantId(restaurantId);

        if (retval.isEmpty()) {
            // L'exception sera automatiquement capturée par SentryExceptionMapper
            throw new NotFoundException("Menu not found");
        }

        return MenuDto.fromEntity(retval.get());
    }
}