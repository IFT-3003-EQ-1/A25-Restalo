package ca.ulaval.glo2003.persistence.infra;


import ca.ulaval.glo2003.entities.RestaurantRepository;
import ca.ulaval.glo2003.entities.filters.Filter;
import ca.ulaval.glo2003.entities.restaurant.ConfigReservation;
import ca.ulaval.glo2003.entities.restaurant.Hours;
import ca.ulaval.glo2003.entities.restaurant.Owner;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class RestaurantRepositoryTest {
    private final String RESTAURANT_ID = "1";
 private RestaurantRepository restaurantRepository;
 private Restaurant restaurant;

 protected abstract RestaurantRepository getRepository();

    @BeforeEach
    public void setUp() {
        restaurantRepository = getRepository();
        restaurant = new Restaurant(
                RESTAURANT_ID,
                new Owner(
                        "1"
                ),
                "Pizz",
                2,
                new Hours(),
                new ConfigReservation()
        );
    }

    @Test
    public void givenGet_whenIdIsValid_thenReturnReservation() {
        restaurantRepository.save(restaurant);
        Optional<Restaurant> value = restaurantRepository.get(RESTAURANT_ID);
        assertTrue(value.isPresent());
        assertEquals(value.get().getId(), RESTAURANT_ID);
    }

    @Test
    public void givenGet_whenIdIsNotValid_thenReturnEmptyOptional() {
        restaurantRepository.save(restaurant);

        Optional<Restaurant> restaurantObtenu = restaurantRepository.get(null);

        assertFalse(restaurantObtenu.isPresent());
    }

    @Test
    public void givenSave_whenParametersAreValid_thenDatastoreContainsReservation() {
        restaurantRepository.save(restaurant);

        Optional<Restaurant> value = restaurantRepository.get(RESTAURANT_ID);

        assertTrue(value.isPresent());
        assertEquals(value.get().getId(), RESTAURANT_ID);
    }

    @Test
    public void givenListByOwner_whenParametersAreValid_thenListOfRestaurantsIsReturned() {
        String ownerId = restaurant.getOwner().getId();
        restaurantRepository.save(restaurant);
        Restaurant other = new Restaurant(
                "2",
                new Owner(
                        "1"
                ),
                "Pizz",
                2,
                new Hours(),
                new ConfigReservation()
        );
        restaurantRepository.save(other);

        assertEquals(2, restaurantRepository.listByOwner(ownerId).size());
    }

    @Test
    public void givenGetAll_thenReturnAllRestaurants() {
        restaurantRepository.save(restaurant);
        Restaurant other = new Restaurant(
                "2",
                new Owner(
                        "1"
                ),
                "Pizz",
                2,
                new Hours(),
                new ConfigReservation()
        );
        restaurantRepository.save(other);

        assertEquals(2, restaurantRepository.getAll().size());
    }

    @Test
    public void givenSearchRestaurant_whenNoFiltres_thenReturnAllRestaurants() {
        restaurantRepository.save(restaurant);
        Restaurant other = new Restaurant(
                "2",
                new Owner(
                        "1"
                ),
                "Pizz",
                2,
                new Hours(),
                new ConfigReservation()
        );
        restaurantRepository.save(other);

        assertEquals(2, restaurantRepository.searchRestaurants(new ArrayList<>()).size());
    }

    @Test
    public void givenSearchRestaurant_whenMatchingFiltre_thenReturnMatchingRestaurants() {
        restaurantRepository.save(restaurant);
        Restaurant other = new Restaurant(
                "2",
                new Owner(
                        "1"
                ),
                "Pizz",
                2,
                new Hours(),
                new ConfigReservation()
        );
        restaurantRepository.save(other);
        Restaurant restaurantWithDifferentName = new Restaurant(
                "3",
                new Owner("1"),
                "Dejeuner",
                2,
                new Hours( "6:00:00", "13:00:00"),
                new ConfigReservation(60)
        );
        restaurantRepository.save(restaurantWithDifferentName);
        List<Filter<Restaurant>> filtres = new ArrayList<>();
        filtres.add(r -> r.getName().equals("Pizz"));

        assertEquals(2, restaurantRepository.searchRestaurants(filtres).size());
    }

    @Test
    public void givenDelete_whenRestaurantIdIsValid_thenRestaurantIsDeleted() {
        restaurantRepository.save(restaurant);
        Restaurant other = new Restaurant(
                "2",
                new Owner(
                        "1"
                ),
                "Pizz",
                2,
                new Hours(),
                new ConfigReservation()
        );
        restaurantRepository.save(other);

        assertTrue(restaurantRepository.delete(RESTAURANT_ID));
        assertFalse(restaurantRepository.get(RESTAURANT_ID).isPresent());
        assertTrue(restaurantRepository.get(other.getId()).isPresent());
    }

    @Test
    public void givenDelete_whenIdIsNotValid_thenReturnFalse() {
        restaurantRepository.save(restaurant);

        restaurantRepository.delete(RESTAURANT_ID);
        assertFalse(restaurantRepository.get(RESTAURANT_ID).isPresent());
    }

    //    @Test
//    public void givenDelete_whenRestaurantIdIsValid_thenRestaurantIsDeleted() {
//
//    }
//
//    @Test
//    public void givenDelete_whenIdIsNotValid_thenReturnFalse() {
//
//    }
}
