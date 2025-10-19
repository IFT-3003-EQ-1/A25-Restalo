package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.entities.reservation.Reservation;

import java.util.*;

public class InMemoryReservationRepository implements ReservationRepository {
    private final Map<String, Reservation> database;

    public InMemoryReservationRepository() {
        database = new HashMap<>();
    }

    public InMemoryReservationRepository(Map<String, Reservation> database) {
        this.database = database;
    }

    @Override
    public void save(Reservation reservation) {
        this.database.put(reservation.getNumber(), reservation);
    }

    @Override
    public Optional<Reservation> get(String id) {
        return Optional.ofNullable(database.get(id));
    }
}
