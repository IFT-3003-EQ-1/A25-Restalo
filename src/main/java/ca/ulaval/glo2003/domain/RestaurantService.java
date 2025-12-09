package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.SalesDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.OwnerDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.Sales;
import ca.ulaval.glo2003.entities.SalesRepository;
import ca.ulaval.glo2003.entities.reservation.ReservationRepository;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import ca.ulaval.glo2003.entities.restaurant.Owner;
import ca.ulaval.glo2003.entities.restaurant.OwnerFactory;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.assemblers.*;
import ca.ulaval.glo2003.entities.filters.Filter;
import ca.ulaval.glo2003.entities.filters.FilterRestaurantFactory;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.entities.restaurant.RestaurantRepository;

import java.util.List;

public class RestaurantService {

    private final RestaurantFactory restaurantFactory;
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final SalesRepository salesRepository;
    private final OwnerFactory ownerFactory;
    private final RestaurantAssembler restaurantAssembler;
    private final FilterRestaurantFactory filterRestaurantFactory;

    public RestaurantService(
            RestaurantFactory restaurantFactory,
            RestaurantRepository restaurantRepository,
            ReservationRepository reservationRepository,
            SalesRepository salesRepository,
            OwnerFactory ownerFactory,
            RestaurantAssembler restaurantAssembler,
            FilterRestaurantFactory filterRestaurantFactory) {
        this.restaurantFactory = restaurantFactory;
        this.restaurantRepository = restaurantRepository;
        this.reservationRepository = reservationRepository;
        this.salesRepository = salesRepository;
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

    public boolean deleteRestaurant(String restaurantId) {
        reservationRepository.deleteRelatedReservations(restaurantId);
        return restaurantRepository.delete(restaurantId);
    }

    public String createSalesReport(SalesDto salesDto, RestaurantDto restaurantDto) {
        Sales sales = restaurantFactory.createSalesReport(salesDto, restaurantFactory.fromDto(restaurantDto));
        salesRepository.saveSalesReport(sales);
        return restaurantDto.id;
    }
}
