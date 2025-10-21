package ca.ulaval.glo2003.api.assemblers;

import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;

import java.util.Map;
import java.util.Objects;

public class RestaurantDtoAssembler {
    public Map<String, Object> toJson(RestaurantDto restaurantDto) {
        return Map.of(
                "id", Objects.toString(restaurantDto.id, ""),
                "name", restaurantDto.name,
                "capacity", restaurantDto.capacity,
                "hours", restaurantDto.hours,
                "reservation", Map.of(
                        "duration", restaurantDto.reservation.duration
                )
        );
    }

}
