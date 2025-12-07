package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.assemblers.ReservationDtoAssembler;
import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.dtos.*;
import ca.ulaval.glo2003.domain.dtos.restaurant.*;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class End2EndTestUtils {

    public static RestaurantDto buildDefaultRestaurantDto() {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.id = "1";

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.capacity = 2;
        restaurantDto.hours = new HourDto("11:00:00","19:00:00");
        restaurantDto.owner = ownerDto;
        restaurantDto.name = "Pizz";
        restaurantDto.hours.open = "11:00:00";
        restaurantDto.hours.close = "19:00:00";
        restaurantDto.name = "Pizz";
        restaurantDto.reservation = new ConfigReservationDto();
        restaurantDto.reservation.duration = 60;
        return restaurantDto;
    }

    public static ReservationDto buildReservationDto(){
        ReservationDto createReservationDto = new ReservationDto();
        createReservationDto.customer =
                (new CustomerDto("testName","test@mail.com","5144151540"));

        createReservationDto.date = "2025-10-18";
        createReservationDto.groupSize = 2;
        createReservationDto.time.start = "11:30:00";
        createReservationDto.time.end = "12:30:00";
        createReservationDto.restaurant = buildDefaultRestaurantDto();
        return createReservationDto;
    }

    public static MenuDto buildDefaultMenuDto() {
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
                buildDefaultRestaurantDto().id
        );
    }

    public static String postRestaurant(WebTarget target, RestaurantDto restaurantDto) {
        Map<String, Object> json =  (new RestaurantDtoAssembler()).toJson(restaurantDto);
        String restaurantId = null;
        try (Response response = target.path("/restaurants").request().header("Owner", "1").post(Entity.json(json))) {
            String[] pathFragments = response.getLocation().getPath().split("/");

            restaurantId = pathFragments[pathFragments.length-1];
            restaurantDto.id = restaurantId;


            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        return restaurantId;
    }

    public static String postReservation(WebTarget target, ReservationDto reservationDto) {
        Map<String, Object> json =  (new ReservationDtoAssembler()).toJson(reservationDto);
        String reservationNumber = null;
        target = target.path("/restaurants/" + reservationDto.restaurant.id + "/reservations");
        try (Response response = target.request().post(Entity.json(json))) {
            String[] pathFragments = response.getLocation().getPath().split("/");

            reservationNumber = pathFragments[pathFragments.length-1];
            reservationDto.number = reservationNumber;

            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        return reservationNumber;

    }

}
