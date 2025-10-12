package ca.ulaval.glo2003.entities.assemblers;


import ca.ulaval.glo2003.entities.Restaurant;
import ca.ulaval.glo2003.entities.exceptions.ParametreInvalideException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantFactoryTest {

    private RestaurantFactory restaurantFactory;

    private ProprietaireFactory proprietaireFactory;

    @BeforeEach
    public void setUp() {
        restaurantFactory = new RestaurantFactory();
        proprietaireFactory = new ProprietaireFactory();
    }

    @Test
    public void givenCreateRestaurant_whenParametersValide_thenRestaurantIsReturned() {
        Restaurant restaurant = restaurantFactory.createRestaurant(
            proprietaireFactory.createProprietaire("1"),
                "Pizz",
                2,
                "10:00:00",
                "19:00:00"
        );

        assertNotNull(restaurant);
    }

    @Test
    public void givenCreateRestaurant_whenProprietaireNull_thenParametreInvalideIsThrown() {

        assertThrows(ParametreInvalideException.class, () -> restaurantFactory.createRestaurant(
                null,
                "Pizz",
                2,
                "10:00:00",
                "19:00:00"
        ));
    }

}
