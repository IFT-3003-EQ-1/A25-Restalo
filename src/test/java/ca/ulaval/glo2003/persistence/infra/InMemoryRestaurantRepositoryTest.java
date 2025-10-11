package ca.ulaval.glo2003.persistence.infra;

import ca.ulaval.glo2003.entities.Proprietaire;
import ca.ulaval.glo2003.entities.Restaurant;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRestaurantRepositoryTest {

    private final String RESTAURANT_ID = "1";

    private InMemoryRestaurantRepository repository;

    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryRestaurantRepository();

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

        Optional<Restaurant> restaurantObtenu = repository.get(RESTAURANT_ID);

        assertTrue(restaurantObtenu.isPresent());
        assertEquals(restaurantObtenu.get().getId(), RESTAURANT_ID);
    }

    @Test
    public void givenGet_whenIdIsNotValid_thenReturnEmptyOptional() {
        Optional<Restaurant> restaurantObtenu = repository.get(RESTAURANT_ID);

        assertFalse(restaurantObtenu.isPresent());
    }
}
