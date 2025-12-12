package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.AppContext;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppConfigEnd2EndTest extends JerseyTest {

    private static final String INVALID_ID = "-1";

    protected Application configure() {
        return (new AppContext()).getRessources();
    }

    @Test
    public void useInMemoryDb_forEnd2EndTests() {
        String mongoDBmode = "inmemory";
        assertEquals(mongoDBmode,System.getProperty("persistence", "inmemory"), "End2EndTest should be configured to use the inmemory DB");
    }

    /**
     * The following test aim at validing the E2E behavior of the @OwnerOnly (AutorizationRequestFilter.)
     * Since it's an E2E test, we validate the behavior through a route which "calls" the Autorization logic.
     */
    @Test
    public void givenGetRestaurant_whenNullOwnerId_thenResponseIsBadRequest() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        End2EndTestUtils.postRestaurant(target(), restaurantDto);

        Response response = target("/restaurants/" + restaurantDto.id).request().header("Owner", null).get();
        ErrorDto errorDto = response.readEntity(ErrorDto.class);

        assertTrue(errorDto.error.contains("MISSING_PARAMETER"));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenGetRestaurant_whenInvalideRestaurantId_thenResponseIsNotFound() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        End2EndTestUtils.postRestaurant(target(), restaurantDto);

        Response response = target("/restaurants/" + INVALID_ID).request().header("Owner", restaurantDto.owner.id).get();
        ErrorDto errorDto = response.readEntity(ErrorDto.class);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenGetRestaurant_whenOwnerIdDoesntMatch_thenResponseIsBadRequest() {
        RestaurantDto restaurantDto = End2EndTestUtils.buildDefaultRestaurantDto();
        End2EndTestUtils.postRestaurant(target(), restaurantDto);

        Response response = target("/restaurants/" + restaurantDto.id).queryParam("id", INVALID_ID).request().get();
        ErrorDto errorDto = response.readEntity(ErrorDto.class);

        assertTrue(errorDto.error.contains("MISSING_PARAMETER"));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

}
