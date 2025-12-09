package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.menu.MenuFactory;
import ca.ulaval.glo2003.entities.menu.MenuRepository;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;

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
        Restaurant restaurant = restaurantFactory.fromDto(restaurantDto);
        Menu menu = menuFactory.createMenu(menuDto, restaurant);
        menuRepository.save(menu);
        return menu.getRestaurant().getId();
    }

    public MenuDto getMenu(String restaurantId) {
        Optional<Menu> retval = menuRepository.getFromRestaurantId(restaurantId);
        if(retval.isEmpty())
            throw new NotFoundException("Menu not found");

        return MenuDto.fromEntity(retval.get());
    }
}
