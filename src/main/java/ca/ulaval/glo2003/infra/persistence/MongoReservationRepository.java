package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.entities.reservation.Reservation;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.util.Optional;

public class MongoReservationRepository implements ReservationRepository {
    private final Datastore datastore;
    public MongoReservationRepository() {
        datastore = MongoDBConnection.getInstance().getDatastore();
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
