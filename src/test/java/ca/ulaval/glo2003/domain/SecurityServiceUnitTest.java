package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.HourDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.restaurant.*;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceUnitTest {

    private SecurityService securityService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantAssembler restaurantAssembler;

    private RestaurantDto restaurantDto;

    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {

        securityService = new SecurityService(
                restaurantRepository,
                restaurantAssembler
        );

        restaurantDto = DomainTestUtils.getRestaurantDto();
        restaurant = DomainTestUtils.getRestaurant();

    }

    @Test
    public void givenGetRestaurant_whenValidParameters_thenReturnRestaurant() {
        String restaurantId = restaurantDto.id;
        String ownerId = restaurantDto.owner.id;

        when(restaurantRepository.get(restaurantId)).thenReturn(Optional.of(restaurant));
        when(restaurantAssembler.toDto(restaurant)).thenReturn(restaurantDto);


        RestaurantDto returnedVal = securityService.getRestaurant(restaurantId, ownerId);
        assertEquals(restaurantDto, returnedVal);
    }

    @Test
    public void givenGetRestaurant_whenOwnerIdIsNull_thenThrowMissingParameterException() {
        String restaurantId = restaurantDto.id;
        String ownerId = null;

        assertThrows(MissingParameterException.class, () -> securityService.getRestaurant(restaurantId, ownerId));
    }

    @Test
    public void givenGetRestaurant_whenRestaurantIdDoesntExist_thenThrowNotFoundException() {
        String restaurantId = "-1";
        String ownerId = restaurantDto.owner.id;

        when(restaurantRepository.get(restaurantId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> securityService.getRestaurant(restaurantId, ownerId));
    }
}
