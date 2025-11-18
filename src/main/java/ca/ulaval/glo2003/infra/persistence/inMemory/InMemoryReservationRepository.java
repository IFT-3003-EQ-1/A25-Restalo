package ca.ulaval.glo2003.infra.persistence.inMemory;

import ca.ulaval.glo2003.entities.filters.Filter;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.ReservationRepository;

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

    @Override
    public List<Reservation> search(List<Filter<Reservation>> filters) {
        List<Reservation> restaurants = new ArrayList<>();
        database.values().forEach(r -> {
            boolean isValide = true;
            for (Filter<Reservation> filter : filters) {
                if(!filter.filter(r)) {
                    isValide = false;
                }
            }

            if (isValide) {
                restaurants.add(r);
            }
        });
        return restaurants;
    }

    @Override
    public boolean deleteRelatedReservations(String restaurantId) {
        return database.values().removeIf(reservation -> reservation.getRestaurant().getId().equals(restaurantId));
    }

    @Override
    public boolean delete(String number) {
        return database.remove(number) != null;
    }
}
