package ca.ulaval.glo2003.entities.reservation;

import java.util.UUID;

import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.CustomerFactory;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import com.google.common.base.Strings;
import io.sentry.Sentry;

public class ReservationFactory {
    private final CustomerFactory customerFactory;
    private final ReservationTimeFactory reservationTimeFactory;

    public ReservationFactory(
            CustomerFactory customerFactory,
            ReservationTimeFactory reservationTimeFactory
    ) {
        this.customerFactory = customerFactory;
        this.reservationTimeFactory = reservationTimeFactory;
    }

    public Reservation createReservation(ReservationDto reservationDto, Restaurant restaurant) {
        // Ajouter du contexte pour Sentry (sera inclus si une erreur se produit)
        Sentry.setExtra("operation", "createReservation");
        Sentry.setExtra("groupSize", String.valueOf(reservationDto.groupSize));
        Sentry.setExtra("date", reservationDto.date);
        Sentry.setExtra("restaurantId", restaurant.getId());
        Sentry.setExtra("startTime", reservationDto.time != null ? reservationDto.time.start : null);

        // Validation de la taille du groupe
        if (reservationDto.groupSize < 1) {
            throw new InvalideParameterException("Group size must be at least 1");
        }

        // Validation de la date
        if (Strings.isNullOrEmpty(reservationDto.date)) {
            throw new MissingParameterException("Reservation date");
        }

        Customer customer = customerFactory.create(reservationDto.customer);
        ReservationTime time = reservationTimeFactory.create(
                reservationDto.time.start,
                restaurant.getHours().getClose(),
                restaurant.getReservationDuration()
        );

        String reservationId = UUID.randomUUID().toString().replace("-", "");

        return new Reservation(
                reservationId,
                reservationDto.date,
                time,
                reservationDto.groupSize,
                customer,
                restaurant
        );
    }
}