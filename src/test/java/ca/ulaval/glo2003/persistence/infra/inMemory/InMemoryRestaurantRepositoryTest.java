package ca.ulaval.glo2003.persistence.infra.inMemory;

import ca.ulaval.glo2003.entities.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.persistence.infra.RestaurantRepositoryTest;

import java.util.*;

public class InMemoryRestaurantRepositoryTest extends RestaurantRepositoryTest {

    private InMemoryRestaurantRepository repository;
    @Override
    protected RestaurantRepository getRepository() {
        Map<String, Restaurant> database = new HashMap<>();
        return new InMemoryRestaurantRepository(database);
    }
}
