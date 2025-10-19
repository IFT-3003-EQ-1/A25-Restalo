package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.ProprietaireDto;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.RestaurantDto;
import ca.ulaval.glo2003.entities.Reservation;
import ca.ulaval.glo2003.entities.Restaurant;

public class ReservationAssembler {
    public ReservationDto toDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
                       reservationDto.setNumber(reservation.getNumber());
                       reservationDto.setDate(reservation.getDate());
                       reservationDto.setCustomer(reservation.getCustomer());
                       reservationDto.setRestaurant(new RestaurantAssembler().toDto(reservation.getRestaurant()));
                       reservationDto.setTime(reservation.getTime());
                       return reservationDto;
    }
}
