package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.exceptions.ForbiddenAccessException;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.filters.FilterRestaurantFactory;
import ca.ulaval.glo2003.entities.restaurant.OwnerFactory;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.entities.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryReservationRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemorySalesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SecurityServiceIntegratedTest {

    private RestaurantDto restaurantDto;

    private RestaurantService restaurantService;

    private SecurityService securityService;

    @BeforeEach
    public void setUp() {
        restaurantDto = DomainTestUtils.getRestaurantDto();
        final RestaurantRepository restaurantRepository  = new InMemoryRestaurantRepository();

        securityService  = new SecurityService(
            restaurantRepository,
            new RestaurantAssembler()
        );

        restaurantService = new RestaurantService(
                new RestaurantFactory(),
                restaurantRepository,
                new InMemoryReservationRepository(),
                new InMemorySalesRepository(),
                new OwnerFactory(),
                new RestaurantAssembler(),
                new FilterRestaurantFactory()
        );
    }


    @Test
    public void givenGetRestaurant_whenParameterAreValid_thenRestaurantIsReturned() {
        restaurantDto.id = restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);
        RestaurantDto dto = securityService.getRestaurant(restaurantDto.id, restaurantDto.owner.id);
        assertEquals(restaurantDto.id , dto.id);
    }

    @Test
    public void givenGetRestaurant_whenProprietaireIdIsInvalid_thenAccessInterditExceptionIsThrown() {
        restaurantDto.id = restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);
        assertThrows(ForbiddenAccessException.class, () -> securityService.getRestaurant(restaurantDto.id, "1234546"));
    }

    @Test
    public void givenGetRestaurant_whenRestaurantDoesntExist_thenNotFoundExceptionIsThrown() {
        restaurantService.createRestaurant(restaurantDto.owner, restaurantDto);
        restaurantDto.id = "12345";
        assertThrows(NotFoundException.class, () -> securityService.getRestaurant(restaurantDto.id, restaurantDto.owner.id));
    }
}
