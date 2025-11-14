package ca.ulaval.glo2003.persistence.infra.mongoDB;

import ca.ulaval.glo2003.entities.filters.Filter;
import ca.ulaval.glo2003.entities.restaurant.ConfigReservation;
import ca.ulaval.glo2003.entities.restaurant.Hours;
import ca.ulaval.glo2003.entities.restaurant.Owner;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoRestaurantRepository;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Disabled
public class MongoRestaurantRepositoryTest {

    private final String RESTAURANT_ID = "1";

    @Mock
    public Datastore datastore;

    private MongoRestaurantRepository repository;

    private Restaurant restaurant;


    @BeforeEach
    public void setUp() {
        // Mockito.mockitoSession().strictness(Strictness.LENIENT).startMocking();
        repository = new MongoRestaurantRepository(datastore);
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

    @AfterEach
    public void tearDown() {
        Mockito.mockitoSession();
    }

    @Test
    public void givenGet_whenIdIsValid_thenReturnReservation() {
        Query<Restaurant> mockQuery = mock(Query.class);
        when(mockQuery.filter(any())).thenReturn(mockQuery);
        when(mockQuery.first()).thenReturn(restaurant);
        when(datastore.find(Restaurant.class)).thenReturn(mockQuery);

        Optional<Restaurant> value = repository.get(RESTAURANT_ID);

        assertTrue(value.isPresent());
        assertEquals(value.get(), restaurant);
    }

    @Test
    public void givenGet_whenIdIsNotValid_thenReturnEmptyOptional() {
        repository.save(restaurant);

        Optional<Restaurant> restaurantObtenu = repository.get(null);

        assertFalse(restaurantObtenu.isPresent());
    }

    @Test
    public void givenSave_whenParametersAreValid_thenDatastoreContainsReservation() {
        repository.save(restaurant);

        Optional<Restaurant> value = repository.get(RESTAURANT_ID);

        assertTrue(value.isPresent());
        assertEquals(value.get(), restaurant);
    }

    @Test
    public void givenListByOwner_whenParametersAreValid_thenListOfRestaurantsIsReturned() {
        String ownerId = restaurant.getOwner().getId();
        repository.save(restaurant);
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
        repository.save(other);

        assertEquals(2, repository.listByOwner(ownerId).size());
    }

    @Test
    public void givenGetAll_thenReturnAllRestaurants() {
        repository.save(restaurant);
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
        repository.save(other);

        assertEquals(2, repository.getAll().size());
    }

    @Test
    public void givenSearchRestaurant_whenNoFiltres_thenReturnAllRestaurants() {
        repository.save(restaurant);
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
        repository.save(other);

        assertEquals(2, repository.searchRestaurants(new ArrayList<>()).size());
    }

    @Test
    public void givenSearchRestaurant_whenMatchingFiltre_thenReturnMatchingRestaurants() {
        repository.save(restaurant);
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
        repository.save(other);
        Restaurant restaurantWithDifferentName = new Restaurant(
                "3",
                new Owner("1"),
                "Dejeuner",
                2,
                new Hours( "6:00:00", "13:00:00"),
                new ConfigReservation(60)
        );
        repository.save(restaurantWithDifferentName);
        List<Filter<Restaurant>> filtres = new ArrayList<>();
        filtres.add(r -> r.getName().equals("Pizz"));

        assertEquals(2, repository.searchRestaurants(filtres).size());
    }

    @Test
    public void givenDelete_whenRestaurantIdIsValid_thenRestaurantIsDeleted() {
        repository.save(restaurant);
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
        repository.save(other);

        assertTrue(repository.delete(RESTAURANT_ID));
        assertFalse(repository.get(RESTAURANT_ID).isPresent());
        assertTrue(repository.get(other.getId()).isPresent());
    }

    @Test
    public void givenDelete_whenIdIsNotValid_thenReturnFalse() {
        repository.save(restaurant);
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
        repository.save(other);

        assertFalse(repository.delete(RESTAURANT_ID));
        assertTrue(repository.get(RESTAURANT_ID).isPresent());
        assertTrue(repository.get(other.getId()).isPresent());
    }

}
