package ca.ulaval.glo2003.persistence.infra;

import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.ReservationRepository;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.reservation.ReservationTime;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public abstract class ReservationRepositoryTest {
    private final String RESERVATION_NUMBER = "1";
    private final int GROUPE_SIZE = 2;

    private ReservationRepository repository;

    protected abstract ReservationRepository getRepository();

    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        repository = getRepository();
        reservation = new Reservation(
                RESERVATION_NUMBER,
                "2025-10-25",
                new ReservationTime(),
                GROUPE_SIZE,
                new Customer(),
                new Restaurant()
        );
    }

    @Test
    public void givenGet_whenNumberIsValid_thenReturnReservation() {
        repository.save(reservation);

        Optional<Reservation> value = repository.get(RESERVATION_NUMBER);
        assertTrue(value.isPresent());
        assertEquals(value.get().getNumber(), RESERVATION_NUMBER);
    }

    @Test
    public void givenGet_whenNumberIsNotValid_thenReturnEmptyOptional() {
        repository.save(reservation);

        Optional<Reservation> foundReservation = repository.get(null);

        assertFalse(foundReservation.isPresent());
    }

    @Test
    public void givenSave_whenParametersAreValid_thenDatastoreContainsReservation() {
        repository.save(reservation);

        Optional<Reservation> value = repository.get(RESERVATION_NUMBER);
        assertTrue(value.isPresent());
        assertEquals(value.get().getGroupSize(), GROUPE_SIZE);
    }
}
