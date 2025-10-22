package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.AppContext;
import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class RestaurantEnd2EndTest extends JerseyTest {

    private RestaurantDtoAssembler assembler;

    @Override
    protected Application configure() {
        return AppContext.getRessources();
    }

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        assembler = new RestaurantDtoAssembler();
    }

    @Test
    public void givenCreateRestaurant_whenCorrectRequest_thenResponseIsCreated() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();

        Map<String, Object> json =  assembler.toJson(restaurantDto);

        try (Response response = target("/restaurants").request().header("Owner", "1").post(Entity.json(json))) {
            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void givenCreateRestaurant_whenNullProprietaireId_thenResponseIsBadRequest() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        Map<String, Object> json =  assembler.toJson(restaurantDto);

        try (Response response = target("/restaurants").request().post(Entity.json(json))) {
            ErrorDto errorDto = response.readEntity(ErrorDto.class);
            assertEquals("MISSING_PARAMETER", errorDto.error);
            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void givenCreateRestaurant_whenConfigReservationIsProvided_thenResponseIsCreated() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        ConfigReservationDto configReservationDto = new ConfigReservationDto();
        configReservationDto.duration = 60;
        restaurantDto.reservation = configReservationDto;
        Map<String, Object> json =  assembler.toJson(restaurantDto);

        try (Response response = target("/restaurants").request().header("Owner", "1").post(Entity.json(json))) {
            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void givenGetRestaurant_whenCorrectRequest_thenResponseIsOk() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        End2EndTestUtils.postRestaurant(target("/restaurants"), restaurantDto);

        Response response = target("/restaurants").queryParam("id", restaurantDto.id).request().header("Owner", "1").get();
        RestaurantDto content  = response.readEntity(RestaurantDto.class);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(restaurantDto.name, content.name);
        assertEquals(restaurantDto.capacity, content.capacity);
        assertEquals(restaurantDto.hours.close, content.hours.close);
        assertEquals(restaurantDto.hours.open, content.hours.open);
        assertEquals(restaurantDto.reservation.duration, content.reservation.duration);
    }

    @Test
    public void givenGetRestaurant_whenNullOwnerId_thenResponseIsError() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();

        Response response = target("/restaurants").queryParam("id", restaurantDto.id).request().get();
        ErrorDto errorDto = response.readEntity(ErrorDto.class);

        assertTrue(errorDto.error.contains("MISSING_PARAMETER"));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenListRestaurants_whenCorrectRequest_thenResponseIsOk() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        End2EndTestUtils.postRestaurant(target("/restaurants"), restaurantDto);

        Response response = target("/restaurants").request().header("Owner", "1").get();
        List<RestaurantDto> restaurantDtos = response.readEntity(List.class);

        assertEquals(1, restaurantDtos.size());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenListRestaurants_whenNullOwnerId_thenResponseIsError() {

        Response response = target("/restaurants").request().header("Owner", "").get();

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

}
