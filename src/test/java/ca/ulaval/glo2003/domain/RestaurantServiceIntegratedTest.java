package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.HourDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.restaurant.OwnerFactory;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import ca.ulaval.glo2003.entities.filters.FilterRestaurantFactory;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryReservationRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemorySalesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantServiceIntegratedTest {

    private RestaurantService restaurantService;

    private RestaurantDto restaurantDto;


    @BeforeEach
    public void setUp() {
        restaurantService = new RestaurantService(
                new RestaurantFactory(),
                new InMemoryRestaurantRepository(),
                new InMemoryReservationRepository(),
                new InMemorySalesRepository(),
                new OwnerFactory(),
                new RestaurantAssembler(),
                new FilterRestaurantFactory()
        );
        restaurantDto = DomainTestUtils.getRestaurantDto();
    }

    @Test
    public void givenCreateRestaurant_whenParameterAreValide_thenRestaurantIsCreated() {
        String restaurantId = restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);

        assertNotNull(restaurantId);
    }

    @Test
    public void givenCreateRestaurant_whenProprietaireIsNull_thenParameterManquantExceptionIsThrown() {
        restaurantDto.owner.id = null;

        assertThrows(MissingParameterException.class, () -> restaurantService.createRestaurant(restaurantDto.owner, restaurantDto));
    }

    @Test
    public void givenCreateRestaurant_whenNomIsNull_thenParameterManquantExceptionIsThrown() {
        restaurantDto.name = null;

        assertThrows(MissingParameterException.class, () -> restaurantService.createRestaurant(restaurantDto.owner, restaurantDto));
    }

    @Test
    public void givenCreateRestaurant_whenHoraireFermetureIsNull_thenParameterManquantExceptionIsThrown() {
        restaurantDto.hours.close = null;

        assertThrows(MissingParameterException.class, () -> restaurantService.createRestaurant(restaurantDto.owner, restaurantDto));
    }

    @Test
    public void givenCreateRestaurant_whenHoraireOuvertureIsNull_thenParameterManquantExceptionIsThrown() {
        restaurantDto.hours.open = null;

        assertThrows(MissingParameterException.class, () -> restaurantService.createRestaurant(restaurantDto.owner, restaurantDto));
    }

    @Test
    public void givenGetRestaurants_whenParameterAreValid_thenListRestaurantsIsReturned() {
        String proprietaireCible = restaurantDto.owner.id;
        restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);
        restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);
        restaurantDto.owner.id = "12345";
        restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);

        assertEquals(2, restaurantService.getRestaurants(proprietaireCible).size());
    }



    @Test
    public void givenSearchRestaurants_whenNoParameterAreSpecified_thenReturnAllRestaurants() {
        restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);
        restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);
        restaurantDto.id = "12345";
        restaurantDto.name = "Dejeuner";
        restaurantDto.hours = new HourDto("11:00:00","19:00:00");
        restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);

        assertEquals(3, restaurantService.searchRestaurants(new RestaurantDto()).size());
    }

    @Test
    public void givenSearchRestaurants_withParamaterName_thenReturnOnlyMatchingRestaurants() {
        restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);
        restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);
        restaurantDto.id = "12345";
        restaurantDto.name = "Dejeuner";
        restaurantDto.hours.close = "18:00:00";
        restaurantDto.hours.open = "13:00:00";
        restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);

        RestaurantDto searchParams = new RestaurantDto();
        searchParams.name = "Pizz";
        assertEquals(2, restaurantService.searchRestaurants(searchParams).size());

    }

    @Test
    public void givenDeleteRestaurant_shouldDeleteRestaurant_whenRestaurantExists() {
        String restaurantId = restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);
        boolean isDeleted = restaurantService.deleteRestaurant(restaurantId);

        assertTrue(isDeleted, "Should be able to delete restaurant");
    }

}
