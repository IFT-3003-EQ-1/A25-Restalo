package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.ReservationTimeDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.HourDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.filters.FilterReservationFactory;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.reservation.ReservationTime;
import ca.ulaval.glo2003.entities.restaurant.ConfigReservation;
import ca.ulaval.glo2003.entities.restaurant.Hours;
import ca.ulaval.glo2003.entities.restaurant.Owner;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.assemblers.ReservationAssembler;
import ca.ulaval.glo2003.entities.reservation.ReservationFactory;
import ca.ulaval.glo2003.entities.reservation.ReservationRepository;
import ca.ulaval.glo2003.entities.restaurant.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Mock
    private FilterReservationFactory filterFactory;

    private ReservationService reservationService;

    private Reservation reservation;

    private Restaurant restaurant;

    private ReservationDto reservationDto;

    private RestaurantDto restaurantDto;

    @BeforeEach
    public void setUp() {
        reservationService = new ReservationService(
                restaurantRepository,
                reservationFactory,
                reservationRepository,
                reservationAssembler,
                filterFactory
        );


        restaurantDto = DomainTestUtils.getRestaurantDto();

        reservationDto = DomainTestUtils.getReservationDto();

        restaurant = DomainTestUtils.getRestaurant();

        reservation = DomainTestUtils.getReservation();
    }

    @Test
    public void addReservation_shouldCreateAndSaveReservation_whenRestaurantExists() {
        String restaurantId = "restaurant-123";
        ReservationDto createDto = new ReservationDto();
        
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
        ReservationDto createDto = new ReservationDto();
        
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

    @Test
    public void givenDeleteReservation_whenParameterIsValid_thenReturnTrue() {
        when(reservationRepository.delete(any())).thenReturn(true);
        assertTrue(reservationService.deleteReservation("reservation-123"));
    }

    @Test
    public void givenDeleteReservation_whenReservationDoesntExist_thenThrowNotFoundException() {
        when(reservationRepository.delete(any())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> reservationService.deleteReservation("non-existent"));
    }

    @Test
    public void givenFindBySearchCriteria_whenParameterAreValid_thenReturnReservationDtos() {
        List<ReservationDto> expected = new ArrayList<>();
        expected.add(reservationDto);

        when(filterFactory.createFilters(
                reservation.getCustomer().getName(),
                reservation.getDate(),
                restaurant.getId(),
                restaurant.getOwner().getId()
        )).thenReturn(List.of(r->true));

        when(reservationRepository.search(any())).thenReturn(List.of(reservation));

        assertEquals(expected.size(), reservationService.findBySearchCriteria(
                restaurantDto,
                reservationDto.customer.name,
                reservationDto.date
        ).size());
    }

    @Test
    public void givenFindBySearchCriteria_whenNoFilter_thenReturnAllReservations() {
        List<ReservationDto> expected = new ArrayList<>();
        expected.add(reservationDto);

        when(reservationRepository.getAll()).thenReturn(List.of(reservation));

        assertEquals(expected.size(), reservationService.findBySearchCriteria(
                restaurantDto,
                null,
                null
        ).size());
    }

    @Test
    public void givenFindBySearchCriteria_whenNoMatchingResult_thenThrowNotFoundException() {
        List<ReservationDto> expected = new ArrayList<>();
        expected.add(reservationDto);

        when(filterFactory.createFilters(
                reservation.getCustomer().getName(),
                reservation.getDate(),
                restaurant.getId(),
                restaurant.getOwner().getId()
        )).thenReturn(new  ArrayList<>());

        when(reservationRepository.search(any())).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> reservationService.findBySearchCriteria(
                restaurantDto,
                reservationDto.customer.name,
                reservationDto.date
        ));
    }

}