package ca.ulaval.glo2003.entities.reservation;

import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import com.google.common.base.Strings;

import java.time.LocalTime;

public class ReservationTimeFactory {
    public ReservationTime create(String startTime, Restaurant restaurant) {
        if(Strings.isNullOrEmpty(startTime)) {
            throw new MissingParameterException("Reservation Start time");
        }

        ReservationTime time = adjustAndValidateReservationTime(startTime,restaurant);
        if(time == null) {
            throw new InvalideParameterException("Reservation Start time is too late");
        }
        return time;
    }

    private ReservationTime adjustAndValidateReservationTime(
            String startTime,
            Restaurant restaurant
    ) {
        LocalTime requestedStartTime = LocalTime.parse(startTime);
        LocalTime adjustedStartTime = adjustToNext15Minutes(requestedStartTime);
        LocalTime endTime = adjustedStartTime.plusMinutes(restaurant.getReservationDuration());

        LocalTime closingTime = LocalTime.parse(restaurant.getHours().close);
        // Pour le moment on ne tient pas compte de la durée de reervation.
        if (adjustedStartTime.isAfter(closingTime) ||  adjustedStartTime.equals(closingTime)) {
            return null;
        }
        return new ReservationTime(adjustedStartTime.toString(), endTime.toString());
    }

    private static LocalTime adjustToNext15Minutes(LocalTime time) {
        int minute = time.getMinute();
        int remainder = minute % 15;

        if (remainder == 0) {
            // Déjà à une tranche de 15 minutes
            return time.withSecond(0).withNano(0);
        }

        // Calculer les minutes à ajouter
        int minutesToAdd = 15 - remainder;

        // Retourner l'heure ajustée (secondes et nanosecondes à zéro)
        return time.plusMinutes(minutesToAdd).withSecond(0).withNano(0);
    }
}
