package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.restaurant.ConfigReservation;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;

public class RestaurantAssembler {
    public RestaurantDto toDto(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getId(),
                restaurant.getName(),
               restaurant.getHours(),
                restaurant.getCapacity(),
               toDto(restaurant.getConfigReservation())
        );
    }

    public RestaurantDto toPartialDto(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getHours(),
                restaurant.getCapacity()
        );
    }

    private ConfigReservationDto toDto(ConfigReservation configReservation) {
        return new ConfigReservationDto(configReservation.getDuration());
    }
}
