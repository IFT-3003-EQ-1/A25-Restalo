package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.menu.MenuFactory;
import ca.ulaval.glo2003.entities.menu.MenuRepository;
import ca.ulaval.glo2003.entities.restaurant.OwnerFactory;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;

import java.util.Optional;

public class MenuService {

    private final MenuRepository menuRepository;

    private final MenuFactory menuFactory;

    private final RestaurantFactory restaurantFactory;

    private final OwnerFactory ownerFactory;

    public MenuService(MenuRepository menuRepository, MenuFactory menuFactory, RestaurantFactory restaurantFactory, OwnerFactory ownerFactory) {
        this.menuRepository = menuRepository;
        this.menuFactory = menuFactory;
        this.restaurantFactory = restaurantFactory;
        this.ownerFactory = ownerFactory;
    }


    public String createMenu(MenuDto menuDto, RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantFactory.createRestaurant(
                ownerFactory.createOwner(restaurantDto.owner.id),
                restaurantDto
        );
        Menu menu = menuFactory.createMenu(menuDto, restaurant);

        return menuRepository.save(menu);
    }

    public MenuDto getMenu(String restaurantId) {
        Optional<Menu> retval = menuRepository.get(restaurantId);
        if(retval.isEmpty())
            throw new NotFoundException("Menu not found");

        return MenuDto.fromEntity(retval.get());
    }
}
