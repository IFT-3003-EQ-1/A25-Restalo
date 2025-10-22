package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.CustomerFactory;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.reservation.ReservationFactory;
import ca.ulaval.glo2003.entities.reservation.ReservationTime;
import ca.ulaval.glo2003.entities.reservation.ReservationTimeFactory;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.restaurant.Hours;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationFactoryTest {

    @Mock
    private CustomerFactory customerFactory;

    @Mock
    private ReservationTimeFactory reservationTimeFactory;

    @Mock
    private Restaurant restaurant;

    @Mock
    private Hours hours;

    @Mock
    private Customer customer;

    @Mock
    private ReservationTime reservationTime;

    private ReservationFactory reservationFactory;

    private ReservationDto validReservationDto;

    @BeforeEach
    void setUp() {
        reservationFactory = new ReservationFactory(customerFactory, reservationTimeFactory);

        // Setup valid ReservationDto
        validReservationDto = new ReservationDto();
        validReservationDto.date = "2025-10-25";
        validReservationDto.startTime = "18:00";
        validReservationDto.groupSize = 4;
        validReservationDto.customer = mock(CustomerDto.class); // Customer DTO object

        // Setup default mock behaviors
        when(restaurant.getHours()).thenReturn(hours);
        when(hours.getClose()).thenReturn("22:00");
        when(restaurant.getReservationDuration()).thenReturn(120);
        when(customerFactory.create(any())).thenReturn(customer);
        when(reservationTimeFactory.create(anyString(), anyString(), anyInt())).thenReturn(reservationTime);
    }

    @Test
    void createReservation_shouldCreateValidReservation_whenAllParametersAreValid() {
        Reservation result = reservationFactory.createReservation(validReservationDto, restaurant);

        assertNotNull(result);
        assertNotNull(result.getNumber());
        assertEquals(validReservationDto.date, result.getDate());
        assertEquals(reservationTime, result.getTime());
        assertEquals(validReservationDto.groupSize, result.getGroupSize());
        assertEquals(customer, result.getCustomer());
        assertEquals(restaurant, result.getRestaurant());
    }

    @Test
    void createReservation_shouldCallCustomerFactory_withCustomerDto() {
        reservationFactory.createReservation(validReservationDto, restaurant);

        verify(customerFactory).create(validReservationDto.customer);
    }

    @Test
    void createReservation_shouldCallReservationTimeFactory_withCorrectParameters() {
        reservationFactory.createReservation(validReservationDto, restaurant);

        verify(reservationTimeFactory).create(
                validReservationDto.startTime,
                "22:00",
                120
        );
    }

    @Test
    void createReservation_shouldGenerateUniqueReservationId() {
        Reservation result1 = reservationFactory.createReservation(validReservationDto, restaurant);
        Reservation result2 = reservationFactory.createReservation(validReservationDto, restaurant);

        assertNotNull(result1.getNumber());
        assertNotNull(result2.getNumber());
        assertNotEquals(result1.getNumber(), result2.getNumber());
    }

    @Test
    void createReservation_shouldGenerateReservationIdWithoutHyphens() {
        Reservation result = reservationFactory.createReservation(validReservationDto, restaurant);

        assertNotNull(result.getNumber());
        assertFalse(result.getNumber().contains("-"));
    }

    @Test
    void createReservation_shouldGenerateReservationIdWith32Characters() {
        Reservation result = reservationFactory.createReservation(validReservationDto, restaurant);

        assertEquals(32, result.getNumber().length());
    }

    @Test
    void createReservation_shouldAcceptMinimumValidGroupSize() {
        validReservationDto.groupSize = 1;

        Reservation result = reservationFactory.createReservation(validReservationDto, restaurant);

        assertNotNull(result);
        assertEquals(1, result.getGroupSize());
    }

    @Test
    void createReservation_shouldAcceptLargeGroupSize() {
        validReservationDto.groupSize = 100;

        Reservation result = reservationFactory.createReservation(validReservationDto, restaurant);

        assertNotNull(result);
        assertEquals(100, result.getGroupSize());
    }

    @Test
    void createReservation_shouldGetClosingTimeFromRestaurant() {
        when(hours.getClose()).thenReturn("23:30");

        reservationFactory.createReservation(validReservationDto, restaurant);

        verify(restaurant).getHours();
        verify(hours).getClose();
        verify(reservationTimeFactory).create(
                validReservationDto.startTime,
                "23:30",
                120
        );
    }

    @Test
    void createReservation_shouldGetReservationDurationFromRestaurant() {
        when(restaurant.getReservationDuration()).thenReturn(90);

        reservationFactory.createReservation(validReservationDto, restaurant);

        verify(restaurant).getReservationDuration();
        verify(reservationTimeFactory).create(
                validReservationDto.startTime,
                "22:00",
                90
        );
    }

    @Test
    void createReservation_shouldHandleDifferentStartTimes() {
        validReservationDto.startTime = "19:30";

        reservationFactory.createReservation(validReservationDto, restaurant);

        verify(reservationTimeFactory).create(
                "19:30",
                "22:00",
                120
        );
    }

    @Test
    void createReservation_shouldHandleDifferentDates() {
        validReservationDto.date = "2025-12-31";

        Reservation result = reservationFactory.createReservation(validReservationDto, restaurant);

        assertEquals("2025-12-31", result.getDate());
    }


    @Test
    void createReservation_shouldPropagateReservationTimeFactoryException() {
        when(reservationTimeFactory.create(anyString(), anyString(), anyInt()))
                .thenThrow(new InvalideParameterException("Reservation Start time is too late"));

        assertThrows(InvalideParameterException.class, () -> {
            reservationFactory.createReservation(validReservationDto, restaurant);
        });
    }

    @Test
    void createReservation_shouldCreateReservationWithAllFactoryDependencies() {
        Reservation result = reservationFactory.createReservation(validReservationDto, restaurant);

        assertNotNull(result);
        verify(customerFactory, times(1)).create(validReservationDto.customer);
        verify(reservationTimeFactory, times(1)).create(anyString(), anyString(), anyInt());
    }
}