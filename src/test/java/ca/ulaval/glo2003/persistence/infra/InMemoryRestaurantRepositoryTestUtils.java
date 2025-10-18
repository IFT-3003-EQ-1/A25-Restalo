package ca.ulaval.glo2003.persistence.infra;

import ca.ulaval.glo2003.entities.restaurant.ConfigReservation;
import ca.ulaval.glo2003.entities.restaurant.Proprietaire;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;

public class InMemoryRestaurantRepositoryTestUtils {

    public static Restaurant createRestaurant(String restaurantId) {
        return new Restaurant(
                restaurantId,
                new Proprietaire("1"),
                "Pizz",
                2,
                "11:00:00",
                "19:00:00",
                new ConfigReservation(60)
        );
    }
}
