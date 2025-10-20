package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.dtos.*;
import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.HourDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.Customer;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class End2EndTestUtils {



    public static RestaurantDto buildDefaultRestaurantDto() {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.id = "1";

        RestaurantDto  restaurantDto = new RestaurantDto();
        restaurantDto.capacity = 2;
        restaurantDto.hours= new HourDto("11:00:00","19:00:00");
        restaurantDto.owner = ownerDto;
        restaurantDto.name = "Pizz";
        restaurantDto.hours.open = "11:00:00";
        restaurantDto.hours.close = "19:00:00";
        restaurantDto.name = "Pizz";
        restaurantDto.configReservation = new ConfigReservationDto();
        restaurantDto.configReservation.duration = 60;
        return restaurantDto;
    }

    public static ReservationDto buildReservationDto(){
         ReservationDto createReservationDto = new ReservationDto();
                              createReservationDto.customer =(new Customer("testName","test@mail.com","5144151540"));
                              createReservationDto.date= "2025-10-18";
                              createReservationDto.groupSize = 2;
                              createReservationDto.startTime="11:30:00";
                              return createReservationDto;
    }

    public static void postRestaurant(WebTarget target, RestaurantDto restaurantDto) {
        Map<String, Object> json =  (new RestaurantDtoAssembler()).toJson(restaurantDto);

        try (Response response = target.request().header("Owner", "1").post(Entity.json(json))) {

            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
