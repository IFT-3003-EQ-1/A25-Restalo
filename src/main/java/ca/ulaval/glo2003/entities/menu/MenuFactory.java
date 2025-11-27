package ca.ulaval.glo2003.entities.menu;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;

import java.util.ArrayList;

public class MenuFactory {

    public Menu createMenu(MenuDto menuDto, Restaurant restaurant) {

        return new Menu(
                menuDto.id,
                restaurant,
                menuDto.title,
                menuDto.startDate,
                new ArrayList<>()
        );
    }
}
