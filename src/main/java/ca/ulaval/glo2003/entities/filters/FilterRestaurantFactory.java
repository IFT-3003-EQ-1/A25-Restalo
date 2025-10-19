package ca.ulaval.glo2003.entities.filters;

import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FilterRestaurantFactory {

    public List<Filter<Restaurant>> createFiltres(String name, String hoursOpen, String hoursClose) {
        List<Filter<Restaurant>> filtres = new ArrayList<>();
        if (name != null && !name.isBlank()) {
            filtres.add((restaurant)
                    -> restaurant.getName().toLowerCase().trim()
                        .contains(name.toLowerCase().trim())
            );
        }

        if (hoursOpen != null && !hoursOpen.isBlank()) {
            filtres.add(((restaurant) -> {
                try {
                    LocalTime from =  LocalTime.parse(hoursOpen);
                    LocalTime actual = LocalTime.parse(restaurant.getHoursOpen());
                    return from.isBefore(actual);
                } catch (DateTimeParseException e) {
                    throw new InvalideParameterException("`horaires.*` doivent respecter le format HH:mm:ss");
                }
            }));
        }

        if (hoursClose != null && !hoursClose.isBlank()) {
            filtres.add(((restaurant) -> {
                try {
                    LocalTime to =  LocalTime.parse(hoursClose);
                    LocalTime actual = LocalTime.parse(restaurant.getHoursClose());
                    return to.isAfter(actual);

                } catch (DateTimeParseException e) {
                    throw new InvalideParameterException("`horaires.*` doivent respecter le format HH:mm:ss");
                }
            }));
        }
        return filtres;
    }
}
