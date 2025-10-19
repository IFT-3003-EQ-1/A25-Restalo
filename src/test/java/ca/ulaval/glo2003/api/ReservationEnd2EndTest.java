package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.AppContext;
import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.dtos.CreateReservationDto;
import ca.ulaval.glo2003.domain.dtos.RestaurantDto;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReservationEnd2EndTest extends JerseyTest {
    @Override
    protected Application configure() {
        return AppContext.getRessources();
    }

    @Test
   public void givenGetReservation_whenValidRequest_thenSuccess() {
     try( Response createReservationResponse =  createReservation(End2EndTestUtils.buildReservationDto()) ) {
         String createdReservationId = extractIdFromLocation(createReservationResponse);
         Response getReservationResponse = target("/reservations/" + createdReservationId).request(MediaType.APPLICATION_JSON_TYPE).get();
         assertEquals(Response.Status.OK.getStatusCode(), getReservationResponse.getStatus());
     }catch (Exception e){
         fail(e.getMessage());
     }
   }

    @Test
    public void givenGetReservation_whenBadRequest_thenSuccess() {
        try( Response createReservationResponse =  createReservation(End2EndTestUtils.buildReservationDto()) ) {
            String createdReservationId = extractIdFromLocation(createReservationResponse);
            Response getReservationResponse = target("/reservations/" + createdReservationId+"_").request(MediaType.APPLICATION_JSON_TYPE).get();
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), getReservationResponse.getStatus());
        }catch (Exception e){
            fail(e.getMessage());
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
