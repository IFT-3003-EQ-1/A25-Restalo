package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.entities.reservation.Reservation;

import java.util.Optional;

public interface ReservationRepository {
    void save(Reservation reservation);

    Optional<Reservation> get(String id);
    
    boolean deleteRelatedReservations(String restaurantId);

    boolean delete(String id);
}
