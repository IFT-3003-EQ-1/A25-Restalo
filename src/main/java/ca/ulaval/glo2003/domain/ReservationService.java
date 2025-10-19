package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.CreateReservationDto;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.entities.Reservation;
import ca.ulaval.glo2003.entities.Restaurant;
import ca.ulaval.glo2003.entities.assemblers.ReservationAssembler;
import ca.ulaval.glo2003.entities.assemblers.ReservationFactory;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.infra.persistence.ReservationRepository;
import ca.ulaval.glo2003.infra.persistence.RestaurantRepository;

public class ReservationService {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationFactory reservationFactory;
    private final ReservationAssembler reservationAssembler;

    public ReservationService(
            RestaurantRepository restaurantRepository,
            ReservationFactory reservationFactory,
            ReservationRepository reservationRepository,
            ReservationAssembler reservationAssembler
    )
    {
        this.restaurantRepository = restaurantRepository;
        this.reservationFactory = reservationFactory;
        this.reservationRepository = reservationRepository;
        this.reservationAssembler = reservationAssembler;
    }

    public String addReservation(String restaurantId, CreateReservationDto createReservationDto) {
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
}
