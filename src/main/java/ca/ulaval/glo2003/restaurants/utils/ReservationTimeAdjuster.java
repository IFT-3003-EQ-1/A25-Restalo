package ca.ulaval.glo2003.restaurants.utils;
import java.time.LocalTime;
import java.time.LocalDate;
import ca.ulaval.glo2003.restaurants.domain.ReservationTime;
import ca.ulaval.glo2003.restaurants.domain.Restaurant;





public class ReservationTimeAdjuster {
    public static ReservationTime adjustAndValidateReservationTime(
        String startTime, 
        Restaurant restaurant
    ) {
        LocalTime requestedStartTime = LocalTime.parse(startTime);
        LocalTime adjustedStartTime = adjustToNext15Minutes(requestedStartTime);
        LocalTime endTime = adjustedStartTime.plusMinutes(restaurant.getReservationDuration());
        
        // 4. Parser l'heure de fermeture
        LocalTime closingTime = LocalTime.parse(restaurant.getHours().getClose());
        if (endTime.isAfter(closingTime)) {
            return null;
        }
        return new ReservationTime(adjustedStartTime.toString(), endTime.toString());
    }
    
    /**
     * Ajuste l'heure à la prochaine tranche de 15 minutes.
     * Si déjà à une tranche de 15 minutes, retourne la même heure.
     * 
     * Exemples:
     * - 21:04:00 -> 21:15:00
     * - 21:15:00 -> 21:15:00 (pas de changement)
     * - 21:16:00 -> 21:30:00
     * - 21:46:00 -> 22:00:00
     */
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
