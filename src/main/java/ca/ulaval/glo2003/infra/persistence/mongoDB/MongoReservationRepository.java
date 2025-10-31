package ca.ulaval.glo2003.infra.persistence.mongoDB;

import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.infra.persistence.ReservationRepository;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.util.Optional;

public class MongoReservationRepository implements ReservationRepository {
    private final Datastore datastore;

    public MongoReservationRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public void save(Reservation reservation) {
        datastore.save(reservation);
    }

    @Override
    public Optional<Reservation> get(String id) {
       Reservation reservation = datastore.find(Reservation.class)
               .filter(Filters.eq("id", id))
               .first();
       return Optional.ofNullable(reservation);
    }
}
