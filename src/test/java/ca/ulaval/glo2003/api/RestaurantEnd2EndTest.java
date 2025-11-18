package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.AppContext;
import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class RestaurantEnd2EndTest extends JerseyTest {

    private RestaurantDtoAssembler assembler;

    @Override
    protected Application configure() {
        return (new AppContext()).getRessources();
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
    public void givenCreateRestaurant_whenNullOwnerId_thenResponseIsBadRequest() {
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
            URI location = response.getLocation();
            assertNotNull(location, "Location header should be present");

            String locationPath = location.getPath();
            assertTrue(locationPath.startsWith("/restaurants/"),
                    "Location should start with /restaurants/");
            String restaurantId = extractIdFromLocation(response);
            assertNotNull(restaurantId, "Restaurant ID should be present");
            assertFalse(restaurantId.isEmpty(), "Restaurant ID should not be empty");

            assertFalse(response.hasEntity(),
                    "Response body should be empty for 201 CREATED");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void givenGetRestaurant_whenCorrectRequest_thenResponseIsOk() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        End2EndTestUtils.postRestaurant(target(), restaurantDto);

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
        End2EndTestUtils.postRestaurant(target(), restaurantDto);

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

    @Test
    public void givenDeleteRestaurant_whenCorrectRequest_thenResponseIsNoContent() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        String restaurantId = End2EndTestUtils.postRestaurant(target(), restaurantDto);

        try (Response response =  target("/restaurants/" + restaurantId)
                .request().header("Owner", restaurantDto.owner.id)
                .delete()) {
            assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenDeleteRestaurant_whenOwnerDoesntMatch_thenResponseIsNotFound() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        String restaurantId = End2EndTestUtils.postRestaurant(target(), restaurantDto);

        try (Response response =  target("/restaurants/" + restaurantId)
                .request().header("Owner", "-1")
                .delete()) {
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenDeleteRestaurant_whenRestaurantDoesntExist_thenResponseIsNotFound() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        End2EndTestUtils.postRestaurant(target(), restaurantDto);
        String restaurantId = "-1"; // "-1" id doesnt exist
        try (Response response =  target("/restaurants/" + restaurantId)
                .request().header("Owner", restaurantDto.owner.id)
                .delete()) {
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        }

    }

    @Test
    public void givenDeleteRestaurant_whenMissingParameter_thenResponseIsBadRequest() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        String restaurantId = End2EndTestUtils.postRestaurant(target(), restaurantDto);

        try (Response response =  target("/restaurants/" + restaurantId)
                .request().header("Owner", null)
                .delete()) {
            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        }
    }

    private String extractIdFromLocation(Response response) {
        URI location = response.getLocation();
        String path = location.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

}
