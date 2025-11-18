package ca.ulaval.glo2003.persistence.infra.inMemory;

import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.reservation.ReservationTime;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryReservationRepositoryTest {

    private InMemoryReservationRepository reservationRepository;

    private Map<String, Reservation> reservations;

    private Reservation reservation;

    private Restaurant restaurant;

    private final String RESTAURANT_ID = "1";

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

        assertFalse(reservationRepository.deleteRelatedReservations(null),
                "Delete shouldn't delete any reservation");


    }
}
