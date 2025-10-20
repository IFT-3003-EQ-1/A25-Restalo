package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.CreateReservationDto;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.entities.Reservation;
import ca.ulaval.glo2003.entities.Restaurant;
import ca.ulaval.glo2003.entities.assemblers.ReservationAssembler;
import ca.ulaval.glo2003.entities.assemblers.ReservationFactory;
import ca.ulaval.glo2003.infra.persistence.ReservationRepository;
import ca.ulaval.glo2003.infra.persistence.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceUnitTest {

    @Mock
    private RestaurantRepository restaurantRepository;
    
    @Mock
    private ReservationRepository reservationRepository;
    
    @Mock
    private ReservationFactory reservationFactory;
    
    @Mock
    private ReservationAssembler reservationAssembler;

    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        reservationService = new ReservationService(
            restaurantRepository,
            reservationFactory,
            reservationRepository,
            reservationAssembler
        );
    }

    @Test
    public void addReservation_shouldCreateAndSaveReservation_whenRestaurantExists() {
        String restaurantId = "restaurant-123";
        CreateReservationDto createDto = new CreateReservationDto();
        
        Restaurant mockRestaurant = mock(Restaurant.class);
        Reservation mockReservation = mock(Reservation.class);
        String expectedReservationNumber = "RES-456";
        
        when(restaurantRepository.get(restaurantId)).thenReturn(Optional.of(mockRestaurant));
        when(reservationFactory.createReservation(createDto, mockRestaurant)).thenReturn(mockReservation);
        when(mockReservation.getNumber()).thenReturn(expectedReservationNumber);

        String result = reservationService.addReservation(restaurantId, createDto);

        assertEquals(expectedReservationNumber, result);
        verify(restaurantRepository).get(restaurantId);
        verify(reservationFactory).createReservation(createDto, mockRestaurant);
        verify(reservationRepository).save(mockReservation);
    }

    @Test
    public void addReservation_shouldThrowNotFoundException_whenRestaurantDoesNotExist() {
        String restaurantId = "non-existent";
        CreateReservationDto createDto = new CreateReservationDto();
        
        when(restaurantRepository.get(restaurantId)).thenReturn(Optional.empty());

        try {
            reservationService.addReservation(restaurantId, createDto);
            fail("Expected NotFoundException to be thrown");
        } catch (Exception e) {
            assertEquals("Restaurant not found", e.getMessage());
        }
        
        verify(restaurantRepository).get(restaurantId);
        verify(reservationFactory, never()).createReservation(any(), any());
        verify(reservationRepository, never()).save(any());
    }

    @Test
    public void getReservation_shouldReturnReservationDto_whenReservationExists() {
        String reservationId = "reservation-789";
        Reservation mockReservation = mock(Reservation.class);
        ReservationDto expectedDto = new ReservationDto();
        
        when(reservationRepository.get(reservationId)).thenReturn(Optional.of(mockReservation));
        when(reservationAssembler.toDto(mockReservation)).thenReturn(expectedDto);

        ReservationDto result = reservationService.getReservation(reservationId);

        assertEquals(expectedDto, result);
        verify(reservationRepository).get(reservationId);
        verify(reservationAssembler).toDto(mockReservation);
    }

    @Test
    public void getReservation_shouldThrowNotFoundException_whenReservationDoesNotExist() {
        String reservationId = "non-existent";
        
        when(reservationRepository.get(reservationId)).thenReturn(Optional.empty());

        try {
            reservationService.getReservation(reservationId);
            fail("Expected NotFoundException to be thrown");
        } catch (Exception e) {
            assertEquals("Reservation not found", e.getMessage());
        }
        
        verify(reservationRepository).get(reservationId);
        verify(reservationAssembler, never()).toDto(any());
    }
}