package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.HourDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.ReservationRepository;
import ca.ulaval.glo2003.entities.RestaurantRepository;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.filters.FilterRestaurantFactory;
import ca.ulaval.glo2003.entities.restaurant.*;
import com.google.common.base.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceUnitTest {


    private RestaurantService restaurantService;

    @Mock
    private RestaurantFactory restaurantFactory;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private OwnerFactory ownerFactory;

    @Mock
    private RestaurantAssembler restaurantAssembler;

    @Mock
    private FilterRestaurantFactory filterFactory;

    @Mock
    private ReservationRepository reservationRepository;

    private RestaurantDto  restaurantDto;

    private OwnerDto ownerDto;

    private Restaurant restaurant;

    private Owner owner;

    @BeforeEach
    public void setUp() {
        restaurantService = new RestaurantService(
                restaurantFactory,
                restaurantRepository,
                reservationRepository,
                ownerFactory,
                restaurantAssembler,
                filterFactory
        );

        restaurantDto = new RestaurantDto(
                "1",
                ownerDto,
                "Pizz",
                new HourDto(),
                4,
                new ConfigReservationDto()
        );
        ownerDto = new OwnerDto("1");
        owner = new Owner("1");
        restaurant = new Restaurant(
                "i",
                owner,
                "Pizz",
                4,
                new Hours(),
                new ConfigReservation()
        );
    }

    @Test
    public void givenCreateRestaurant_whenValidParameters_thenReturnRestaurantId() {
        when(ownerFactory.createOwner(ownerDto.id)).thenReturn(owner);
        when(restaurantFactory.createRestaurant(owner, restaurantDto)).thenReturn(restaurant);

        String restaurantId = restaurantService.createRestaurant(ownerDto, restaurantDto);


        assertFalse(Strings.isNullOrEmpty(restaurantId));
    }

    @Test
    public void givenGetRestaurant_whenValidParameters_thenReturnRestaurant() {
        String restaurantId = restaurantDto.id;
        String ownerId = ownerDto.id;

        when(restaurantRepository.get(restaurantId)).thenReturn(Optional.of(restaurant));
        when(restaurantAssembler.toDto(restaurant)).thenReturn(restaurantDto);

        RestaurantDto returnedVal = restaurantService.getRestaurant(restaurantId, ownerId);
        assertEquals(restaurantDto, returnedVal);
    }

    @Test
    public void givenGetRestaurant_whenOwnerIdIsNull_thenThrowMissingParameterException() {
        String restaurantId = restaurantDto.id;
        String ownerId = null;

        assertThrows(MissingParameterException.class, () -> restaurantService.getRestaurant(restaurantId, ownerId));
    }

    @Test
    public void givenGetRestaurant_whenRestaurantIdDOesntExist_thenThrowNotFoundException() {
        String restaurantId = "-1";
        String ownerId = ownerDto.id;

        assertThrows(NotFoundException.class, () -> restaurantService.getRestaurant(restaurantId, ownerId));
    }

    @Test
    public void givenDeleteRestaurant_whenValidParameters_thenReturnTrue() {
        String restaurantId = restaurantDto.id;
        String ownerId = ownerDto.id;

        when(restaurantRepository.get(restaurantId)).thenReturn(Optional.of(restaurant));
        when(restaurantRepository.delete(restaurantId)).thenReturn(true);

        assertTrue(restaurantService.deleteRestaurant(restaurantId, ownerId));
    }

    @Test
    public void givenDeleteRestaurant_whenRestaurantIdIsNull_thenThrowNotFoundException() {
        String restaurantId = null;
        String ownerId = ownerDto.id;

        assertThrows(NotFoundException.class, ()-> restaurantService.deleteRestaurant(restaurantId, ownerId));
    }

    @Test
    public void givenDeleteRestaurant_whenOwnerIdIsNull_thenThrowMissingParameterException() {
        String restaurantId = restaurantDto.id;
        String ownerId = null;

        assertThrows(MissingParameterException.class, ()-> restaurantService.deleteRestaurant(restaurantId, ownerId));
    }

    @Test
    public void givenDeleteRestaurant_whenOwnerIdDoesntMatch_thenThrowForbiddenAcessException() {
        String restaurantId = restaurantDto.id;
        String ownerId = "123";

        assertThrows(NotFoundException.class, ()-> restaurantService.deleteRestaurant(restaurantId, ownerId));

    }







}
