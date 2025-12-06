package ca.ulaval.glo2003.entities.menu;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.MenuItemDto;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MenuFactory {

    public Menu createMenu(MenuDto menuDto, Restaurant restaurant) {
        List<MenuItem> menuItems = new ArrayList<>();

        LocalTime start = LocalTime.parse(menuDto.startDate);

        if (start.isAfter(LocalTime.now()))
            throw new InvalideParameterException("La date d'entré en vigueur du menu ne peut pas être dans le future.");

        if (menuDto.items.isEmpty())
            throw new InvalideParameterException("Le menu doit contenir au minimum un item");

        if(menuDto.title == null)
            menuDto.title = restaurant.getName();

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
