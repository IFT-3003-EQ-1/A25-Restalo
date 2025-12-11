package ca.ulaval.glo2003.entities.assemblers;


import ca.ulaval.glo2003.domain.dtos.SalesDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.HourDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.Sales;
import ca.ulaval.glo2003.entities.restaurant.Owner;
import ca.ulaval.glo2003.entities.restaurant.OwnerFactory;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantFactoryTest {

    private RestaurantDto restaurantDto;

    private Owner proprietaire;

    private RestaurantFactory restaurantFactory;

    @BeforeEach
    public void setUp() {
        OwnerFactory proprietaireFactory = new OwnerFactory();
        restaurantFactory = new RestaurantFactory();

        restaurantDto = new RestaurantDto();
        restaurantDto.owner = new OwnerDto();
        restaurantDto.reservation = new ConfigReservationDto();
        restaurantDto.name = "Pizz";
        restaurantDto.capacity = 2;
        restaurantDto.hours = new HourDto("10:00:00","19:00:00");
        restaurantDto.reservation.duration = 90;
        restaurantDto.owner.id = "1";

        proprietaire = proprietaireFactory.createOwner("1");

    }

    @Test
    public void givenCreateRestaurant_whenParametersValide_thenRestaurantIsReturned() {
        Restaurant restaurant = restaurantFactory.createRestaurant(
            proprietaire, restaurantDto
        );

        assertNotNull(restaurant);
        assertEquals(restaurantDto.name, restaurant.getName());
        assertEquals(restaurantDto.capacity, restaurant.getCapacity());
        assertEquals(restaurantDto.owner.id, restaurant.getOwner().getId());
        assertEquals(restaurantDto.reservation.duration, restaurant.getConfigReservation().getDuration());
    }

    @Test
    public void givenCreateRestaurant_whenNomNull_thenParametreManquantExceptionIsThrown() {
        restaurantDto.name = null;

        assertThrows(MissingParameterException.class, () -> restaurantFactory.createRestaurant(
                proprietaire,
                restaurantDto));
    }

    @Test
    public void givenCreateRestaurant_whenCapaciteIsZero_thenInvalidIsThrown() {
        restaurantDto.capacity = 0;
        assertThrows(InvalideParameterException.class, () -> restaurantFactory.createRestaurant(
                proprietaire,
                restaurantDto
        ));
    }

    @Test
    public void givenCreateRestaurant_whenHoraireOuvertureIsBiggerThanHoraireFermeture_thenParameterInvalidIsThrown() {
        restaurantDto.hours.open = "20:00:00";
        restaurantDto.hours.close = "19:00:00";
        assertThrows(InvalideParameterException.class, () -> restaurantFactory.createRestaurant(
                proprietaire,
                restaurantDto
        ));

    }

    @Test
    public void givenCreateRestaurant_whenConfigReservationIsNull_thenDefaultConfigurationIsUsed() {
        restaurantDto.reservation = null;
        Restaurant restaurant = restaurantFactory.createRestaurant(proprietaire, restaurantDto);

        assertEquals(RestaurantFactory.DEFAULT_RESERVATION_DURATION, restaurant.getConfigReservation().getDuration());
    }
    
    @Test
    public void givenCreateSalesReport_whenParamatersAreValid_thenReturnId() {
        SalesDto salesDto = new SalesDto(
                null,
                "2014-04-04",
                300.0F,
                restaurantDto.id
        );
        Sales sales = restaurantFactory.createSalesReport(salesDto, restaurantFactory.fromDto(restaurantDto));

        assertEquals(salesDto.date, sales.getDate());
        assertEquals(salesDto.salesAmount, sales.getSalesAmount());
        assertEquals(salesDto.restaurantId, sales.getRestaurant().getId());
    }

    @Test
    public void givenCreateSalesReport_whenDateIsNotValid_thenThrowInvalidParameterException() {
        SalesDto salesDto = new SalesDto(
                null,
                "04-04-2014",
                300.0F,
                restaurantDto.id
        );
        assertThrows(InvalideParameterException.class, () -> restaurantFactory.createSalesReport(salesDto, restaurantFactory.fromDto(restaurantDto)));
    }

    @Test
    public void givenCreateSalesReport_whenDateIsMissing_thenThrowMissingParameterException() {
        SalesDto salesDto = new SalesDto(
                null,
                null,
                300.0F,
                restaurantDto.id
        );
        assertThrows(MissingParameterException.class, () -> restaurantFactory.createSalesReport(salesDto, restaurantFactory.fromDto(restaurantDto)));

    }

}
