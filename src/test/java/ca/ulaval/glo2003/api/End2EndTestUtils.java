package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.dtos.*;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class End2EndTestUtils {



    public static RestaurantDto buildDefaultRestaurantDto() {
        ProprietaireDto proprietaireDto = new ProprietaireDto();
        proprietaireDto.nom = "Bob";
        proprietaireDto.id = "1";

        RestaurantDto  restaurantDto = new RestaurantDto();
        restaurantDto.capacite = 2;
        restaurantDto.horaireOuverture = "11:00:00";
        restaurantDto.horaireFermeture = "19:00:00";
        restaurantDto.proprietaire = proprietaireDto;
        restaurantDto.nom = "Pizz";
        return restaurantDto;
    }

    public static CreateReservationDto buildReservationDto(){
         CreateReservationDto createReservationDto = new CreateReservationDto();
                              createReservationDto.setCustomer(new CustomerDto("testName","test@mail.com","5144151540"));
                              createReservationDto.setDate("2025-10-18");
                              createReservationDto.setGroupSize(2);
                              createReservationDto.setStartTime("11:30:00");
                              return createReservationDto;
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
