package ca.ulaval.glo2003.entities.assemblers;


import ca.ulaval.glo2003.entities.Proprietaire;
import ca.ulaval.glo2003.entities.Restaurant;
import ca.ulaval.glo2003.entities.exceptions.ParametreInvalideException;
import ca.ulaval.glo2003.entities.exceptions.ParametreManquantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantFactoryTest {

    private Restaurant restaurant;

    private Proprietaire proprietaire;

    private RestaurantFactory restaurantFactory;

    @BeforeEach
    public void setUp() {
        proprietaire = new Proprietaire("1");
        restaurant = new Restaurant(
                "1",
                proprietaire,
                "Pizz",
                2,
                "10:00:00",
                "19:00:00"
        );
        restaurantFactory = new RestaurantFactory();
    }

    @Test
    public void givenCreateRestaurant_whenParametersValide_thenRestaurantIsReturned() {
        Restaurant restaurant = restaurantFactory.createRestaurant(
            proprietaire,
                "Pizz",
                2,
                "10:00:00",
                "19:00:00"
        );

        assertNotNull(restaurant);
    }

    @Test
    public void givenCreateRestaurant_whenNomNull_thenParametreManquantExceptionIsThrown() {
        assertThrows(ParametreManquantException.class, () -> restaurantFactory.createRestaurant(
                proprietaire,
                null,
                2,
                "10:00:00",
                "19:00:00"
        ));
    }

    @Test
    public void givenCreateRestaurant_whenCapaciteIsZero_thenInvalidIsThrown() {
        assertThrows(ParametreInvalideException.class, () -> restaurantFactory.createRestaurant(
                proprietaire,
                "Pizz",
                0,
                "10:00:00",
                "19:00:00"
        ));
    }

    @Test
    public void givenCreateRestaurant_whenHoraireOuvertureIsBiggerThanHoraireFermeture_thenParameterInvalidIsThrown() {
        assertThrows(ParametreInvalideException.class, () -> restaurantFactory.createRestaurant(
                proprietaire,
                "Pizz",
                0,
                "19:00:00",
                "10:00:00"
        ));

    }

}
