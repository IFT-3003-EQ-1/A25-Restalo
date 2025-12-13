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
import io.sentry.Sentry;

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
        Sentry.setExtra("operation", "createRestaurant");
        Sentry.setExtra("ownerId", ownerDto.id);
        Sentry.setExtra("restaurantName", restaurantDto.name);
        Sentry.setExtra("capacity", String.valueOf(restaurantDto.capacity));
        Sentry.setExtra("openTime", restaurantDto.hours != null ? restaurantDto.hours.open : null);
        Sentry.setExtra("closeTime", restaurantDto.hours != null ? restaurantDto.hours.close : null);

        Owner owner = ownerFactory.createOwner(ownerDto.id);
        Restaurant restaurant = restaurantFactory.createRestaurant(owner, restaurantDto);
        restaurantRepository.save(restaurant);

        return restaurant.getId();
    }

    public List<RestaurantDto> getRestaurants(String ownerId) {
        Sentry.setExtra("operation", "getRestaurants");
        Sentry.setExtra("ownerId", ownerId);

        if (ownerId == null || ownerId.isBlank()) {
            throw new MissingParameterException("Owner");
        }

        List<Restaurant> restaurants = restaurantRepository.listByOwner(ownerId);
        Sentry.setExtra("restaurantsCount", String.valueOf(restaurants.size()));

        return restaurants.stream().map(restaurantAssembler::toDto).toList();
    }

    public List<RestaurantDto> searchRestaurants(RestaurantDto searchValues) {
        Sentry.setExtra("operation", "searchRestaurants");
        Sentry.setExtra("searchName", searchValues.name);
        Sentry.setExtra("searchOpenTime", searchValues.hours != null ? searchValues.hours.open : null);
        Sentry.setExtra("searchCloseTime", searchValues.hours != null ? searchValues.hours.close : null);

        List<Filter<Restaurant>> filtres = filterRestaurantFactory.createFilters(
                searchValues.name,
                searchValues.hours != null ? searchValues.hours.open : null,
                searchValues.hours != null ? searchValues.hours.close : null
        );

        Sentry.setExtra("filtersCount", String.valueOf(filtres.size()));
        Sentry.setExtra("hasFilters", String.valueOf(!filtres.isEmpty()));

        List<Restaurant> results;

        if (filtres.isEmpty()) {
            results = restaurantRepository.getAll();
        } else {
            results = restaurantRepository.searchRestaurants(filtres);
        }

        Sentry.setExtra("resultsCount", String.valueOf(results.size()));

        return results.stream().map(restaurantAssembler::toDto).toList();
    }

    public boolean deleteRestaurant(String restaurantId) {
        Sentry.setExtra("operation", "deleteRestaurant");
        Sentry.setExtra("restaurantId", restaurantId);

        reservationRepository.deleteRelatedReservations(restaurantId);
        Sentry.addBreadcrumb("Réservations supprimées pour le restaurant " + restaurantId);

        boolean deleted = restaurantRepository.delete(restaurantId);
        Sentry.setExtra("deleted", String.valueOf(deleted));

        return deleted;
    }

    public String createSalesReport(SalesDto salesDto, RestaurantDto restaurantDto) {
        Sentry.setExtra("operation", "createSalesReport");
        Sentry.setExtra("restaurantId", restaurantDto.id);
        Sentry.setExtra("salesDate", salesDto.date);
        Sentry.setExtra("salesAmount", String.valueOf(salesDto.salesAmount));

        Sales sales = restaurantFactory.createSalesReport(salesDto, restaurantFactory.fromDto(restaurantDto));
        salesRepository.saveSalesReport(sales);

        return restaurantDto.id;
    }
}