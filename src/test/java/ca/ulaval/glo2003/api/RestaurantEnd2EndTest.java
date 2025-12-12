package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.AppContext;
import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import com.google.common.base.Strings;
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

    private RestaurantDto restaurantDto;

    private static final String INVALID_ID = "-1";

    @Override
    protected Application configure() {
        return (new AppContext()).getRessources();
    }

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        assembler = new RestaurantDtoAssembler();
        restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
    }

    @Test
    public void givenCreateRestaurant_whenCorrectRequest_thenResponseIsCreated() {
        Map<String, Object> json =  assembler.toJson(restaurantDto);

        try (Response response = target("/restaurants").request()
                .header("Owner", INVALID_ID).post(Entity.json(json))) {
            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void givenCreateRestaurant_whenNullOwnerId_thenResponseIsBadRequest() {
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
        ConfigReservationDto configReservationDto = new ConfigReservationDto();
        configReservationDto.duration = 60;
        restaurantDto.reservation = configReservationDto;
        Map<String, Object> json =  assembler.toJson(restaurantDto);

        try (Response response = target("/restaurants").request().header("Owner", INVALID_ID).post(Entity.json(json))) {
            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
            URI location = response.getLocation();
            assertNotNull(location, "Location header should be present");

            String locationPath = location.getPath();
            assertTrue(locationPath.startsWith("/restaurants/"),
                    "Location should start with /restaurants/");
            String restaurantId = End2EndTestUtils.extractIdFromLocation(response);
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
    public void givenListRestaurants_whenCorrectRequest_thenResponseIsOk() {
        End2EndTestUtils.postRestaurant(target(), restaurantDto);

        Response response = target("/restaurants").request().header("Owner", restaurantDto.owner.id).get();
        var restaurantDtos = response.readEntity(List.class);

        assertEquals(1, restaurantDtos.size());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenListRestaurants_whenInvalidOwnerId_thenResponseIsError() {
        Response response = target("/restaurants").request().header("Owner", "").get();

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenDeleteRestaurant_whenCorrectRequest_thenResponseIsNoContent() {
        String restaurantId = End2EndTestUtils.postRestaurant(target(), restaurantDto);

        try (Response response =  target("/restaurants/" + restaurantId)
                .request().header("Owner", restaurantDto.owner.id)
                .delete()) {
            assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenDeleteRestaurant_whenOwnerDoesntMatch_thenResponseIsNotFound() {
        String restaurantId = End2EndTestUtils.postRestaurant(target(), restaurantDto);

        try (Response response =  target("/restaurants/" + restaurantId)
                .request().header("Owner", INVALID_ID)
                .delete()) {
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenDeleteRestaurant_whenRestaurantDoesntExist_thenResponseIsNotFound() {
        End2EndTestUtils.postRestaurant(target(), restaurantDto);
        try (Response response =  target("/restaurants/" + INVALID_ID)
                .request().header("Owner", restaurantDto.owner.id)
                .delete()) {
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        }

    }

    @Test
    public void givenDeleteRestaurant_whenMissingParameter_thenResponseIsBadRequest() {
        String restaurantId = End2EndTestUtils.postRestaurant(target(), restaurantDto);

        try (Response response =  target("/restaurants/" + restaurantId)
                .request().header("Owner", null)
                .delete()) {
            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenSearchReservations_whenCorrectRequest_thenResponseIsOk() {
        ReservationDto reservationDto = End2EndTestUtils.buildReservationDto();
        End2EndTestUtils.postRestaurant(target(), restaurantDto);
        reservationDto.restaurant.id = restaurantDto.id;
        End2EndTestUtils.postReservation(target(), reservationDto);

        String date = reservationDto.date;
        String customerName = reservationDto.customer.name;

        try (Response response = target("/restaurants/" + restaurantDto.id  + "/reservations")
                .queryParam("date", date)
                .queryParam("customerName", customerName)
                .request().header("Owner", restaurantDto.owner.id).get()) {
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenSearchReservations_whenOwnerDoesntMatch_thenResponseIsNotFound() {
        ReservationDto reservationDto = End2EndTestUtils.buildReservationDto();
        End2EndTestUtils.postRestaurant(target(), restaurantDto);
        reservationDto.restaurant.id = restaurantDto.id;
        End2EndTestUtils.postReservation(target(), reservationDto);

        String date = reservationDto.date;
        String customerName = reservationDto.customer.name;

        try (Response response = target("/restaurants/" + restaurantDto.id  + "/reservations")
                .queryParam("date", date)
                .queryParam("customerName", customerName)
                .request().header("Owner", INVALID_ID).get()) {
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenSearchReservations_whenInvalidRestaurantId_thenResponseIsNotFound() {
        ReservationDto reservationDto = End2EndTestUtils.buildReservationDto();
        End2EndTestUtils.postRestaurant(target(), restaurantDto);
        reservationDto.restaurant.id = restaurantDto.id;
        End2EndTestUtils.postReservation(target(), reservationDto);

        String date = reservationDto.date;
        String customerName = reservationDto.customer.name;

        try (Response response = target("/restaurants/" + INVALID_ID  + "/reservations")
                .queryParam("date", date)
                .queryParam("customerName", customerName)
                .request().header("Owner", restaurantDto.owner.id).get()) {
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenCreateMenu_whenCorrectRequest_thenReturnLocation() {
        End2EndTestUtils.postRestaurant(target(), restaurantDto);
        MenuDto menuDto = End2EndTestUtils.buildDefaultMenuDto(restaurantDto.id);
        Map<String, Object> json = menuDto.toJson();

        try (Response response = target("/restaurants/" + restaurantDto.id + "/menus/").request()
                .header("Owner", restaurantDto.owner.id).post(Entity.json(json))) {
            String location = End2EndTestUtils.extractIdFromLocation(response);

            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
            assertFalse(Strings.isNullOrEmpty(location));
        }

    }

    @Test
    public void givenCreateMenu_whenInvalidParameter_thenReturnBadRequest() {
        End2EndTestUtils.postRestaurant(target(), restaurantDto);
        MenuDto menuDto = End2EndTestUtils.buildDefaultMenuDto(restaurantDto.id);

        menuDto.items.clear();
        Map<String, Object> json = menuDto.toJson();

        try (Response response = target("/restaurants/" + restaurantDto.id + "/menus/").request()
                .header("Owner", restaurantDto.owner.id).post(Entity.json(json))) {

            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenGetMenu_whenValidParameter_thenReturnMenu() {
        End2EndTestUtils.postRestaurant(target(), restaurantDto);
        MenuDto menuDto = End2EndTestUtils.buildDefaultMenuDto(restaurantDto.id);
        End2EndTestUtils.postMenu(target(), menuDto, restaurantDto.owner.id);

        try (Response response = target("/restaurants/" + restaurantDto.id + "/menus/").request().get()) {
            MenuDto dto = response.readEntity(MenuDto.class);

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(menuDto.restaurantId, dto.restaurantId);
        }
    }

    @Test
    public void givenGetMenu_whenMenuDoesntExist_thenReturnNotFound() {
        End2EndTestUtils.postRestaurant(target(), restaurantDto);

        try (Response response = target("/restaurants/" + restaurantDto.id + "/menus/").request().get()) {

            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        }
    }


    @Test
    public void givenWriteSalesReport_whenValidParameter_thenReturnCreated() {

    }

    @Test
    public void givenWriteSalesReport_whenMissingParameter_thenReturnBadRequest() {
    }


}
