package ca.ulaval.glo2003.entities.filters;

import ca.ulaval.glo2003.entities.reservation.Reservation;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

public class FilterReservationFactory {

    public List<Filter<Reservation>> createFilters(String customerName,
                                                   String reservationDate,
                                                   String restaurantId,
                                                   String ownerId) {
        List<Filter<Reservation>> filters = new ArrayList<>();
        if (!Strings.isNullOrEmpty(customerName)) {
            filters.add(reservation ->
                    reservation.getCustomer().getName().toLowerCase().startsWith(customerName.toLowerCase()));
        }

        if (!Strings.isNullOrEmpty(reservationDate)) {
            filters.add(reservation -> reservation.getDate().equals(reservationDate));
        }

        filters.add(reservation -> reservation.getRestaurant().getId().equals(restaurantId));
        filters.add( reservation -> reservation.getRestaurant().getOwner().getId().equals(ownerId));

        return filters;
    }
}
