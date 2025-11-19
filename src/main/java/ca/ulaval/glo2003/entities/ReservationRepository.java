package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.entities.filters.Filter;
import ca.ulaval.glo2003.entities.reservation.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    void save(Reservation reservation);

    Optional<Reservation> get(String id);

    List<Reservation> getAll();

    List<Reservation> search(List<Filter<Reservation>> filters);

    boolean deleteRelatedReservations(String restaurantId);

    boolean delete(String id);
}
