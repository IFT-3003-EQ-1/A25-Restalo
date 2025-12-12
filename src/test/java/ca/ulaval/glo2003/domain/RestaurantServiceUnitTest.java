package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.SalesDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.Sales;
import ca.ulaval.glo2003.entities.SalesRepository;
import ca.ulaval.glo2003.entities.reservation.ReservationRepository;
import ca.ulaval.glo2003.entities.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.filters.FilterRestaurantFactory;
import ca.ulaval.glo2003.entities.restaurant.*;
import com.google.common.base.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @Mock
    private SalesRepository salesRepository;

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
                salesRepository,
                ownerFactory,
                restaurantAssembler,
                filterFactory
        );

        restaurantDto = DomainTestUtils.getRestaurantDto();
        restaurant = DomainTestUtils.getRestaurant();

        ownerDto = new OwnerDto(restaurantDto.owner.id);
        owner = new Owner(restaurant.getOwner().getId());

    }

    @Test
    public void givenCreateRestaurant_whenValidParameters_thenReturnRestaurantId() {
        when(ownerFactory.createOwner(ownerDto.id)).thenReturn(owner);
        when(restaurantFactory.createRestaurant(owner, restaurantDto)).thenReturn(restaurant);

        String restaurantId = restaurantService.createRestaurant(ownerDto, restaurantDto);


        assertFalse(Strings.isNullOrEmpty(restaurantId));
    }

    @Test
    public void givenDeleteRestaurant_whenValidParameters_thenReturnTrue() {
        String restaurantId = restaurantDto.id;

        when(restaurantRepository.delete(restaurantId)).thenReturn(true);

        assertTrue(restaurantService.deleteRestaurant(restaurantId));
    }

    @Test
    public void givenCreateSalesReport_whenValidParameters_thenReturnId() {
        SalesDto salesDto = new SalesDto(
                null,
                "2014-04-04",
                300.0F,
                restaurantDto.id
        );

        when(restaurantFactory.createSalesReport(any(), any())).thenReturn(new Sales(
                "1",
                salesDto.date,
                salesDto.salesAmount,
                new Restaurant()
        ));

        String id = restaurantService.createSalesReport(salesDto, restaurantDto);
        assertEquals(restaurantDto.id, id);
    }
}
