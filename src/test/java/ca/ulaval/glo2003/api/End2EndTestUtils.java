package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
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
        restaurantDto.hoursOpen = "11:00:00";
        restaurantDto.hoursClose = "19:00:00";
        restaurantDto.nom = "Pizz";
        restaurantDto.owner = ownerDto;
        return restaurantDto;
    }

    public static void postRestaurant(WebTarget target, RestaurantDto restaurantDto) {
        Map<String, Object> json =  (new RestaurantDtoAssembler()).versJson(restaurantDto);

        try (Response response = target.request().header("Owner", "1").post(Entity.json(json))) {

            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }
}
