package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.ReservationRepository;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import ca.ulaval.glo2003.entities.restaurant.Owner;
import ca.ulaval.glo2003.entities.restaurant.OwnerFactory;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.assemblers.*;
import ca.ulaval.glo2003.entities.exceptions.ForbiddenAccessException;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.filters.Filter;
import ca.ulaval.glo2003.entities.filters.FilterRestaurantFactory;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.entities.RestaurantRepository;

import java.util.List;

public class RestaurantService {

    private final RestaurantFactory restaurantFactory;
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final OwnerFactory ownerFactory;
    private final RestaurantAssembler restaurantAssembler;
    private final FilterRestaurantFactory filterRestaurantFactory;

    public RestaurantService(
            RestaurantFactory restaurantFactory,
            RestaurantRepository restaurantRepository,
            ReservationRepository reservationRepository,
            OwnerFactory ownerFactory,
            RestaurantAssembler restaurantAssembler, FilterRestaurantFactory filterRestaurantFactory) {
        this.restaurantFactory = restaurantFactory;
        this.restaurantRepository = restaurantRepository;
        this.reservationRepository = reservationRepository;
        this.ownerFactory = ownerFactory;
        this.restaurantAssembler = restaurantAssembler;
        this.filterRestaurantFactory = filterRestaurantFactory;
    }

    public String createRestaurant(OwnerDto ownerDto, RestaurantDto restaurantDto) {
        Owner owner = ownerFactory.createOwner(ownerDto.id);
        Restaurant restaurant = restaurantFactory.createRestaurant(
                owner,
                restaurantDto
        );
        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

    public List<RestaurantDto> getRestaurants(String ownerId) {

        if (ownerId == null || ownerId.isBlank()) {
            throw new MissingParameterException("Owner");
        }

        return restaurantRepository
                .listByOwner(ownerId)
                .stream()
                .map(restaurantAssembler::toDto)
                .toList();
    }

    public List<RestaurantDto> searchRestaurants(RestaurantDto searchValues) {

        List<Filter<Restaurant>> filtres = filterRestaurantFactory.createFilters(searchValues.name, searchValues.hours.open, searchValues.hours.close);
        if (filtres.isEmpty()) {
            return restaurantRepository
                    .getAll()
                    .stream()
                    .map(restaurantAssembler::toDto)
                    .toList();
        } else {
            return restaurantRepository
                    .searchRestaurants(filtres)
                    .stream()
                    .map(restaurantAssembler::toDto)
                    .toList();
        }

    }

    public boolean deleteRestaurant(String restaurantId, String ownerId) {
        if (ownerId == null || ownerId.isBlank()) {
            throw new MissingParameterException("Owner");
        }

        Restaurant restaurant = restaurantRepository.get(restaurantId)
                .orElseThrow(() -> new NotFoundException("le restaurant n'existe pas"));

        if (!restaurant.getOwner().getId().equals(ownerId)) {
            throw new ForbiddenAccessException("le restaurant n'appartient pas au restaurateur");
        }

        reservationRepository.deleteRelatedReservations(restaurantId);
        return restaurantRepository.delete(restaurantId);
    }
}
