package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.ReservationTimeDto;
import ca.ulaval.glo2003.entities.Restaurant;
import ca.ulaval.glo2003.entities.exceptions.ParametreInvalideException;
import ca.ulaval.glo2003.entities.exceptions.ParametreManquantException;
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
        when(restaurant.getHoraireFermeture()).thenReturn("22:00");

        ReservationTimeDto result = reservationTimeFactory.create(startTime, restaurant);

        assertNotNull(result);
        assertEquals("18:00", result.getStart());
        assertEquals("20:00", result.getEnd());
    }

    @Test
    void create_shouldAdjustToNext15Minutes_whenStartTimeIsNotOn15MinuteInterval() {
        String startTime = "18:07";
        when(restaurant.getReservationDuration()).thenReturn(90); // 1.5 hours
        when(restaurant.getHoraireFermeture()).thenReturn("23:00");

        ReservationTimeDto result = reservationTimeFactory.create(startTime, restaurant);

        assertNotNull(result);
        assertEquals("18:15", result.getStart()); // Adjusted from 18:07 to 18:15
        assertEquals("19:45", result.getEnd());
    }

    @Test
    void create_shouldAdjustToNext15Minutes_whenMinutesAre1() {
        String startTime = "19:01";
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHoraireFermeture()).thenReturn("23:00");

        ReservationTimeDto result = reservationTimeFactory.create(startTime, restaurant);

        assertEquals("19:15", result.getStart()); // Adjusted from 19:01 to 19:15
        assertEquals("20:15", result.getEnd());
    }

    @Test
    void create_shouldAdjustToNext15Minutes_whenMinutesAre14() {
        String startTime = "17:14";
        when(restaurant.getReservationDuration()).thenReturn(90);
        when(restaurant.getHoraireFermeture()).thenReturn("23:00");

        ReservationTimeDto result = reservationTimeFactory.create(startTime, restaurant);

        assertEquals("17:15", result.getStart()); // Adjusted from 17:14 to 17:15
        assertEquals("18:45", result.getEnd());
    }

    @Test
    void create_shouldNotAdjust_whenStartTimeIsAlreadyOn15MinuteInterval() {

        String startTime = "18:30";
        when(restaurant.getReservationDuration()).thenReturn(120);
        when(restaurant.getHoraireFermeture()).thenReturn("23:00");

        ReservationTimeDto result = reservationTimeFactory.create(startTime, restaurant);

        assertEquals("18:30", result.getStart());
        assertEquals("20:30", result.getEnd());
    }

    @Test
    void create_shouldHandleAllValid15MinuteIntervals() {

        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHoraireFermeture()).thenReturn("23:00");

        ReservationTimeDto result1 = reservationTimeFactory.create("18:00", restaurant);
        assertEquals("18:00", result1.getStart());

        ReservationTimeDto result2 = reservationTimeFactory.create("18:15", restaurant);
        assertEquals("18:15", result2.getStart());

        ReservationTimeDto result3 = reservationTimeFactory.create("18:30", restaurant);
        assertEquals("18:30", result3.getStart());

        ReservationTimeDto result4 = reservationTimeFactory.create("18:45", restaurant);
        assertEquals("18:45", result4.getStart());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenStartTimeIsAfterClosingTime() {

        String startTime = "22:30";
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHoraireFermeture()).thenReturn("22:00");

        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            reservationTimeFactory.create(startTime, restaurant);
        });

        assertEquals("Reservation Start time is too late", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenAdjustedStartTimeIsAfterClosingTime() {

        String startTime = "21:50"; // Will be adjusted to 22:00
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHoraireFermeture()).thenReturn("21:45");

        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            reservationTimeFactory.create(startTime, restaurant);
        });

        assertEquals("Reservation Start time is too late", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreManquantException_whenStartTimeIsNull() {
        String startTime = null;

        ParametreManquantException exception = assertThrows(ParametreManquantException.class, () -> {
            reservationTimeFactory.create(startTime, restaurant);
        });

        assertEquals("Reservation Start time  est requis", exception.getMessage());
        verify(restaurant, never()).getReservationDuration();
        verify(restaurant, never()).getHoraireFermeture();
    }

    @Test
    void create_shouldThrowParametreManquantException_whenStartTimeIsEmpty() {
        String startTime = "";

        ParametreManquantException exception = assertThrows(ParametreManquantException.class, () -> {
            reservationTimeFactory.create(startTime, restaurant);
        });

        assertEquals("Reservation Start time  est requis", exception.getMessage());
        verify(restaurant, never()).getReservationDuration();
        verify(restaurant, never()).getHoraireFermeture();
    }

    @Test
    void create_shouldCalculateCorrectEndTime_withDifferentDurations() {
        when(restaurant.getHoraireFermeture()).thenReturn("23:00");

        when(restaurant.getReservationDuration()).thenReturn(30);
        ReservationTimeDto result1 = reservationTimeFactory.create("18:00", restaurant);
        assertEquals("18:30", result1.getEnd());
        when(restaurant.getReservationDuration()).thenReturn(90);
        ReservationTimeDto result2 = reservationTimeFactory.create("18:00", restaurant);
        assertEquals("19:30", result2.getEnd());

        when(restaurant.getReservationDuration()).thenReturn(180);
        ReservationTimeDto result3 = reservationTimeFactory.create("18:00", restaurant);
        assertEquals("21:00", result3.getEnd());
    }

    @Test
    void create_shouldNotAcceptStartTime_whenExactlyAtClosingTime() {
        String startTime = "22:00";
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHoraireFermeture()).thenReturn("22:00");

        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            reservationTimeFactory.create(startTime, restaurant);
        });

        assertEquals("Reservation Start time is too late", exception.getMessage());
    }

    @Test
    void create_shouldAcceptStartTime_whenJustBeforeClosingTime() {
        String startTime = "21:45";
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHoraireFermeture()).thenReturn("22:00");

        ReservationTimeDto result = reservationTimeFactory.create(startTime, restaurant);

        assertNotNull(result);
        assertEquals("21:45", result.getStart());
        assertEquals("22:45", result.getEnd());
    }

    @Test
    void create_shouldHandleSecondsAndNanoseconds_bySettingThemToZero() {

        String startTime = "18:07:30"; // With seconds
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHoraireFermeture()).thenReturn("23:00");

        ReservationTimeDto result = reservationTimeFactory.create(startTime, restaurant);

        assertEquals("18:15", result.getStart()); // Seconds removed, adjusted to next 15
        assertFalse(result.getStart().contains(":30")); // No seconds in output
    }

    @Test
    void create_shouldAdjustAcrossHourBoundary() {

        String startTime = "18:53"; // Should adjust to 19:00
        when(restaurant.getReservationDuration()).thenReturn(60);
        when(restaurant.getHoraireFermeture()).thenReturn("23:00");

        ReservationTimeDto result = reservationTimeFactory.create(startTime, restaurant);

        assertEquals("19:00", result.getStart());
        assertEquals("20:00", result.getEnd());
    }
}