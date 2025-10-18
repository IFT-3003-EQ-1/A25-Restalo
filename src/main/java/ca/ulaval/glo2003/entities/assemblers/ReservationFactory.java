package ca.ulaval.glo2003.entities.assemblers;

import java.util.UUID;

import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.Reservation;
import ca.ulaval.glo2003.entities.ReservationTime;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.exceptions.ParametreInvalideException;
import ca.ulaval.glo2003.entities.exceptions.ParametreManquantException;
import com.google.common.base.Strings;


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

        if(reservationDto.groupSize < 1){
            throw new ParametreInvalideException("Group size must be at least 1");
        }
        if(Strings.isNullOrEmpty(reservationDto.date)){
            throw new ParametreManquantException("Reservation date");
        }

        Customer customer = customerFactory.create(reservationDto.customer);
        ReservationTime time = reservationTimeFactory.create(reservationDto.startTime, restaurant);
        String reservationId = UUID.randomUUID().toString().replace("-", "");

        return new Reservation(reservationId, reservationDto.date, time, reservationDto.groupSize, customer, restaurant);
    }
}
