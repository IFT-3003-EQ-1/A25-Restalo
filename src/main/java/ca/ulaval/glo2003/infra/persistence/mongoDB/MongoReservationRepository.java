package ca.ulaval.glo2003.infra.persistence.mongoDB;

import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.ReservationRepository;
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
    public Optional<Reservation> get(String number) {
       Reservation reservation = datastore.find(Reservation.class)
               .filter(Filters.eq("number", number))
               .first();
       return Optional.ofNullable(reservation);
    }

    @Override
    public boolean deleteRelatedReservations(String restaurantId) {
        return datastore.find(Reservation.class)
                .filter(Filters.eq("restaurantId", restaurantId))
                .delete()
                .getDeletedCount() != 0;
    }

    @Override
    public boolean delete(String number) {
        return datastore.find(Reservation.class)
                .filter(Filters.eq("number", number))
                .delete()
                .getDeletedCount() != 0;
    }
}
