package ca.ulaval.glo2003.persistence.infra;

import ca.ulaval.glo2003.entities.Proprietaire;
import ca.ulaval.glo2003.entities.Restaurant;

public class InMemoryRestaurantRepositoryTestUtils {

    public static Restaurant createRestaurant(String restaurantId) {
        return new Restaurant(
                restaurantId,
                new Proprietaire("1"),
                "Pizz",
                2,
                "11:00:00",
                "19:00:00"
        );
    }
}
