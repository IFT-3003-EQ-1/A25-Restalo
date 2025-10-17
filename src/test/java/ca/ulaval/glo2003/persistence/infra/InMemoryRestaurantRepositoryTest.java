package ca.ulaval.glo2003.persistence.infra;

import ca.ulaval.glo2003.entities.Proprietaire;
import ca.ulaval.glo2003.entities.Restaurant;
import ca.ulaval.glo2003.entities.filtres.Filtre;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRestaurantRepositoryTest {

    private final String RESTAURANT_ID = "1";

    private  Map<String, Restaurant> database;

    private InMemoryRestaurantRepository repository;


    @BeforeEach
    public void setUp() {
        database = new HashMap<>();
        repository = new InMemoryRestaurantRepository(database);

    }

    @Test
    public void givenGet_whenIdIsValid_thenReturnRestaurant() {
        repository.save(InMemoryRestaurantRepositoryTestUtils.createRestaurant(RESTAURANT_ID));

        assertFalse(database.isEmpty());
        assertEquals(database.get(RESTAURANT_ID).getId(), RESTAURANT_ID);
    }

    @Test
    public void givenGet_whenIdIsNotValid_thenReturnEmptyOptional() {
        Optional<Restaurant> restaurantObtenu = repository.get(RESTAURANT_ID);

        assertFalse(restaurantObtenu.isPresent());
    }

    @Test
    public void givenSave_whenParametersAreValid_thenRestaurantIsSaved() {
        repository.save(InMemoryRestaurantRepositoryTestUtils.createRestaurant(RESTAURANT_ID));

        assertFalse(database.isEmpty());
        assertEquals(database.get(RESTAURANT_ID).getId(), RESTAURANT_ID);

    }

    @Test
    public void givenListParProprietaire_whenParametersAreValid_thenListOfRestaurantsIsReturned() {
        String proprietaireId = InMemoryRestaurantRepositoryTestUtils.createRestaurant(RESTAURANT_ID).getProprietaire().getId();
        repository.save(InMemoryRestaurantRepositoryTestUtils.createRestaurant(RESTAURANT_ID));
        repository.save(InMemoryRestaurantRepositoryTestUtils.createRestaurant("2"));

        assertEquals(2, repository.listParProprietaire(proprietaireId).size());
    }

    @Test
    public void givenGetAll_thenReturnAllRestaurants() {
        repository.save(InMemoryRestaurantRepositoryTestUtils.createRestaurant(RESTAURANT_ID));
        repository.save(InMemoryRestaurantRepositoryTestUtils.createRestaurant("2"));

        assertEquals(2, repository.getAll().size());
    }

    @Test
    public void givenSearchRestaurant_whenNoFiltres_thenReturnAllRestaurants() {
        repository.save(InMemoryRestaurantRepositoryTestUtils.createRestaurant(RESTAURANT_ID));
        repository.save(InMemoryRestaurantRepositoryTestUtils.createRestaurant("2"));

        assertEquals(2, repository.searchRestaurants(new ArrayList<>()).size());
    }

    @Test
    public void givenSearchRestaurant_whenMatchingFiltre_thenReturnMatchingRestaurants() {
        repository.save(InMemoryRestaurantRepositoryTestUtils.createRestaurant(RESTAURANT_ID));
        repository.save(InMemoryRestaurantRepositoryTestUtils.createRestaurant("2"));

        Restaurant restaurant = new Restaurant(
                "3",
                new Proprietaire("1"),
                "Dejeuner",
                2,
                "6:00:00",
                "13:00:00"
        );
        repository.save(restaurant);
        List<Filtre<Restaurant>> filtres = new ArrayList<>();
        filtres.add(r -> r.getNom().equals("Pizz"));

        assertEquals(2, repository.searchRestaurants(filtres).size());
    }
}
