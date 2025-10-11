package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.entities.assemblers.ProprietaireFactory;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.assemblers.RestaurantFactory;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestaurantServiceIntegratedTest {

    private RestaurantService restaurantService;


    @BeforeEach
    public void setUp() {
        restaurantService = new RestaurantService(
                new RestaurantFactory(),
                new InMemoryRestaurantRepository(),
                new ProprietaireFactory(),
                new RestaurantAssembler()
        );
    }

    @Test
    public void givenCreate_whenParameterAreValide_thenRestaurantIsCreated() {

    }
}
