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

    private final RestaurantDtoAssembler assembler = new RestaurantDtoAssembler();

    protected Application configure() {
        return (new AppContext()).getRessources();
    }

    @Test
    public void givenSearchRestaurants_whenCorrectRequest_thenResponseIsOk() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        Map<String, Object> json =  assembler.toJson(restaurantDto);

        try (Response response = target("/search/restaurants").request().post(Entity.json(json))) {

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenSearchRestaurants_whenWrongHourOpensFormat_thenResponseIsError() {
        End2EndTestUtils.postRestaurant(target("/restaurants"), End2EndTestUtils.buildDefaultRestaurantDto());

        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        restaurantDto.hours.close = "-1";
        Map<String, Object> json =  assembler.toJson(restaurantDto);

        try (Response response = target("/search/restaurants").request().post(Entity.json(json))) {

            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        }
    }
}
