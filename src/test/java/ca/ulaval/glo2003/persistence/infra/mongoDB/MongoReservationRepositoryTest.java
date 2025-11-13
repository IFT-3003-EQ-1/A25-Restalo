package ca.ulaval.glo2003.persistence.infra.mongoDB;

import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.reservation.ReservationTime;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoReservationRepository;
import dev.morphia.Datastore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Disabled
public class MongoReservationRepositoryTest {

    private final String RESERVATION_ID = "1";

    @Mock
    public Datastore datastore;

    private MongoReservationRepository repository;

    private Reservation reservation;


    @BeforeEach
    public void setUp() {
        repository = new MongoReservationRepository(datastore);
        reservation = new Reservation(
                RESERVATION_ID,
                "2025-10-25",
                new ReservationTime(),
                2,
                new Customer(),
                new Restaurant()
        );
    }

    @Test
    public void givenGet_whenIdIsValid_thenReturnReservation() {
        repository.save(reservation);

        Optional<Reservation> value = repository.get(RESERVATION_ID);

        assertTrue(value.isPresent());
        assertEquals(value.get(), reservation);
    }

    @Test
    public void givenGet_whenIdIsNotValid_thenReturnEmptyOptional() {
        repository.save(reservation);

        Optional<Reservation> restaurantObtenu = repository.get(null);

        assertFalse(restaurantObtenu.isPresent());
    }

    @Test
    public void givenSave_whenParametersAreValid_thenDatastoreContainsReservation() {
        repository.save(reservation);

        Optional<Reservation> value = repository.get(RESERVATION_ID);

        assertTrue(value.isPresent());
        assertEquals(value.get(), reservation);
    }

}
