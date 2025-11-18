package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.AppContext;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationRessourceEnd2EndTest extends JerseyTest {

    private RestaurantDto restaurantDto;

    private ReservationDto reservationDto;

    private static final String INVALID_ID = "-1";

    @Override
    protected Application configure() {
        return (new AppContext()).getRessources();
    }

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        reservationDto = End2EndTestUtils.buildReservationDto();

        reservationDto.restaurant.id = End2EndTestUtils.postRestaurant(target(), restaurantDto);
    }

    @Test
    public void givenGetReservation_whenRequestIsValid_thenReturnOK() {
        End2EndTestUtils.postReservation(target(), reservationDto);

        try (Response response = target("/reservations/"+ reservationDto.number).request().get()) {
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenGetReservation_whenIdIsNotValid_thenReturnNotFound() {
        try (Response response = target("/reservations/" + INVALID_ID).request().get()) {
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenDeleteReservation_whenRequestIsValid_thenReturnNoContent() {
        End2EndTestUtils.postReservation(target(), reservationDto);
        try (Response response = target("/reservations/" + reservationDto.number).request().delete()) {
            assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void givenDeleteReservation_whenNumberIsNotValid_thenReturnNotFound() {
        End2EndTestUtils.postReservation(target(), reservationDto);
        try  (Response response = target("/reservations/" + INVALID_ID).request().delete()) {
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        }
    }
}
