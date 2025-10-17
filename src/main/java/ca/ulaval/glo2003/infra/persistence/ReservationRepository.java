package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.entities.Reservation;

import java.util.Optional;

public interface ReservationRepository {
    void save(Reservation reservation);

    Optional<Reservation> get(String id);
}
