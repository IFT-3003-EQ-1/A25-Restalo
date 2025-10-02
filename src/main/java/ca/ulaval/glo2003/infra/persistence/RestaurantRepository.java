package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.entities.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
    void save(Restaurant restaurant);

    Optional<Restaurant> get(String id);

    List<Restaurant> listParProprietaire(String proprietaireId);
}
