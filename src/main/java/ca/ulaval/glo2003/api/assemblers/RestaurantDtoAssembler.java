package ca.ulaval.glo2003.api.assemblers;

import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;

import java.util.Map;
import java.util.Objects;

public class RestaurantDtoAssembler {
    public Map<String, Object> versJson(RestaurantDto restaurantDto) {
        return Map.of(
                "id", Objects.toString(restaurantDto.id, ""),
                "name", restaurantDto.nom,
                "capacity", restaurantDto.capacity,
                "hours", Map.of(
                        "open", restaurantDto.hoursOpen,
                        "close", restaurantDto.hoursClose
                        ),
                "owner", Map.of(
                        "id", restaurantDto.owner.id
                ),
                "reservation", Map.of(
                        "duration", restaurantDto.configReservation.duration
                )
        );
    }

}
