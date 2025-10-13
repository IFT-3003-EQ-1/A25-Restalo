package ca.ulaval.glo2003.persistence.infra;

import ca.ulaval.glo2003.entities.Proprietaire;
import ca.ulaval.glo2003.entities.Restaurant;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRestaurantRepositoryTest {

    private final String RESTAURANT_ID = "1";

    private  Map<String, Restaurant> database;

    private InMemoryRestaurantRepository repository;

    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        database = new HashMap<>();
        repository = new InMemoryRestaurantRepository(database);

        restaurant = new Restaurant(
                RESTAURANT_ID,
                new Proprietaire("1"),
                "Pizz",
                2,
                "11:00:00",
                "19:00:00"
        );
    }

    @Test
    public void givenGet_whenIdIsValid_thenReturnRestaurant() {
        repository.save(restaurant);

        assertFalse(database.isEmpty());
        assertEquals(database.get(restaurant.getId()).getId(), RESTAURANT_ID);
    }

    @Test
    public void givenGet_whenIdIsNotValid_thenReturnEmptyOptional() {
        Optional<Restaurant> restaurantObtenu = repository.get(RESTAURANT_ID);

        assertFalse(restaurantObtenu.isPresent());
    }

    @Test
    public void givenSave_whenParametersAreValid_thenRestaurantIsSaved() {
        repository.save(restaurant);

        assertFalse(database.isEmpty());
        assertEquals(database.get(restaurant.getId()).getId(), RESTAURANT_ID);

    }

    @Test
    public void givenListParProprietaire_whenParametersAreValid_thenListOfRestaurantsIsReturned() {
        repository.save(restaurant);
        repository.save(new Restaurant(
                "2",
                new Proprietaire("1"),
                "Pizz",
                2,
                "11:00:00",
                "19:00:00"
        ));

        assertEquals(2, repository.listParProprietaire(restaurant.getId()).size());
    }
}
