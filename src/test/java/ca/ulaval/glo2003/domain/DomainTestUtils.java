package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.ReservationTimeDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.*;
import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.menu.MenuItem;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.reservation.ReservationTime;
import ca.ulaval.glo2003.entities.restaurant.ConfigReservation;
import ca.ulaval.glo2003.entities.restaurant.Hours;
import ca.ulaval.glo2003.entities.restaurant.Owner;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class DomainTestUtils {

    public static RestaurantDto getRestaurantDto() {

        OwnerDto ownerDto = new OwnerDto();
        ownerDto.id = "1";

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.id = "1";
        restaurantDto.owner = ownerDto;
        restaurantDto.name = "Pizz";
        restaurantDto.hours = new HourDto("11:00:00","19:00:00");
        restaurantDto.capacity = 2;
        restaurantDto.reservation = new ConfigReservationDto();
        restaurantDto.reservation.duration = 60;
        return restaurantDto;
    }

    public static ReservationDto getReservationDto() {
        return new ReservationDto(
                "10",
                "2014-04-05",
                new ReservationTimeDto(),
                2,
                new CustomerDto(),
                getRestaurantDto()
        );
    }

    public static Restaurant getRestaurant() {
        RestaurantDto restaurantDto = getRestaurantDto();
        return new Restaurant(
                restaurantDto.id,
                new Owner(restaurantDto.owner.id),
                restaurantDto.name,
                restaurantDto.capacity,
                new Hours(restaurantDto.hours.open, restaurantDto.hours.close),
                new ConfigReservation(restaurantDto.reservation.duration)
        );
    }

    public static Reservation getReservation() {
        ReservationDto reservationDto = getReservationDto();
        Restaurant restaurant = getRestaurant();
        return new Reservation(
                reservationDto.number,
                reservationDto.date,
                new ReservationTime(reservationDto.time.start, reservationDto.time.end),
                reservationDto.groupSize,
                new Customer(
                        reservationDto.customer.name,
                        reservationDto.customer.email,
                        reservationDto.customer.phoneNumber
                ),
                restaurant
        );
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
                getRestaurant(),
                menuDto.title,
                menuDto.startDate,
                items
        );
    }
}
