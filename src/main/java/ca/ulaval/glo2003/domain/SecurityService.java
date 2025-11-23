package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.entities.RestaurantRepository;
import ca.ulaval.glo2003.entities.exceptions.ForbiddenAccessException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import com.google.common.base.Strings;

public class SecurityService {

    private final RestaurantRepository restaurantRepository;

    public SecurityService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public boolean hasAccess(String idRestaurant, String ownerId) {
        if (Strings.isNullOrEmpty(ownerId)) {
            throw new MissingParameterException("Owner");
        }

        Restaurant restaurant = restaurantRepository.get(idRestaurant).orElseThrow(
                () -> new NotFoundException("")
        );

        if (!restaurant.getOwner().getId().equals(ownerId)) {
            throw new ForbiddenAccessException("");
        }
        return true;
    }

}
