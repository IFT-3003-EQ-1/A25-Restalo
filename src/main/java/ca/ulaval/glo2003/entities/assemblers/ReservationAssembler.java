package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;

public class ReservationAssembler {
    public ReservationDto toDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
                       reservationDto.number = reservation.getNumber();
                       reservationDto.date = reservation.getDate();
                       reservationDto.customer = reservation.getCustomer();
                       reservationDto.restaurant = new RestaurantAssembler().toPartialDto(reservation.getRestaurant());
                       reservationDto.startTime= reservation.getTime().getStart();
                       return reservationDto;
    }

}
