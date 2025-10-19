package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.ReservationTimeDto;
import ca.ulaval.glo2003.entities.Proprietaire;
import ca.ulaval.glo2003.entities.Reservation;
import ca.ulaval.glo2003.entities.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationAssemblerTest {

    private ReservationAssembler reservationAssembler;

    @BeforeEach
    void setUp() {
        reservationAssembler = new ReservationAssembler();
    }

    @Test
    void toDto_shouldConvertReservationToDto_whenReservationIsValid() {
        Restaurant restaurant = createDummyRestaurant();
        ReservationDto result = reservationAssembler.toDto(createDummyReservation());
        assertNotNull(result);
        assertNotNull(result.getRestaurant());
        assertEquals("restaurant-123", result.getRestaurant().id);
        assertEquals("Hot pizza", result.getRestaurant().nom);
    }

    @Test
    void toDto_shouldMapAllFields_whenAllFieldsArePopulated() {
        Restaurant restaurant =  createDummyRestaurant();
        Reservation reservation = createDummyReservation();
        reservation.setGroupSize(2);
        ReservationDto result = reservationAssembler.toDto(reservation);
        assertNotNull(result);
        assertEquals("RES123456", result.getNumber());
        assertEquals("2025-10-20", result.getDate());
        assertNotNull(result.getRestaurant());
        assertEquals("restaurant-123", result.getRestaurant().id);
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
        reservation.setTime(timeDto);

        ReservationDto result = reservationAssembler.toDto(reservation);

        assertNotNull(result);
        assertNull(result.getCustomer());
        assertEquals("RES111", result.getNumber());
    }

    @Test
    void toDto_shouldHandleNullTime_gracefully() {
        Restaurant restaurant = createDummyRestaurant();
        
        CustomerDto customerDto = new CustomerDto();
        
        Reservation reservation = new Reservation();
        reservation.setNumber("RES222");
        reservation.setDate("2025-11-20");
        reservation.setCustomer(customerDto);
        reservation.setRestaurant(restaurant);
        reservation.setTime(null);

        ReservationDto result = reservationAssembler.toDto(reservation);

        assertNotNull(result);
        assertNull(result.getTime());
        assertEquals("RES222", result.getNumber());
    }

    private Restaurant createDummyRestaurant() {
        return new Restaurant(
                "restaurant-123",
                new Proprietaire("1"),
                "Hot pizza",
                20,
                "10:00:00",
                "22:00:00"
        );
    }

    private Reservation createDummyReservation() {
        String reservationNumber = "RES123456";
        String reservationDate = "2025-10-20";
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("John Doe");
        customerDto.setEmail("john@example.com");

        ReservationTimeDto timeDto = new ReservationTimeDto();
        timeDto.setStart("18:00");
        timeDto.setEnd("20:00");

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