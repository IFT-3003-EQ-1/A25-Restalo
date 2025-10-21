package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.ReservationTimeDto;
import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.reservation.ReservationTime;
import ca.ulaval.glo2003.entities.restaurant.ConfigReservation;
import ca.ulaval.glo2003.entities.restaurant.Hours;
import ca.ulaval.glo2003.entities.restaurant.Owner;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReservationAssemblerTest {

    private ReservationAssembler reservationAssembler;

    @BeforeEach
    void setUp() {
        reservationAssembler = new ReservationAssembler();
    }

    @Test
    void toDto_shouldConvertReservationToDto_whenReservationIsValid() {
        ReservationDto result = reservationAssembler.toDto(createDummyReservation());
        assertNotNull(result);
    }

    @Test
    void toDto_shouldMapAllFields_whenAllFieldsArePopulated() {
        Restaurant restaurant =  createDummyRestaurant();
        Reservation reservation = createDummyReservation();
        reservation.setGroupSize(2);
        ReservationDto result = reservationAssembler.toDto(reservation);
        assertNotNull(result);
    }

    @Test
    void toDto_shouldHandleNullCustomer_gracefully() {
        Restaurant restaurant = createDummyRestaurant();
        
        ReservationTimeDto timeDto = new ReservationTimeDto();
        
        Reservation reservation = new Reservation();
        reservation.setNumber("RES111");
        reservation.setDate("2025-11-15");
        reservation.setCustomer(null);
        reservation.setRestaurant(restaurant);

        ReservationDto result = reservationAssembler.toDto(reservation);

        assertNotNull(result);
    }

    @Test
    void toDto_shouldHandleNullTime_gracefully() {
        Restaurant restaurant = createDummyRestaurant();
        
        Customer customerDto = new Customer();
        
        Reservation reservation = new Reservation();
        reservation.setNumber("RES222");
        reservation.setDate("2025-11-20");
        reservation.setCustomer(customerDto);
        reservation.setRestaurant(restaurant);
        reservation.setTime(null);

        ReservationDto result = reservationAssembler.toDto(reservation);

        assertNotNull(result);
        assertNull(result.startTime);
        assertEquals("RES222", result.number);
    }

    private Restaurant createDummyRestaurant() {
        return new Restaurant(
                "restaurant-123",
                new Owner("1"),
                "Hot pizza",
                20,
                new Hours("10:00:00","22:00:00"),
                new ConfigReservation(60)
        );
    }

    private Reservation createDummyReservation() {
        String reservationNumber = "RES123456";
        String reservationDate = "2025-10-20";
        Customer customerDto = new Customer();
        customerDto.setName("John Doe");
        customerDto.setEmail("john@example.com");

        ReservationTime timeDto = new ReservationTime("18:00","20:00");
        Reservation reservation = new Reservation();
        reservation.setNumber(reservationNumber);
        reservation.setDate(reservationDate);
        reservation.setCustomer(customerDto);
        reservation.setRestaurant(createDummyRestaurant());
        reservation.setTime(timeDto);
        reservation.setGroupSize(4);
        return reservation;
    }
}