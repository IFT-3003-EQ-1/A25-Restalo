package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.RechercheRessource;
import ca.ulaval.glo2003.api.RestaurantRessource;
import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.api.response.exceptions.AccessInterditExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.NotFoundExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.ParametreInvalideExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.ParametreManquantExceptionMapper;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.entities.assemblers.ProprietaireFactory;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.assemblers.RestaurantFactory;
import ca.ulaval.glo2003.entities.filtres.FiltreRestaurantFactory;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import org.glassfish.jersey.server.ResourceConfig;

public class AppContext extends ResourceConfig {

    public static ResourceConfig getRessources() {
        RestaurantDtoAssembler restaurantDtoAssembler = new RestaurantDtoAssembler();

        final RestaurantService restaurantService = new RestaurantService(
                new RestaurantFactory(),
                new InMemoryRestaurantRepository(),
                new ProprietaireFactory(),
                new RestaurantAssembler(),
                new FiltreRestaurantFactory()
        );
        final RestaurantRessource restaurantRessource = new RestaurantRessource(
                restaurantService,
                restaurantDtoAssembler
        );

        final RechercheRessource rechercheRessource = new RechercheRessource(
                restaurantService,
                restaurantDtoAssembler
        );
        return new ResourceConfig()
                .register(rechercheRessource)
                .register(restaurantRessource)
                .register(AccessInterditExceptionMapper.class)
                .register(ParametreInvalideExceptionMapper.class)
                .register(ParametreManquantExceptionMapper.class)
                .register(NotFoundExceptionMapper.class);
    }



}
