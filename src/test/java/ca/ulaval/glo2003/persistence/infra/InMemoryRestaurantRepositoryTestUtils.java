package ca.ulaval.glo2003.persistence.infra;

import ca.ulaval.glo2003.domain.dtos.restaurant.HourDto;
import ca.ulaval.glo2003.entities.restaurant.ConfigReservation;
import ca.ulaval.glo2003.entities.restaurant.Owner;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;

public class InMemoryRestaurantRepositoryTestUtils {

    public static Restaurant createRestaurant(String restaurantId) {
        return new Restaurant(
                restaurantId,
                new Owner("1"),
                "Pizz",
                2,
                new HourDto( "11:00:00",
                        "19:00:00"),
                new ConfigReservation(60)
        );
    }
}
