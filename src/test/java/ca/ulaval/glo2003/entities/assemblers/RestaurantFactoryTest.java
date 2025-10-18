package ca.ulaval.glo2003.entities.assemblers;


import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.ProprietaireDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.restaurant.Proprietaire;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.exceptions.ParametreInvalideException;
import ca.ulaval.glo2003.entities.exceptions.ParametreManquantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantFactoryTest {

    private RestaurantDto restaurantDto;

    private Proprietaire proprietaire;

    private ProprietaireFactory proprietaireFactory;

    private RestaurantFactory restaurantFactory;

    @BeforeEach
    public void setUp() {
        proprietaireFactory = new ProprietaireFactory();
        restaurantFactory = new RestaurantFactory();

        restaurantDto = new RestaurantDto();
        restaurantDto.proprietaire = new ProprietaireDto();
        restaurantDto.configReservation = new ConfigReservationDto();
        restaurantDto.nom = "Pizz";
        restaurantDto.capacite = 2;
        restaurantDto.horaireOuverture = "10:00:00";
        restaurantDto.horaireFermeture = "19:00:00";
        restaurantDto.configReservation.duration = 90;
        restaurantDto.proprietaire.id = "1";

        proprietaire = proprietaireFactory.createProprietaire("1");

    }

    @Test
    public void givenCreateRestaurant_whenParametersValide_thenRestaurantIsReturned() {
        Restaurant restaurant = restaurantFactory.createRestaurant(
            proprietaire, restaurantDto
        );

        assertNotNull(restaurant);
        assertEquals(restaurantDto.nom, restaurant.getNom());
        assertEquals(restaurantDto.capacite, restaurant.getCapacite());
        assertEquals(restaurantDto.horaireOuverture, restaurant.getHoraireOuverture());
        assertEquals(restaurantDto.horaireFermeture, restaurant.getHoraireFermeture());
        assertEquals(restaurantDto.proprietaire.id, restaurant.getProprietaire().getId());
        assertEquals(restaurantDto.configReservation.duration, restaurant.getConfigReservation().getDuration());
    }

    @Test
    public void givenCreateRestaurant_whenNomNull_thenParametreManquantExceptionIsThrown() {
        restaurantDto.nom = null;

        assertThrows(ParametreManquantException.class, () -> restaurantFactory.createRestaurant(
                proprietaire,
                restaurantDto));
    }

    @Test
    public void givenCreateRestaurant_whenCapaciteIsZero_thenInvalidIsThrown() {
        assertThrows(ParametreInvalideException.class, () -> restaurantFactory.createRestaurant(
                proprietaire,
                restaurantDto
        ));
    }

    @Test
    public void givenCreateRestaurant_whenHoraireOuvertureIsBiggerThanHoraireFermeture_thenParameterInvalidIsThrown() {
        assertThrows(ParametreInvalideException.class, () -> restaurantFactory.createRestaurant(
                proprietaire,
                restaurantDto
        ));

    }

    @Test
    public void givenCreateRestaurant_whenConfigReservationIsNull_thenDefaultConfigurationIsUsed() {
        restaurantDto.configReservation = null;
        Restaurant restaurant = restaurantFactory.createRestaurant(proprietaire, restaurantDto);

        assertEquals(RestaurantFactory.DEFAULT_RESERVATION_DURATION, restaurant.getConfigReservation().getDuration());
    }
}
