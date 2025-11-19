package ca.ulaval.glo2003.persistence.infra.inMemory;

import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.filters.Filter;
import ca.ulaval.glo2003.entities.ReservationRepository;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryReservationRepository;
import ca.ulaval.glo2003.persistence.infra.ReservationRepositoryTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryReservationRepositoryTest {

    private InMemoryReservationRepository reservationRepository;

    private Map<String, Reservation> reservations;

    private Reservation reservation;

    private Restaurant restaurant;

    private final String RESTAURANT_ID = "1";

    private final String INVALID_ID = "-11";

    @BeforeEach
    public void setUp() {
        reservations  = new HashMap<>();
        reservationRepository = new InMemoryReservationRepository(reservations);
        restaurant = InMemoryRestaurantRepositoryTestUtils.createRestaurant(RESTAURANT_ID);
        reservation = new Reservation(
                "1",
                "2019-04-04",
                new ReservationTime(),
                4,
                new Customer(),
                restaurant
        );
    }

    @Test
    public void givenDeleteRelatedReservations_whenValidReservationId_thenReturnTrue() {
        reservationRepository.save(reservation);

        assertTrue(reservationRepository.deleteRelatedReservations(RESTAURANT_ID),
                "Delete should delete at least one reservation");
    }

    @Test
    public void givenDeleteRelatedReservations_whenInvalideReservationId_thenReturnFalse() {
        reservationRepository.save(reservation);
public class InMemoryReservationRepositoryTest extends ReservationRepositoryTest {

        assertFalse(reservationRepository.deleteRelatedReservations(null),
                "Delete shouldn't delete any reservation");
    }

    @Test
    public void givenDelete_whenValidReservationId_thenReturnTrue() {
        reservationRepository.save(reservation);
        assertTrue(reservationRepository.delete(reservation.getNumber()));
    }

    @Test
    public void givenDelete_whenInvalidReservationId_thenReturnFalse() {
        reservationRepository.save(reservation);
        assertFalse(reservationRepository.delete(INVALID_ID));
    }

    @Test
    public void givenGetAll_whenNotEmptyDB_thenReturnAllReservations() {
        reservationRepository.save(reservation);
        assertEquals(reservations.size(), reservationRepository.getAll().size());
    }

    @Test
    public void givenSearch_whenFiltersNotEmpty_thenReturnMatchingReservations() {
        reservationRepository.save(reservation);
        List<Filter<Reservation>> filters = new ArrayList<>();
        filters.add(r -> r.getNumber().equals(reservation.getNumber()));

        assertEquals(1, reservationRepository.search(filters).size());
    @Override
    protected ReservationRepository getRepository() {
        Map<String, Reservation> database = new HashMap<>();
        return new InMemoryReservationRepository(database);
    }
}
