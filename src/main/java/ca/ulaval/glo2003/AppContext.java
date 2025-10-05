package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.RestaurantRessource;
import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.entities.assemblers.ProprietaireFactory;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.assemblers.RestaurantFactory;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import org.glassfish.jersey.server.ResourceConfig;

public class AppContext extends ResourceConfig {

    public RestaurantRessource getRessource() {
        return new RestaurantRessource(
                new RestaurantService(
                        new RestaurantFactory(),
                        new InMemoryRestaurantRepository(),
                        new ProprietaireFactory(),
                        new RestaurantAssembler()
                ),
                new RestaurantDtoAssembler()
        );
    }


}
