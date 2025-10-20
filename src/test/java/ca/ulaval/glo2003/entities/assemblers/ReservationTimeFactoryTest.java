package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.ReservationTimeDto;
import ca.ulaval.glo2003.entities.reservation.ReservationTime;
import ca.ulaval.glo2003.entities.reservation.ReservationTimeFactory;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationTimeFactoryTest {

    @Mock
    private Restaurant restaurant;

    private ReservationTimeFactory reservationTimeFactory;

    @BeforeEach
    void setUp() {
        reservationTimeFactory = new ReservationTimeFactory();
    }

    @Test
    void create_shouldCreateValidReservationTime_whenStartTimeIsValid() {
        String startTime = "18:00";
        when(restaurant.getReservationDuration()).thenReturn(120); // 2 hours
        when(restaurant.getHours().close).thenReturn("22:00");

        ReservationTime result = reservationTimeFactory.create(startTime, restaurant);

        assertNotNull(result);
        assertEquals("18:00", result.getStart());
        assertEquals("20:00", result.getEnd());
    }

    @Test
    void create_shouldAdjustToNext15Minutes_whenStartTimeIsNotOn15MinuteInterval() {
        String startTime = "18:07";
        when(restaurant.getReservationDuration()).thenReturn(90); // 1.5 hours
        when(restaurant.getHours().close).thenReturn("23:00");

        ReservationTime result = reservationTimeFactory.create(startTime, restaurant);

        assertNotNull(result);
        assertEquals("18:15", result.getStart()); // Adjusted from 18:07 to 18:15
        assertEquals("19:45", result.getEnd());
    }

    @Test
    void create_shouldAdjustToNext15Minutes_whenMinutesAre1() {
        String startTime = "19:01";
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHours().close).thenReturn("23:00");

        ReservationTime result = reservationTimeFactory.create(startTime, restaurant);

        assertEquals("19:15", result.getStart()); // Adjusted from 19:01 to 19:15
        assertEquals("20:15", result.getEnd());
    }

    @Test
    void create_shouldAdjustToNext15Minutes_whenMinutesAre14() {
        String startTime = "17:14";
        when(restaurant.getReservationDuration()).thenReturn(90);
        when(restaurant.getHours().close).thenReturn("23:00");

        ReservationTime result = reservationTimeFactory.create(startTime, restaurant);

        assertEquals("17:15", result.getStart()); // Adjusted from 17:14 to 17:15
        assertEquals("18:45", result.getEnd());
    }

    @Test
    void create_shouldNotAdjust_whenStartTimeIsAlreadyOn15MinuteInterval() {

        String startTime = "18:30";
        when(restaurant.getReservationDuration()).thenReturn(120);
        when(restaurant.getHours().close).thenReturn("23:00");

        ReservationTime result = reservationTimeFactory.create(startTime, restaurant);

        assertEquals("18:30", result.getStart());
        assertEquals("20:30", result.getEnd());
    }

    @Test
    void create_shouldHandleAllValid15MinuteIntervals() {

        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHours().close).thenReturn("23:00");

        ReservationTime result1 = reservationTimeFactory.create("18:00", restaurant);
        assertEquals("18:00", result1.getStart());

        ReservationTime result2 = reservationTimeFactory.create("18:15", restaurant);
        assertEquals("18:15", result2.getStart());

        ReservationTime result3 = reservationTimeFactory.create("18:30", restaurant);
        assertEquals("18:30", result3.getStart());

        ReservationTime result4 = reservationTimeFactory.create("18:45", restaurant);
        assertEquals("18:45", result4.getStart());
    }

    @Test
    void create_shouldThrowInvalideParameterException_whenStartTimeIsAfterClosingTime() {

        String startTime = "22:30";
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHours().close).thenReturn("22:00");

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            reservationTimeFactory.create(startTime, restaurant);
        });

        assertEquals("Reservation Start time is too late", exception.getMessage());
    }

    @Test
    void create_shouldThrowInvalideParameterException_whenAdjustedStartTimeIsAfterClosingTime() {

        String startTime = "21:50"; // Will be adjusted to 22:00
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHours().close).thenReturn("21:45");

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            reservationTimeFactory.create(startTime, restaurant);
        });

        assertEquals("Reservation Start time is too late", exception.getMessage());
    }

    @Test
    void create_shouldThrowMissingParameterException_whenStartTimeIsNull() {
        String startTime = null;

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> {
            reservationTimeFactory.create(startTime, restaurant);
        });

        assertEquals("Reservation Start time  est requis", exception.getMessage());
        verify(restaurant, never()).getReservationDuration();
        verify(restaurant, never()).getHours();
    }

    @Test
    void create_shouldThrowMissingParameterException_whenStartTimeIsEmpty() {
        String startTime = "";

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> {
            reservationTimeFactory.create(startTime, restaurant);
        });

        assertEquals("Reservation Start time  est requis", exception.getMessage());
        verify(restaurant, never()).getReservationDuration();
        verify(restaurant, never()).getCapacity();
    }

    @Test
    void create_shouldCalculateCorrectEndTime_withDifferentDurations() {
        when(restaurant.getHours().close).thenReturn("23:00");

        when(restaurant.getReservationDuration()).thenReturn(30);
        ReservationTime result1 = reservationTimeFactory.create("18:00", restaurant);
        assertEquals("18:30", result1.getEnd());
        when(restaurant.getReservationDuration()).thenReturn(90);
        ReservationTime result2 = reservationTimeFactory.create("18:00", restaurant);
        assertEquals("19:30", result2.getEnd());

        when(restaurant.getReservationDuration()).thenReturn(180);
        ReservationTime result3 = reservationTimeFactory.create("18:00", restaurant);
        assertEquals("21:00", result3.getEnd());
    }

    @Test
    void create_shouldNotAcceptStartTime_whenExactlyAtClosingTime() {
        String startTime = "22:00";
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHours().close).thenReturn("22:00");

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            reservationTimeFactory.create(startTime, restaurant);
        });

        assertEquals("Reservation Start time is too late", exception.getMessage());
    }

    @Test
    void create_shouldAcceptStartTime_whenJustBeforeClosingTime() {
        String startTime = "21:45";
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHours().close).thenReturn("22:00");

        ReservationTime result = reservationTimeFactory.create(startTime, restaurant);

        assertNotNull(result);
        assertEquals("21:45", result.getStart());
        assertEquals("22:45", result.getEnd());
    }

    @Test
    void create_shouldHandleSecondsAndNanoseconds_bySettingThemToZero() {

        String startTime = "18:07:30"; // With seconds
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHours().close).thenReturn("23:00");

        ReservationTime result = reservationTimeFactory.create(startTime, restaurant);

        assertEquals("18:15", result.getStart()); // Seconds removed, adjusted to next 15
        assertFalse(result.getStart().contains(":30")); // No seconds in output
    }

    @Test
    void create_shouldAdjustAcrossHourBoundary() {

        String startTime = "18:53"; // Should adjust to 19:00
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHours().close).thenReturn("23:00");

        ReservationTime result = reservationTimeFactory.create(startTime, restaurant);

        assertEquals("19:00", result.getStart());
        assertEquals("20:00", result.getEnd());
    }
}