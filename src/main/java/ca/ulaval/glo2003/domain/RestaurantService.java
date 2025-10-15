package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.*;
import ca.ulaval.glo2003.entities.Proprietaire;
import ca.ulaval.glo2003.entities.Reservation;
import ca.ulaval.glo2003.entities.Restaurant;
import ca.ulaval.glo2003.entities.assemblers.*;
import ca.ulaval.glo2003.entities.exceptions.AccessInterditException;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.infra.persistence.RestaurantRepository;

import java.util.List;
import java.util.UUID;

public class RestaurantService {

    private final RestaurantFactory restaurantFactory;
    private final RestaurantRepository restaurantRepository;
    private final ProprietaireFactory proprietaireFactory;
    private final RestaurantAssembler restaurantAssembler;

    public RestaurantService(
            RestaurantFactory restaurantFactory,
            RestaurantRepository restaurantRepository,
            ProprietaireFactory proprietaireFactory,
            RestaurantAssembler restaurantAssembler) {
        this.restaurantFactory = restaurantFactory;
        this.restaurantRepository = restaurantRepository;
        this.proprietaireFactory = proprietaireFactory;
        this.restaurantAssembler = restaurantAssembler;
    }

    public String createRestaurant(ProprietaireDto proprietaireDto, RestaurantDto restaurantDto) {
        Proprietaire proprietaire = proprietaireFactory.createProprietaire(proprietaireDto.id);
        Restaurant restaurant = restaurantFactory.createRestaurant(
                proprietaire,
                restaurantDto.nom,
                restaurantDto.capacite,
                restaurantDto.horaireOuverture,
                restaurantDto.horaireFermeture
        );
        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

    public RestaurantDto getRestaurant(String idRestaurant, String proprietaireId) {

        Restaurant restaurant = restaurantRepository.get(idRestaurant).orElseThrow(
                () -> new NotFoundException("")
        );

        if (!restaurant.getProprietaire().getId().equals(proprietaireId)) {
            throw new AccessInterditException("");
        }
        return restaurantAssembler.toDto(restaurant);
    }

    public List<RestaurantDto> getRestaurants(String proprietaireId) {

        return restaurantRepository
                .listParProprietaire(proprietaireId)
                .stream()
                .map(restaurantAssembler::toDto)
                .toList();
    }
}
