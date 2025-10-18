package ca.ulaval.glo2003.entities.filtres;

import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FiltreRestaurantFactory {

    public List<Filtre<Restaurant>> createFiltres(String nom, String horaireOuverture, String horaireFermeture) {
        List<Filtre<Restaurant>> filtres = new ArrayList<>();
        if (nom != null && !nom.isBlank()) {
            filtres.add((restaurant)
                    -> restaurant.getNom().toLowerCase().trim()
                        .contains(nom.toLowerCase().trim())
            );
        }

        if (horaireOuverture != null && !horaireOuverture.isBlank()) {
            filtres.add(((restaurant) -> {
                try {
                    LocalTime from =  LocalTime.parse(horaireOuverture);
                    LocalTime actual = LocalTime.parse(restaurant.getHoraireOuverture());
                    return from.isBefore(actual);
                } catch (DateTimeParseException e) {
                    throw new InvalideParameterException("`horaires.*` doivent respecter le format HH:mm:ss");
                }
            }));
        }

        if (horaireFermeture != null && !horaireFermeture.isBlank()) {
            filtres.add(((restaurant) -> {
                try {
                    LocalTime to =  LocalTime.parse(horaireFermeture);
                    LocalTime actual = LocalTime.parse(restaurant.getHoraireFermeture());
                    return to.isAfter(actual);

                } catch (DateTimeParseException e) {
                    throw new InvalideParameterException("`horaires.*` doivent respecter le format HH:mm:ss");
                }
            }));
        }
        return filtres;
    }
}
