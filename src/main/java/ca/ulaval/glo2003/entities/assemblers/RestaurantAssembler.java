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
                new OwnerDto(restaurant.getOwner().getId()),
                restaurant.getName(),
                restaurant.getHoursOpen(),
                restaurant.getHoursClose(),
                restaurant.getCapacity(),
                toDto(restaurant.getConfigReservation())
        );
    }

    private ConfigReservationDto toDto(ConfigReservation configReservation) {
        return new ConfigReservationDto(configReservation.getDuration());
    }
}
