package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.CustomerFactory;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.reservation.ReservationFactory;
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
class ReservationFactoryTest {

    @Mock
    private CustomerFactory customerFactory;
    
    @Mock
    private ReservationTimeFactory reservationTimeFactory;
    
    @Mock
    private Restaurant restaurant;

    private ReservationFactory reservationFactory;

    @BeforeEach
    void setUp() {
        reservationFactory = new ReservationFactory(
            customerFactory,
            reservationTimeFactory
        );
    }

    @Test
    void createReservation_shouldCreateValidReservation_whenAllParametersAreValid() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.groupSize = 4;
        reservationDto.date = "2025-10-20";
        reservationDto.startTime = "18:00";
        
        Customer mockCustomer = new Customer();
        mockCustomer.setName("John Doe");
        
        ReservationTime mockTime = new ReservationTime("18:00:00","18:00:00");
        //mockTimeDto.setStart("18:00:00");
        
        when(customerFactory.create(reservationDto.customer)).thenReturn(mockCustomer);
        when(reservationTimeFactory.create(reservationDto.startTime, restaurant)).thenReturn(mockTime);

        Reservation result = reservationFactory.createReservation(reservationDto, restaurant);

        assertNotNull(result);
        assertNotNull(result.getNumber());
        assertFalse(result.getNumber().contains("-")); // UUID without dashes
        assertEquals(mockTime, result.getTime());
        assertEquals(restaurant, result.getRestaurant());
        assertEquals(mockCustomer, result.getCustomer());
        assertEquals(4, result.getGroupSize());
        assertEquals("2025-10-20", result.getDate());
        
        verify(customerFactory).create(reservationDto.customer);
        verify(reservationTimeFactory).create(reservationDto.startTime, restaurant);
    }

    @Test
    void createReservation_shouldThrowInvalideParameterException_whenGroupSizeIsZero() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.groupSize=0;
        reservationDto.date = "2025-10-20";

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            reservationFactory.createReservation(reservationDto, restaurant);
        });
        
        assertEquals("Group size must be at least 1", exception.getMessage());
        verify(customerFactory, never()).create(any());
        verify(reservationTimeFactory, never()).create(any(), any());
    }

    @Test
    void createReservation_shouldThrowInvalideParameterException_whenGroupSizeIsNegative() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.groupSize=-5;
        reservationDto.date="2025-10-20";

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            reservationFactory.createReservation(reservationDto, restaurant);
        });
        
        assertEquals("Group size must be at least 1", exception.getMessage());
        verify(customerFactory, never()).create(any());
        verify(reservationTimeFactory, never()).create(any(), any());
    }

    @Test
    void createReservation_shouldThrowMissingParameterException_whenDateIsNull() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.groupSize=4;
        reservationDto.date =null;

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> {
            reservationFactory.createReservation(reservationDto, restaurant);
        });
        
        assertEquals("Reservation date  est requis", exception.getMessage());
        verify(customerFactory, never()).create(any());
        verify(reservationTimeFactory, never()).create(any(), any());
    }

    @Test
    void createReservation_shouldThrowMissingParameterException_whenDateIsEmpty() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.groupSize=4;
        reservationDto.date="";

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> {
            reservationFactory.createReservation(reservationDto, restaurant);
        });
        
        assertEquals("Reservation date  est requis", exception.getMessage());
        verify(customerFactory, never()).create(any());
        verify(reservationTimeFactory, never()).create(any(), any());
    }

    @Test
    void createReservation_shouldGenerateUniqueIds_forMultipleReservations() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.groupSize=2;
        reservationDto.date="2025-10-20";
        reservationDto.startTime="18:00";
        
        Customer mockCustomer = new Customer();
        ReservationTime mockTime = new ReservationTime("18:00","22:00");
        
        when(customerFactory.create(any())).thenReturn(mockCustomer);
        when(reservationTimeFactory.create(any(), any())).thenReturn(mockTime);

        Reservation reservation1 = reservationFactory.createReservation(reservationDto, restaurant);
        Reservation reservation2 = reservationFactory.createReservation(reservationDto, restaurant);

        assertNotEquals(reservation1.getNumber(), reservation2.getNumber());
        assertFalse(reservation1.getNumber().contains("-"));
        assertFalse(reservation2.getNumber().contains("-"));
    }

    @Test
    void createReservation_shouldCallFactories_withCorrectParameters() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.groupSize=3;
        reservationDto.date="2025-10-20";
        reservationDto.startTime="19:30";
        
        CustomerDto customerDto = new CustomerDto();
        customerDto.name = "Jane Smith";
        reservationDto.customer = customerDto;
        
        Customer mockCustomerDto = new Customer();
        ReservationTime mockTimeDto = new ReservationTime("19:30","22:30:00");
        
        when(customerFactory.create(customerDto)).thenReturn(mockCustomerDto);
        when(reservationTimeFactory.create("19:30", restaurant)).thenReturn(mockTimeDto);

        reservationFactory.createReservation(reservationDto, restaurant);

        verify(customerFactory).create(customerDto);
        verify(reservationTimeFactory).create("19:30", restaurant);
    }
}