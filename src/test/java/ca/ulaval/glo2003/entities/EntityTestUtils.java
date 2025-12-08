package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.domain.dtos.restaurant.*;
import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.menu.MenuItem;
import ca.ulaval.glo2003.entities.restaurant.ConfigReservation;
import ca.ulaval.glo2003.entities.restaurant.Hours;
import ca.ulaval.glo2003.entities.restaurant.Owner;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityTestUtils {


    public static Restaurant createRestaurant() {
        return new Restaurant(
                "restaurant-123",
                new Owner("1"),
                "Hot pizza",
                20,
                new Hours("10:00:00","22:00:00"),
                new ConfigReservation(60)
        );
    }

    public static RestaurantDto getRestaurantDto() {

        OwnerDto ownerDto = new OwnerDto();
        ownerDto.id = "1";

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.id = "restaurant-123";
        restaurantDto.owner = ownerDto;
        restaurantDto.name = "Hot pizza";
        restaurantDto.hours = new HourDto("10:00:00","22:00:00");
        restaurantDto.capacity = 20;
        restaurantDto.reservation = new ConfigReservationDto();
        restaurantDto.reservation.duration = 60;
        return restaurantDto;
    }

    public static MenuDto getMenuDto() {
        List<MenuItemDto> items = new ArrayList<>();
        items.add(new MenuItemDto(
                "1",
                "steak",
                30.0F
        ));
        return new MenuDto(
                "1",
                "Menu - 1",
                "2024-04-05",
                items,
                getRestaurantDto().id
        );
    }

    public static Menu getMenu() {
        MenuDto menuDto = getMenuDto();
        List<MenuItem> items = menuDto.items.stream()
                .map(dto -> new MenuItem(dto.id, dto.name, dto.price)).toList();
        return new Menu(
                menuDto.id,
                createRestaurant(),
                menuDto.title,
                menuDto.startDate,
                items
        );
    }

    public static Map<String, Object> menuJson() {
        MenuDto dto = getMenuDto();
        Map<String, Object> menuJson = new HashMap<>();
        menuJson.put("id", dto.id);
        menuJson.put("title", dto.title);
        menuJson.put("startDate", dto.startDate);
        menuJson.put("restaurantId", dto.restaurantId);
        menuJson.put("items", dto.items.stream().map(MenuItemDto::toJson).toList());
        return menuJson;
    }
}
