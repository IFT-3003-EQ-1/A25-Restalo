package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.ReservationTimeDto;
import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.reservation.ReservationTime;

import javax.annotation.Nullable;

public class ReservationAssembler {
    public ReservationDto toDto(Reservation reservation) {
        ReservationTimeDto reservationTimeDto = toDto(reservation.getTime());

        ReservationDto reservationDto = new ReservationDto();
                       reservationDto.number = reservation.getNumber();
                       reservationDto.date = reservation.getDate();
                       reservationDto.customer = toDto(reservation.getCustomer());
                       reservationDto.restaurant = new RestaurantAssembler().toPartialDto(reservation.getRestaurant());
                       reservationDto.time = reservationTimeDto;

        return reservationDto;
    }

    private CustomerDto toDto(@Nullable Customer customer) {
        if (customer == null) return null;
        return new CustomerDto(customer.getName(), customer.getEmail(), customer.getPhoneNumber());
    }

    private ReservationTimeDto toDto(@Nullable ReservationTime reservationTime) {
        if (reservationTime == null) return new ReservationTimeDto();
        return new ReservationTimeDto(reservationTime.getStart(), reservationTime.getEnd());
    }

}
