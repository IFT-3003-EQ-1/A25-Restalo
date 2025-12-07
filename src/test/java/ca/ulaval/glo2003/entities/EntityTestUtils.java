package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.entities.restaurant.ConfigReservation;
import ca.ulaval.glo2003.entities.restaurant.Hours;
import ca.ulaval.glo2003.entities.restaurant.Owner;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;

public class EntityTestUtils {


    public static Restaurant createRestaurant() {
        return new Restaurant(
                "restaurant-123",
                new Owner("1"),
                "Hot pizza",
                20,
                new Hours("10:00:00","22:00:00"),
                new ConfigReservation(60)
        );
    }
}
