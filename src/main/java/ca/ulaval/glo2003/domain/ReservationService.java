package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.assemblers.ReservationAssembler;
import ca.ulaval.glo2003.entities.exceptions.ForbiddenAccessException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.filters.Filter;
import ca.ulaval.glo2003.entities.filters.FilterReservationFactory;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.reservation.ReservationFactory;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.reservation.ReservationRepository;
import ca.ulaval.glo2003.entities.restaurant.RestaurantRepository;
import com.google.common.base.Strings;
import io.sentry.Sentry;

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
    ) {
        this.restaurantRepository = restaurantRepository;
        this.reservationFactory = reservationFactory;
        this.reservationRepository = reservationRepository;
        this.reservationAssembler = reservationAssembler;
        this.filterFactory = filterFactory;
    }

    public String addReservation(String restaurantId, ReservationDto createReservationDto) {
        // Ajouter du contexte pour Sentry
        Sentry.setExtra("operation", "addReservation");
        Sentry.setExtra("restaurantId", restaurantId);
        Sentry.setExtra("groupSize", String.valueOf(createReservationDto.groupSize));
        Sentry.setExtra("date", createReservationDto.date);
        Sentry.setExtra("startTime", createReservationDto.time != null ? createReservationDto.time.start : null);

        Restaurant restaurant = restaurantRepository.get(restaurantId).orElseThrow(
                () -> new NotFoundException("Restaurant not found")
        );

        Reservation reservation = reservationFactory.createReservation(createReservationDto, restaurant);
        reservationRepository.save(reservation);

        return reservation.getNumber();
    }

    public ReservationDto getReservation(String reservationId) {
        // Ajouter du contexte pour Sentry
        Sentry.setExtra("operation", "getReservation");
        Sentry.setExtra("reservationId", reservationId);

        Reservation reservation = reservationRepository.get(reservationId).orElseThrow(
                () -> new NotFoundException("Reservation not found")
        );

        return reservationAssembler.toDto(reservation);
    }

    public boolean deleteReservation(String reservationNumber) {
        // Ajouter du contexte pour Sentry
        Sentry.setExtra("operation", "deleteReservation");
        Sentry.setExtra("reservationNumber", reservationNumber);

        boolean isDeleted = reservationRepository.delete(reservationNumber);

        if (!isDeleted) {
            throw new NotFoundException("Reservation not found");
        }

        return true;
    }

    public List<ReservationDto> findBySearchCriteria(RestaurantDto restaurantDto, String customerName, String reservationData) {
        // Ajouter du contexte pour Sentry
        Sentry.setExtra("operation", "findBySearchCriteria");
        Sentry.setExtra("restaurantId", restaurantDto.id);
        Sentry.setExtra("ownerId", restaurantDto.owner != null ? restaurantDto.owner.id : null);
        Sentry.setExtra("customerName", customerName);
        Sentry.setExtra("reservationData", reservationData);
        Sentry.setExtra("hasCustomerFilter", String.valueOf(!Strings.isNullOrEmpty(customerName)));
        Sentry.setExtra("hasDateFilter", String.valueOf(!Strings.isNullOrEmpty(reservationData)));

        if (Strings.isNullOrEmpty(reservationData) && Strings.isNullOrEmpty(customerName)) {
            List<Reservation> allReservations = reservationRepository.getAll();
            Sentry.setExtra("totalReservations", String.valueOf(allReservations.size()));
            return allReservations.stream().map(reservationAssembler::toDto).toList();
        } else {
            List<Filter<Reservation>> filters =
                    filterFactory.createFilters(customerName, reservationData, restaurantDto.id, restaurantDto.owner.id);

            Sentry.setExtra("filtersCount", String.valueOf(filters.size()));

            List<Reservation> reservations = reservationRepository.search(filters);

            Sentry.setExtra("reservationsFound", String.valueOf(reservations.size()));

            if (reservations.isEmpty()) {
                throw new NotFoundException("Reservation not found");
            }

            return reservations.stream().map(reservationAssembler::toDto).toList();
        }
    }
}