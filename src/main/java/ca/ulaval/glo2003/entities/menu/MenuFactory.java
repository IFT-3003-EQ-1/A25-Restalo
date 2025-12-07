package ca.ulaval.glo2003.entities.menu;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.MenuItemDto;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import com.google.common.base.Strings;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MenuFactory {

    public Menu createMenu(MenuDto menuDto, Restaurant restaurant) {
        List<MenuItem> menuItems = new ArrayList<>();
        LocalDate start;

        if (Strings.isNullOrEmpty(menuDto.startDate))
            throw new InvalideParameterException("startDate can't be null or empty");

        try {
            start = LocalDate.parse(menuDto.startDate);
        } catch (DateTimeParseException e) {
            throw new InvalideParameterException("invalid date format : " + menuDto.startDate);
        }

        if (start.isAfter(LocalDate.now()))
            throw new InvalideParameterException("La date d'entré en vigueur du menu ne peut pas être dans le future.");

        if (menuDto.items.isEmpty())
            throw new InvalideParameterException("Le menu doit contenir au minimum un item");

        if(Strings.isNullOrEmpty(menuDto.title))
            menuDto.title = restaurant.getName();

        for (MenuItemDto item: menuDto.items) {
            menuItems.add(new MenuItem(
                    item.id,
                    item.name,
                    item.price
            ));
        }

        String id = UUID.randomUUID().toString().replace("-", "");

        return new Menu(
                id,
                restaurant,
                menuDto.title,
                menuDto.startDate,
                menuItems
        );
    }
}
