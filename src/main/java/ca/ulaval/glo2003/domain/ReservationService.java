package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.entities.Reservation;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.assemblers.ReservationFactory;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.infra.persistence.ReservationRepository;
import ca.ulaval.glo2003.infra.persistence.RestaurantRepository;

public class ReservationService {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationFactory reservationFactory;

    public ReservationService(
            RestaurantRepository restaurantRepository,
            ReservationFactory reservationFactory,
            ReservationRepository reservationRepository
    )
    {
        this.restaurantRepository = restaurantRepository;
        this.reservationFactory = reservationFactory;
        this.reservationRepository = reservationRepository;
    }

    public String addReservation(String restaurantId, ReservationDto createReservationDto) {
        Restaurant restaurant = restaurantRepository.get(restaurantId).orElseThrow(
                () -> new NotFoundException("Restaurant not found")
        );

        Reservation reservation = reservationFactory.createReservation(createReservationDto, restaurant);
        reservationRepository.save(reservation);
        return reservation.getNumber();
    }

    public Reservation getReservation(String reservationId) {
        return reservationRepository.get(reservationId).orElseThrow(
                () -> new NotFoundException("Reservation not found")
        );
    }
}
