package ca.ulaval.glo2003.persistence.infra.inMemory;

import ca.ulaval.glo2003.entities.ReservationRepository;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryReservationRepository;
import ca.ulaval.glo2003.persistence.infra.ReservationRepositoryTest;

import java.util.HashMap;
import java.util.Map;

public class InMemoryReservationRepositoryTest extends ReservationRepositoryTest {

    private InMemoryReservationRepository reservationRepository;

    private Map<String, Reservation> reservations;

    private Reservation reservation;

    private Restaurant restaurant;

    private final String RESTAURANT_ID = "1";

    @Override
    protected ReservationRepository getRepository() {
        Map<String, Reservation> database = new HashMap<>();
        return new InMemoryReservationRepository(database);
    }
}
