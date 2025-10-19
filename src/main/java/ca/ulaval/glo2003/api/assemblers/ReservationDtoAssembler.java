package ca.ulaval.glo2003.api.assemblers;

import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.RestaurantDto;

import java.util.Map;
import java.util.Objects;

public class ReservationDtoAssembler {
    public Map<String, Object> toJson(ReservationDto reservationDto) {
        return Map.of(
                "number", Objects.toString(reservationDto.getNumber(), ""),
                "date", reservationDto.getDate(),
                "time", reservationDto.getTime(),
                "groupSize", reservationDto.getGroupSize(),
                "customer", reservationDto.getCustomer(),
                "restaurant",reservationDto.getRestaurant()
        );
    }
}
