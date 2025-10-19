package ca.ulaval.glo2003.api.assemblers;

import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;

import java.util.Map;
import java.util.Objects;

public class RestaurantDtoAssembler {
    public Map<String, Object> versJson(RestaurantDto restaurantDto) {
        return Map.of(
                "id", Objects.toString(restaurantDto.id, ""),
                "owner", Map.of(
                        "id", restaurantDto.owner.id
                        ),
                "name", restaurantDto.name,
                "capacity", restaurantDto.capacity,
                "hoursOpen", restaurantDto.hoursOpen,
                "hoursClose", restaurantDto.hoursClose,
                "reservation", Map.of(
                        "duration", restaurantDto.configReservation.duration
                )
        );
    }

}
