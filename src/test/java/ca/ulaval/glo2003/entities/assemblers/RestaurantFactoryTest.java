package ca.ulaval.glo2003.entities.assemblers;


import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.HourDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
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

    private OwnerFactory proprietaireFactory;

    private RestaurantFactory restaurantFactory;

    @BeforeEach
    public void setUp() {
        proprietaireFactory = new OwnerFactory();
        restaurantFactory = new RestaurantFactory();

        restaurantDto = new RestaurantDto();
        restaurantDto.owner = new OwnerDto();
        restaurantDto.reservation = new ConfigReservationDto();
        restaurantDto.name = "Pizz";
        restaurantDto.capacity = 2;
        restaurantDto.hours = new HourDto("10:00:00","19:00:00");
        restaurantDto.reservation.duration = 90;
        restaurantDto.owner.id = "1";

        proprietaire = proprietaireFactory.createProprietaire("1");

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
}
