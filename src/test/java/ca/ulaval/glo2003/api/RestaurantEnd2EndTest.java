package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.AppContext;
import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.dtos.CreateReservationDto;
import ca.ulaval.glo2003.domain.dtos.RestaurantDto;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class RestaurantEnd2EndTest extends JerseyTest {

    @Override
    protected Application configure() {
        return AppContext.getRessources();
    }

    @Test
    public void givenCreerRestaurant_whenCorrectRequest_thenResponseIsCreated() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        Map<String, Object> json =  (new RestaurantDtoAssembler()).versJson(restaurantDto);

        try (Response response = target("/restaurants").request().header("Owner", "1").post(Entity.json(json))) {

            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void givenCreateReservation_whenCorrectRequest_thenResponseIsCreated() {
        try (Response createReservationResponse = createReservation(End2EndTestUtils.buildReservationDto())) {
            assertEquals(Response.Status.CREATED.getStatusCode(), createReservationResponse.getStatus());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void givenCreateReservation_whenBadRequest_thenResponseIs_Http400() {
        CreateReservationDto reservationDto =End2EndTestUtils.buildReservationDto();
        reservationDto.setCustomer(null);
        try (Response createReservationResponse = createReservation(reservationDto)) {
            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), createReservationResponse.getStatus());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void givenCreerRestaurant_whenNullProprietaireId_thenResponseIsError() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        Map<String, Object> json =  (new RestaurantDtoAssembler()).versJson(restaurantDto);

        try (Response response = target("/restaurants").request().post(Entity.json(json))) {

            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void givenObtenirRestaurant_whenCorrectRequest_thenResponseIsOk() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();

        Response response = target("/restaurants").queryParam("id", restaurantDto.id).request().header("Owner", "1").get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenObtenirRestaurant_whenNullProprietaireId_thenResponseIsError() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();

        Response response = target("/restaurants").queryParam("id", restaurantDto.id).request().get();

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenListerRestaurants_whenCorrectRequest_thenResponseIsOk() {

        Response response = target("/restaurants").request().header("Owner", "1").get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

    @Test
    public void givenListerRestaurants_whenNullProprietaireId_thenResponseIsError() {

        Response response = target("/restaurants").request().header("Owner", "").get();

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

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
        restaurantDto.horaireFermeture = "-1";
        Map<String, Object> json =  (new RestaurantDtoAssembler()).versJson(restaurantDto);

        try (Response response = target("/search/restaurants").request().post(Entity.json(json))) {

            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        }
    }

    private Response createReservation(CreateReservationDto createReservationDto) {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        Map<String, Object> json = new RestaurantDtoAssembler().versJson(restaurantDto);

        try (Response createRestaurantResponse = target("/restaurants")
                .request()
                .header("Owner", "1")
                .post(Entity.json(json))) {

            String restaurantId = extractIdFromLocation(createRestaurantResponse);
            return target("/restaurants/" + restaurantId + "/reservations")
                    .request()
                    .post(Entity.json(createReservationDto));
        }
    }

    private String extractIdFromLocation(Response response) {
        URI location = response.getLocation();
        String path = location.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
