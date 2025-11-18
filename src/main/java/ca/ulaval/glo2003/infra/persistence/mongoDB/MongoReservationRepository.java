package ca.ulaval.glo2003.infra.persistence.mongoDB;

import ca.ulaval.glo2003.domain.dtos.restaurant.ReservationSearch;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.ReservationRepository;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
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
    public Optional<List<Reservation>> search(ReservationSearch searchDto) {
        Query<Reservation> query = datastore.find(Reservation.class);

        if (searchDto.restaurantId != null) {
            query.filter(Filters.eq("restaurant.id", searchDto.restaurantId));
        }

        if (searchDto.ownerId != null) {
            query.filter(Filters.eq("restaurant.ownerId", searchDto.ownerId));
        }

        if (searchDto.customerName != null) {
            query.filter(Filters.eq("customer.name", searchDto.customerName));
        }

        if (searchDto.reservationDate != null) {
            query.filter(Filters.eq("date", searchDto.reservationDate));
        }

        return Optional.of(query.iterator().toList());
    }
}
