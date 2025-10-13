package ca.ulaval.glo2003.entities.filtres;

import ca.ulaval.glo2003.entities.Restaurant;

import java.time.LocalTime;
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
                LocalTime from =  LocalTime.parse(horaireOuverture);
                LocalTime actual = LocalTime.parse(restaurant.getHoraireOuverture());
                return from.isBefore(actual);
            }));
        }

        if (horaireFermeture != null && !horaireFermeture.isBlank()) {
            filtres.add(((restaurant) -> {
                LocalTime to =  LocalTime.parse(horaireOuverture);
                LocalTime actual = LocalTime.parse(restaurant.getHoraireOuverture());
                return to.isAfter(actual);
            }));
        }
        return filtres;
    }
}
