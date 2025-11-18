package ca.ulaval.glo2003.api.assemblers;

import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.ReservationTimeDto;

import java.util.Map;
import java.util.Objects;

public class ReservationDtoAssembler {
    public Map<String, Object> toJson(ReservationDto reservationDto) {
        return Map.of(
                "number", Objects.toString(reservationDto.number, ""),
                "date", reservationDto.date,
                "time", toJson(reservationDto.time),
                "groupSize", reservationDto.groupSize,
                "customer", toJson(reservationDto.customer),
                "restaurant",reservationDto.restaurant
        );
    }

    private Map<String, Object> toJson(CustomerDto customerDto) {
        return Map.of(
                "name", customerDto.name,
                "email", customerDto.email,
                "phoneNumber", customerDto.phoneNumber
        );
    }

    private Map<String, Object> toJson(ReservationTimeDto reservationTimeDto) {
        return Map.of(
                "start", reservationTimeDto.start,
                "end", reservationTimeDto.end
        );
    }
}
