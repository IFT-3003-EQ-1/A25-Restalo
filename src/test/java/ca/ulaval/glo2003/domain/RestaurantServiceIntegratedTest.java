package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.HourDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.restaurant.OwnerFactory;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.entities.exceptions.ForbiddenAccessException;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import ca.ulaval.glo2003.entities.filters.FilterRestaurantFactory;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryReservationRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantServiceIntegratedTest {

    private RestaurantService restaurantService;

    private RestaurantDto restaurantDto;

    private OwnerDto ownerDto;

    @BeforeEach
    public void setUp() {
        restaurantService = new RestaurantService(
                new RestaurantFactory(),
                new InMemoryRestaurantRepository(),
                new InMemoryReservationRepository(),
                new OwnerFactory(),
                new RestaurantAssembler(),
                new FilterRestaurantFactory()
        );

        ownerDto = new OwnerDto();
        ownerDto.id = "1";

        restaurantDto = new RestaurantDto();
        restaurantDto.owner = ownerDto;
        restaurantDto.name = "Pizz";
        restaurantDto.hours = new HourDto("11:00:00","19:00:00");
        restaurantDto.capacity = 2;
        restaurantDto.reservation = new ConfigReservationDto();
        restaurantDto.reservation.duration = 60;
    }

    @Test
    public void givenCreateRestaurant_whenParameterAreValide_thenRestaurantIsCreated() {
        String restaurantId = restaurantService.createRestaurant(ownerDto, restaurantDto);

        assertNotNull(restaurantId);
    }

    @Test
    public void givenCreateRestaurant_whenProprietaireIsNull_thenParameterManquantExceptionIsThrown() {
        restaurantDto.owner.id = null;

        assertThrows(MissingParameterException.class, () -> restaurantService.createRestaurant(ownerDto, restaurantDto));
    }

    @Test
    public void givenCreateRestaurant_whenNomIsNull_thenParameterManquantExceptionIsThrown() {
        restaurantDto.name = null;

        assertThrows(MissingParameterException.class, () -> restaurantService.createRestaurant(ownerDto, restaurantDto));
    }

    @Test
    public void givenCreateRestaurant_whenHoraireFermetureIsNull_thenParameterManquantExceptionIsThrown() {
        restaurantDto.hours.close = null;

        assertThrows(MissingParameterException.class, () -> restaurantService.createRestaurant(ownerDto, restaurantDto));
    }

    @Test
    public void givenCreateRestaurant_whenHoraireOuvertureIsNull_thenParameterManquantExceptionIsThrown() {
        restaurantDto.hours.open = null;

        assertThrows(MissingParameterException.class, () -> restaurantService.createRestaurant(ownerDto, restaurantDto));
    }

    @Test
    public void givenGetRestaurants_whenParameterAreValid_thenListRestaurantsIsReturned() {
        String proprietaireCible = ownerDto.id;
        restaurantService.createRestaurant(ownerDto, restaurantDto);
        restaurantService.createRestaurant(ownerDto, restaurantDto);
        ownerDto.id = "12345";
        restaurantService.createRestaurant(ownerDto, restaurantDto);

        assertEquals(2, restaurantService.getRestaurants(proprietaireCible).size());
    }

    @Test
    public void givenGetRestaurant_whenParameterAreValid_thenRestaurantIsReturned() {
        restaurantDto.id = restaurantService.createRestaurant(ownerDto, restaurantDto);
        RestaurantDto dto = restaurantService.getRestaurant(restaurantDto.id, ownerDto.id);
        assertEquals(restaurantDto.id , dto.id);
    }

    @Test
    public void givenGetRestaurant_whenProprietaireIdIsInvalid_thenAccessInterditExceptionIsThrown() {
        restaurantDto.id = restaurantService.createRestaurant(ownerDto, restaurantDto);
        assertThrows(ForbiddenAccessException.class, () -> restaurantService.getRestaurant(restaurantDto.id, "1234546"));
    }

    @Test
    public void givenGetRestaurant_whenRestaurantDoesntExist_thenNotFoundExceptionIsThrown() {
        restaurantService.createRestaurant(ownerDto, restaurantDto);
        restaurantDto.id = "12345";
        assertThrows(NotFoundException.class, () -> restaurantService.getRestaurant(restaurantDto.id, ownerDto.id));
    }

    @Test
    public void givenSearchRestaurants_whenNoParameterAreSpecified_thenReturnAllRestaurants() {
        restaurantService.createRestaurant(ownerDto, restaurantDto);
        restaurantService.createRestaurant(ownerDto, restaurantDto);
        restaurantDto.id = "12345";
        restaurantDto.name = "Dejeuner";
        restaurantDto.hours = new HourDto("11:00:00","19:00:00");
        restaurantService.createRestaurant(ownerDto, restaurantDto);

        assertEquals(3, restaurantService.searchRestaurants(new RestaurantDto()).size());
    }

    @Test
    public void givenSearchRestaurants_withParamaterName_thenReturnOnlyMatchingRestaurants() {
        restaurantService.createRestaurant(ownerDto, restaurantDto);
        restaurantService.createRestaurant(ownerDto, restaurantDto);
        restaurantDto.id = "12345";
        restaurantDto.name = "Dejeuner";
        restaurantDto.hours.close = "18:00:00";
        restaurantDto.hours.open = "13:00:00";
        restaurantService.createRestaurant(ownerDto, restaurantDto);

        RestaurantDto searchParams = new RestaurantDto();
        searchParams.name = "Pizz";
        assertEquals(2, restaurantService.searchRestaurants(searchParams).size());

    }

    @Test
    public void givenDeleteRestaurant_shouldDeleteRestaurant_whenRestaurantExists() {
        boolean isDeleted = restaurantService.deleteRestaurant(restaurantDto.id, restaurantDto.owner.id);

        assertTrue(isDeleted, "Should be able to delete restaurant");
    }

    @Test
    public void givenDeleteRestaurant_shouldThrowException_whenOwnerDoesntExist() {

        assertThrows(ForbiddenAccessException.class,
                () -> restaurantService.deleteRestaurant(restaurantDto.id, "12345"));
    }

    @Test
    public void givenDeleteRestaurant_shouldThrowException_whenReservationDoesNotExist() {

        assertThrows(NotFoundException.class,
                () -> restaurantService.deleteRestaurant("12345", restaurantDto.owner.id));
    }
}
