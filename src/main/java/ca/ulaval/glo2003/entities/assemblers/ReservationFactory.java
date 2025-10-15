package ca.ulaval.glo2003.entities.assemblers;

import java.util.UUID;

import ca.ulaval.glo2003.domain.dtos.CreateReservationDto;
import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.domain.dtos.ReservationTimeDto;
import ca.ulaval.glo2003.entities.Reservation;
import ca.ulaval.glo2003.entities.Restaurant;
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

    public Reservation createReservation(CreateReservationDto reservationDto, Restaurant restaurant) {

        if(reservationDto.getGroupSize() < 1){
            throw new ParametreInvalideException("Group size must be at least 1");
        }
        if(Strings.isNullOrEmpty(reservationDto.getDate())){
            throw new ParametreManquantException("Reservation date");
        }

        CustomerDto customer = customerFactory.create(reservationDto.getCustomer());
        System.out.println("In Reservation Factory-customer: "+ customer.toString());
        ReservationTimeDto time = reservationTimeFactory.create(reservationDto.getStartTime(), restaurant);
        System.out.println("In Reservation Factory-time: "+ time.toString());
        String reservationId = UUID.randomUUID().toString().replace("-", "");

        Reservation reservation = new Reservation();
        reservation.setNumber(reservationId);
        reservation.setTime(time);
        reservation.setRestaurant(restaurant);
        reservation.setCustomer(customer);
        reservation.setGroupSize(reservationDto.getGroupSize());
        reservation.setDate(reservationDto.getDate());
        System.out.println("In Reservation Factory-reservation: "+ reservation.toString());
        return  reservation;
    }
}
