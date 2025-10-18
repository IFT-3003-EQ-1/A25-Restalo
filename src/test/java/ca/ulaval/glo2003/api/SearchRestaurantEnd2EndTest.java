package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.AppContext;
import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchRestaurantEnd2EndTest extends JerseyTest {

    protected Application configure() {
        return AppContext.getRessources();
    }

    @Test
    public void givenRechercherRestaurants_whenCorrectRequest_thenResponseIsOk() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        Map<String, Object> json =  (new RestaurantDtoAssembler()).versJson(restaurantDto);

        try (Response response = target("/search/restaurants").request().post(Entity.json(json))) {

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenRechercherRestaurants_whenMauvaisFormatHoraireOuverture_thenResponseIsError() {
        End2EndTestUtils.postRestaurant(target("/restaurants"), End2EndTestUtils.buildDefaultRestaurantDto());

        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        restaurantDto.hoursClose = "-1";
        Map<String, Object> json =  (new RestaurantDtoAssembler()).versJson(restaurantDto);

        try (Response response = target("/search/restaurants").request().post(Entity.json(json))) {

            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        }
    }
}
