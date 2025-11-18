package ca.ulaval.glo2003.infra.persistence.inMemory;

import ca.ulaval.glo2003.domain.dtos.restaurant.ReservationSearch;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.ReservationRepository;

import java.util.*;
import java.util.stream.Collectors;

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
    public Optional<List<Reservation>> search(ReservationSearch searchDto) {
        return Optional.of(database.values().stream()
                .filter(reservation -> matchesSearchCriteria(reservation, searchDto))
                .collect(Collectors.toList()));
    }

    private boolean matchesSearchCriteria(Reservation reservation, ReservationSearch searchDto) {
        if (searchDto.restaurantId != null) {
            if (reservation.getRestaurant() == null ||
                    !searchDto.restaurantId.equals(reservation.getRestaurant().getId())) {
                return false;
            }
        }

        if (searchDto.ownerId != null) {
            if (reservation.getRestaurant() == null ||
                    !searchDto.ownerId.equals(reservation.getRestaurant().getOwner().getId())) {
                return false;
            }
        }

        if (searchDto.customerName != null) {
            if (reservation.getCustomer() == null ||
                    !reservation.getCustomer().getName().toLowerCase().startsWith(searchDto.customerName.toLowerCase())) {
                return false;
            }
        }

        if (searchDto.reservationDate != null) {
            if (!searchDto.reservationDate.equals(reservation.getDate())) {
                return false;
            }
        }
        return true;
    }
}
