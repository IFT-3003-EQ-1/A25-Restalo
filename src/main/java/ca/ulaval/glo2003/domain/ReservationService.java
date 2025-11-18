package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.entities.assemblers.ReservationAssembler;
import ca.ulaval.glo2003.entities.exceptions.ForbiddenAccessException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.filters.Filter;
import ca.ulaval.glo2003.entities.filters.FilterReservationFactory;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.reservation.ReservationFactory;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.ReservationRepository;
import ca.ulaval.glo2003.entities.RestaurantRepository;

import java.util.List;

public class ReservationService {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationFactory reservationFactory;
    private final ReservationAssembler reservationAssembler;
    private final FilterReservationFactory filterFactory;

    public ReservationService(
            RestaurantRepository restaurantRepository,
            ReservationFactory reservationFactory,
            ReservationRepository reservationRepository,
            ReservationAssembler reservationAssembler,
            FilterReservationFactory filterFactory
    )
    {
        this.restaurantRepository = restaurantRepository;
        this.reservationFactory = reservationFactory;
        this.reservationRepository = reservationRepository;
        this.reservationAssembler = reservationAssembler;
        this.filterFactory = filterFactory;
    }

    public String addReservation(String restaurantId, ReservationDto createReservationDto) {
        Restaurant restaurant = restaurantRepository.get(restaurantId).orElseThrow(
                () -> new NotFoundException("Restaurant not found")
        );

        Reservation reservation = reservationFactory.createReservation(createReservationDto, restaurant);
        reservationRepository.save(reservation);
        return reservation.getNumber();
    }

    public ReservationDto getReservation(String reservationId) {
        Reservation reservation =  reservationRepository.get(reservationId).orElseThrow(
                () -> new NotFoundException("Reservation not found")
        );

        return reservationAssembler.toDto(reservation);
    }

    public boolean deleteReservation(String reservationNumber) {
        boolean isDeleted = reservationRepository.delete(reservationNumber);
        if(!isDeleted)
            throw new NotFoundException("Reservation not found");
        return true;
    }

    public List<ReservationDto> findBySearchCriteria(String ownerId, String customerName, String reservationData, String restaurantId) {

        if (ownerId == null || ownerId.isBlank()) {
            throw new MissingParameterException("Owner");
        }

        Restaurant restaurant = restaurantRepository.get(restaurantId)
                .orElseThrow(() -> new NotFoundException("le restaurant n'existe pas"));

        if (!restaurant.getOwner().getId().equals(ownerId)) {
            throw new ForbiddenAccessException("le restaurant n'appartient pas au restaurateur");
        }

        List<Filter<Reservation>> filters =
                filterFactory.createFilters(customerName, reservationData, restaurantId, ownerId);

        List<Reservation> reservations = reservationRepository.search(filters);
        if (reservations.isEmpty())
            throw new NotFoundException("Reservation not found");

        return reservations.stream().map(reservationAssembler::toDto).toList();

    }
}
