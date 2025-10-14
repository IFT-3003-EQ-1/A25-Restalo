package ca.ulaval.glo2003.restaurants.service;

import ca.ulaval.glo2003.restaurants.RestaurantRepository;
import ca.ulaval.glo2003.restaurants.domain.Reservation;

public class ReservationService {
    public Reservation getReservation(String reservationId){
        return RestaurantRepository.findReservationById(reservationId);
    }
}
