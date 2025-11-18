package ca.ulaval.glo2003.infra.persistence.mongoDB;

import ca.ulaval.glo2003.entities.filters.Filter;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.ReservationRepository;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.util.List;
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

    @Override
    public List<Reservation> search(List<Filter<Reservation>> filters) {
        return datastore.find(Reservation.class).stream().filter(
                reservation -> filters.stream().anyMatch(
                        filter -> filter.filter(reservation)
                )
        ).toList();
    }

    @Override
    public boolean deleteRelatedReservations(String restaurantId) {
        return datastore.find(Reservation.class)
                .filter(Filters.eq("restaurantId", restaurantId))
                .delete()
                .getDeletedCount() != 0;
    }

    @Override
    public boolean delete(String id) {
        return datastore.find(Reservation.class)
                .filter(Filters.eq("id", id))
                .delete()
                .getDeletedCount() != 0;
    }
}
