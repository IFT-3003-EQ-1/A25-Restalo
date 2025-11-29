package ca.ulaval.glo2003.entities.menu;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.MenuItemDto;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MenuFactory {

    public Menu createMenu(MenuDto menuDto, Restaurant restaurant) {
        List<MenuItem> menuItems = new ArrayList<>();

        for (MenuItemDto item: menuDto.items) {
            menuItems.add(new MenuItem(
                    item.id,
                    item.name,
                    item.price
            ));
        }
        return new Menu(
                menuDto.id,
                restaurant,
                menuDto.title,
                menuDto.startDate,
                menuItems
        );
    }
}
