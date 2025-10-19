package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.CreateReservationDto;
import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.domain.dtos.ReservationTimeDto;
import ca.ulaval.glo2003.entities.Reservation;
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
        CreateReservationDto reservationDto = new CreateReservationDto();
        reservationDto.setGroupSize(4);
        reservationDto.setDate("2025-10-20");
        reservationDto.setStartTime("18:00");
        
        CustomerDto mockCustomerDto = new CustomerDto();
        mockCustomerDto.setName("John Doe");
        
        ReservationTimeDto mockTimeDto = new ReservationTimeDto();
        mockTimeDto.setStart("18:00:00");
        
        when(customerFactory.create(reservationDto.getCustomer())).thenReturn(mockCustomerDto);
        when(reservationTimeFactory.create(reservationDto.getStartTime(), restaurant)).thenReturn(mockTimeDto);

        Reservation result = reservationFactory.createReservation(reservationDto, restaurant);

        assertNotNull(result);
        assertNotNull(result.getNumber());
        assertFalse(result.getNumber().contains("-")); // UUID without dashes
        assertEquals(mockTimeDto, result.getTime());
        assertEquals(restaurant, result.getRestaurant());
        assertEquals(mockCustomerDto, result.getCustomer());
        assertEquals(4, result.getGroupSize());
        assertEquals("2025-10-20", result.getDate());
        
        verify(customerFactory).create(reservationDto.getCustomer());
        verify(reservationTimeFactory).create(reservationDto.getStartTime(), restaurant);
    }

    @Test
    void createReservation_shouldThrowParametreInvalideException_whenGroupSizeIsZero() {
        CreateReservationDto reservationDto = new CreateReservationDto();
        reservationDto.setGroupSize(0);
        reservationDto.setDate("2025-10-20");

        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            reservationFactory.createReservation(reservationDto, restaurant);
        });
        
        assertEquals("Group size must be at least 1", exception.getMessage());
        verify(customerFactory, never()).create(any());
        verify(reservationTimeFactory, never()).create(any(), any());
    }

    @Test
    void createReservation_shouldThrowParametreInvalideException_whenGroupSizeIsNegative() {
        CreateReservationDto reservationDto = new CreateReservationDto();
        reservationDto.setGroupSize(-5);
        reservationDto.setDate("2025-10-20");

        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            reservationFactory.createReservation(reservationDto, restaurant);
        });
        
        assertEquals("Group size must be at least 1", exception.getMessage());
        verify(customerFactory, never()).create(any());
        verify(reservationTimeFactory, never()).create(any(), any());
    }

    @Test
    void createReservation_shouldThrowParametreManquantException_whenDateIsNull() {
        CreateReservationDto reservationDto = new CreateReservationDto();
        reservationDto.setGroupSize(4);
        reservationDto.setDate(null);

        ParametreManquantException exception = assertThrows(ParametreManquantException.class, () -> {
            reservationFactory.createReservation(reservationDto, restaurant);
        });
        
        assertEquals("Reservation date  est requis", exception.getMessage());
        verify(customerFactory, never()).create(any());
        verify(reservationTimeFactory, never()).create(any(), any());
    }

    @Test
    void createReservation_shouldThrowParametreManquantException_whenDateIsEmpty() {
        CreateReservationDto reservationDto = new CreateReservationDto();
        reservationDto.setGroupSize(4);
        reservationDto.setDate("");

        ParametreManquantException exception = assertThrows(ParametreManquantException.class, () -> {
            reservationFactory.createReservation(reservationDto, restaurant);
        });
        
        assertEquals("Reservation date  est requis", exception.getMessage());
        verify(customerFactory, never()).create(any());
        verify(reservationTimeFactory, never()).create(any(), any());
    }

    @Test
    void createReservation_shouldGenerateUniqueIds_forMultipleReservations() {
        CreateReservationDto reservationDto = new CreateReservationDto();
        reservationDto.setGroupSize(2);
        reservationDto.setDate("2025-10-20");
        reservationDto.setStartTime("18:00");
        
        CustomerDto mockCustomerDto = new CustomerDto();
        ReservationTimeDto mockTimeDto = new ReservationTimeDto();
        
        when(customerFactory.create(any())).thenReturn(mockCustomerDto);
        when(reservationTimeFactory.create(any(), any())).thenReturn(mockTimeDto);

        Reservation reservation1 = reservationFactory.createReservation(reservationDto, restaurant);
        Reservation reservation2 = reservationFactory.createReservation(reservationDto, restaurant);

        assertNotEquals(reservation1.getNumber(), reservation2.getNumber());
        assertFalse(reservation1.getNumber().contains("-"));
        assertFalse(reservation2.getNumber().contains("-"));
    }

    @Test
    void createReservation_shouldCallFactories_withCorrectParameters() {
        CreateReservationDto reservationDto = new CreateReservationDto();
        reservationDto.setGroupSize(3);
        reservationDto.setDate("2025-10-20");
        reservationDto.setStartTime("19:30");
        
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Jane Smith");
        reservationDto.setCustomer(customerDto);
        
        CustomerDto mockCustomerDto = new CustomerDto();
        ReservationTimeDto mockTimeDto = new ReservationTimeDto();
        
        when(customerFactory.create(customerDto)).thenReturn(mockCustomerDto);
        when(reservationTimeFactory.create("19:30", restaurant)).thenReturn(mockTimeDto);

        reservationFactory.createReservation(reservationDto, restaurant);

        verify(customerFactory).create(customerDto);
        verify(reservationTimeFactory).create("19:30", restaurant);
    }
}