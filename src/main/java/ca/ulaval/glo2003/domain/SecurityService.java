package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.exceptions.ForbiddenAccessException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import com.google.common.base.Strings;

public class SecurityService {

    private final RestaurantRepository restaurantRepository;

    private final RestaurantAssembler assembler;

    public SecurityService(RestaurantRepository restaurantRepository, RestaurantAssembler assembler) {
        this.restaurantRepository = restaurantRepository;
        this.assembler = assembler;
    }

    public RestaurantDto getRestaurant(String idRestaurant, String ownerId) {
        if (Strings.isNullOrEmpty(ownerId)) {
            throw new MissingParameterException("Owner");
        }

        Restaurant restaurant = restaurantRepository.get(idRestaurant).orElseThrow(
                () -> new NotFoundException("")
        );

        if (!restaurant.getOwner().getId().equals(ownerId)) {
            throw new ForbiddenAccessException("");
        }

        return assembler.toDto(restaurant);
    }

}
