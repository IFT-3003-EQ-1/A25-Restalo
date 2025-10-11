package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.AppContext;
import ca.ulaval.glo2003.Main;
import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.dtos.ProprietaireDto;
import ca.ulaval.glo2003.domain.dtos.RestaurantDto;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RestaurantEnd2EndTest extends JerseyTest {

    @Override
    protected Application configure() {
        return AppContext.getRessources();
    }

    @Test
    public void givenPostRestaurant_whenCorrectRequest_thenResponseIsCreated() {
        // given
        RestaurantDto restaurantDto = RestaurantEnd2EndUtils.buildDefaultRestaurantDto();

        Map<String, Object> json =  (new RestaurantDtoAssembler()).versJson(restaurantDto);
        // when

        Response response = target("/restaurants").request().header("Owner", "1").post(Entity.json(json));
        // expected

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenPostRestaurant_whenNullProprietaireId_thenResponseIsError() {
        RestaurantDto restaurantDto = RestaurantEnd2EndUtils.buildDefaultRestaurantDto();
        Map<String, Object> json =  (new RestaurantDtoAssembler()).versJson(restaurantDto);
        // when

        Response response = target("/restaurants").request().post(Entity.json(json));
        // expected

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }


}
