package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
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
import ca.ulaval.glo2003.infra.persistence.RestaurantRepository;

import java.util.List;

public class RestaurantService {

    private final RestaurantFactory restaurantFactory;
    private final RestaurantRepository restaurantRepository;
    private final OwnerFactory proprietaireFactory;
    private final RestaurantAssembler restaurantAssembler;
    private final FilterRestaurantFactory filtreRestaurantFactory;

    public RestaurantService(
            RestaurantFactory restaurantFactory,
            RestaurantRepository restaurantRepository,
            OwnerFactory proprietaireFactory,
            RestaurantAssembler restaurantAssembler, FilterRestaurantFactory filtreRestaurantFactory) {
        this.restaurantFactory = restaurantFactory;
        this.restaurantRepository = restaurantRepository;
        this.proprietaireFactory = proprietaireFactory;
        this.restaurantAssembler = restaurantAssembler;
        this.filtreRestaurantFactory = filtreRestaurantFactory;
    }

    public String createRestaurant(OwnerDto proprietaireDto, RestaurantDto restaurantDto) {
        Owner proprietaire = proprietaireFactory.createProprietaire(proprietaireDto.id);
        Restaurant restaurant = restaurantFactory.createRestaurant(
                proprietaire,
                restaurantDto
        );
        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

    public RestaurantDto getRestaurant(String idRestaurant, String proprietaireId) {

        if (proprietaireId == null || proprietaireId.isBlank()) {
            throw new MissingParameterException("Owner");
        }

        Restaurant restaurant = restaurantRepository.get(idRestaurant).orElseThrow(
                () -> new NotFoundException("")
        );

        if (!restaurant.getOwner().getId().equals(proprietaireId)) {
            throw new ForbiddenAccessException("");
        }
        return restaurantAssembler.toDto(restaurant);
    }

    public List<RestaurantDto> getRestaurants(String proprietaireId) {

        if (proprietaireId == null || proprietaireId.isBlank()) {
            throw new MissingParameterException("Owner");
        }

        return restaurantRepository
                .listParProprietaire(proprietaireId)
                .stream()
                .map(restaurantAssembler::toDto)
                .toList();
    }

    public List<RestaurantDto> searchRestaurants(RestaurantDto searchValues) {

        List<Filter<Restaurant>> filtres = filtreRestaurantFactory.createFiltres(searchValues.name, searchValues.hoursOpen, searchValues.hoursClose);
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
}
