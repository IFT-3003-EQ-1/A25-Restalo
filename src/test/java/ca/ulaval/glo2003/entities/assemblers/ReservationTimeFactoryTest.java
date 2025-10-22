package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import ca.ulaval.glo2003.entities.reservation.ReservationTime;
import ca.ulaval.glo2003.entities.reservation.ReservationTimeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReservationTimeFactoryTest {

    private ReservationTimeFactory reservationTimeFactory;

    @BeforeEach
    void setUp() {
        reservationTimeFactory = new ReservationTimeFactory();
    }

    @Test
    void create_shouldCreateValidReservationTime_whenStartTimeIsValid() {
        String startTime = "18:00";
        String closingTime = "22:00";
        int reservationDuration = 120; // 2 hours

        ReservationTime result = reservationTimeFactory.create(startTime, closingTime, reservationDuration);

        assertNotNull(result);
        assertEquals("18:00", result.getStart());
        assertEquals("20:00", result.getEnd());
    }

    @Test
    void create_shouldAdjustToNext15Minutes_whenStartTimeIsNotOn15MinuteInterval() {
        String startTime = "18:07";
        String closingTime = "23:00";
        int reservationDuration = 90; // 1.5 hours

        ReservationTime result = reservationTimeFactory.create(startTime, closingTime, reservationDuration);

        assertNotNull(result);
        assertEquals("18:15", result.getStart()); // Adjusted from 18:07 to 18:15
        assertEquals("19:45", result.getEnd());
    }

    @Test
    void create_shouldAdjustToNext15Minutes_whenMinutesAre1() {
        String startTime = "19:01";
        String closingTime = "23:00";
        int reservationDuration = 60;

        ReservationTime result = reservationTimeFactory.create(startTime, closingTime, reservationDuration);

        assertEquals("19:15", result.getStart()); // Adjusted from 19:01 to 19:15
        assertEquals("20:15", result.getEnd());
    }

    @Test
    void create_shouldAdjustToNext15Minutes_whenMinutesAre14() {
        String startTime = "17:14";
        String closingTime = "23:00";
        int reservationDuration = 90;

        ReservationTime result = reservationTimeFactory.create(startTime, closingTime, reservationDuration);

        assertEquals("17:15", result.getStart()); // Adjusted from 17:14 to 17:15
        assertEquals("18:45", result.getEnd());
    }

    @Test
    void create_shouldNotAdjust_whenStartTimeIsAlreadyOn15MinuteInterval() {
        String startTime = "18:30";
        String closingTime = "23:00";
        int reservationDuration = 120;

        ReservationTime result = reservationTimeFactory.create(startTime, closingTime, reservationDuration);

        assertEquals("18:30", result.getStart());
        assertEquals("20:30", result.getEnd());
    }

    @Test
    void create_shouldHandleAllValid15MinuteIntervals() {
        String closingTime = "23:00";
        int reservationDuration = 60;

        ReservationTime result1 = reservationTimeFactory.create("18:00", closingTime, reservationDuration);
        assertEquals("18:00", result1.getStart());

        ReservationTime result2 = reservationTimeFactory.create("18:15", closingTime, reservationDuration);
        assertEquals("18:15", result2.getStart());

        ReservationTime result3 = reservationTimeFactory.create("18:30", closingTime, reservationDuration);
        assertEquals("18:30", result3.getStart());

        ReservationTime result4 = reservationTimeFactory.create("18:45", closingTime, reservationDuration);
        assertEquals("18:45", result4.getStart());
    }

    @Test
    void create_shouldThrowInvalideParameterException_whenStartTimeIsAfterClosingTime() {
        String startTime = "22:30";
        String closingTime = "22:00";
        int reservationDuration = 60;

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            reservationTimeFactory.create(startTime, closingTime, reservationDuration);
        });

        assertEquals("Reservation Start time is too late", exception.getMessage());
    }

    @Test
    void create_shouldThrowInvalideParameterException_whenAdjustedStartTimeIsAfterClosingTime() {
        String startTime = "21:50"; // Will be adjusted to 22:00
        String closingTime = "21:45";
        int reservationDuration = 60;

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            reservationTimeFactory.create(startTime, closingTime, reservationDuration);
        });

        assertEquals("Reservation Start time is too late", exception.getMessage());
    }

    @Test
    void create_shouldThrowMissingParameterException_whenStartTimeIsNull() {
        String startTime = null;
        String closingTime = "22:00";
        int reservationDuration = 60;

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> {
            reservationTimeFactory.create(startTime, closingTime, reservationDuration);
        });

        assertEquals("Reservation Start time  est requis", exception.getMessage());
    }

    @Test
    void create_shouldThrowMissingParameterException_whenStartTimeIsEmpty() {
        String startTime = "";
        String closingTime = "22:00";
        int reservationDuration = 60;

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> {
            reservationTimeFactory.create(startTime, closingTime, reservationDuration);
        });

        assertEquals("Reservation Start time  est requis", exception.getMessage());
    }

    @Test
    void create_shouldCalculateCorrectEndTime_withDifferentDurations() {
        String startTime = "18:00";
        String closingTime = "23:00";

        ReservationTime result1 = reservationTimeFactory.create(startTime, closingTime, 30);
        assertEquals("18:30", result1.getEnd());

        ReservationTime result2 = reservationTimeFactory.create(startTime, closingTime, 90);
        assertEquals("19:30", result2.getEnd());

        ReservationTime result3 = reservationTimeFactory.create(startTime, closingTime, 180);
        assertEquals("21:00", result3.getEnd());
    }

    @Test
    void create_shouldThrowException_whenStartTimeExactlyAtClosingTime() {
        String startTime = "22:00";
        String closingTime = "22:00";
        int reservationDuration = 60;

        InvalideParameterException exception = assertThrows(InvalideParameterException.class,
                () -> reservationTimeFactory.create(startTime, closingTime, reservationDuration));

        assertEquals("Reservation Start time is too late", exception.getMessage());
    }

    @Test
    void create_shouldAcceptStartTime_whenJustBeforeClosingTime() {
        String startTime = "21:45";
        String closingTime = "22:00";
        int reservationDuration = 60;

        ReservationTime result = reservationTimeFactory.create(startTime, closingTime, reservationDuration);

        assertNotNull(result);
        assertEquals("21:45", result.getStart());
        assertEquals("22:45", result.getEnd());
    }

    @Test
    void create_shouldHandleSecondsAndNanoseconds_bySettingThemToZero() {
        String startTime = "18:07:30"; // With seconds
        String closingTime = "23:00";
        int reservationDuration = 60;

        ReservationTime result = reservationTimeFactory.create(startTime, closingTime, reservationDuration);

        assertEquals("18:15", result.getStart()); // Seconds removed, adjusted to next 15
        assertFalse(result.getStart().contains(":30")); // No seconds in output
    }

    @Test
    void create_shouldAdjustAcrossHourBoundary() {
        String startTime = "18:53"; // Should adjust to 19:00
        String closingTime = "23:00";
        int reservationDuration = 60;

        ReservationTime result = reservationTimeFactory.create(startTime, closingTime, reservationDuration);

        assertEquals("19:00", result.getStart());
        assertEquals("20:00", result.getEnd());
    }

    @Test
    void create_shouldHandleEarlyMorningTimes() {
        String startTime = "06:08";
        String closingTime = "14:00";
        int reservationDuration = 120;

        ReservationTime result = reservationTimeFactory.create(startTime, closingTime, reservationDuration);

        assertEquals("06:15", result.getStart());
        assertEquals("08:15", result.getEnd());
    }

    @Test
    void create_shouldHandleLateMorningToAfternoon() {
        String startTime = "11:37";
        String closingTime = "23:00";
        int reservationDuration = 90;

        ReservationTime result = reservationTimeFactory.create(startTime, closingTime, reservationDuration);

        assertEquals("11:45", result.getStart());
        assertEquals("13:15", result.getEnd());
    }

    @Test
    void create_shouldThrowException_whenAdjustedTimeEqualsClosingTime() {
        String startTime = "21:59"; // Will be adjusted to 22:00
        String closingTime = "22:00";
        int reservationDuration = 60;

        InvalideParameterException exception = assertThrows(InvalideParameterException.class,
                () -> reservationTimeFactory.create(startTime, closingTime, reservationDuration));

        assertEquals("Reservation Start time is too late", exception.getMessage());
    }
}