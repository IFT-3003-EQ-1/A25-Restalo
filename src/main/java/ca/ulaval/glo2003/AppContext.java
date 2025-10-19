package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.ReservationResource;
import ca.ulaval.glo2003.api.RechercheRessource;
import ca.ulaval.glo2003.api.RestaurantRessource;
import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.api.response.exceptions.AccessInterditExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.NotFoundExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.ParametreInvalideExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.ParametreManquantExceptionMapper;
import ca.ulaval.glo2003.domain.ReservationService;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.entities.assemblers.*;
import ca.ulaval.glo2003.infra.persistence.InMemoryReservationRepository;
import ca.ulaval.glo2003.entities.assemblers.ProprietaireFactory;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.assemblers.RestaurantFactory;
import ca.ulaval.glo2003.entities.filtres.FiltreRestaurantFactory;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.ReservationRepository;
import ca.ulaval.glo2003.infra.persistence.RestaurantRepository;
import org.glassfish.jersey.server.ResourceConfig;

public class AppContext extends ResourceConfig {

    public static ResourceConfig getRessources() {
       final RestaurantRepository restaurantRepository = new InMemoryRestaurantRepository();
       final  RestaurantDtoAssembler restaurantDtoAssembler = new RestaurantDtoAssembler();

       final RestaurantService restaurantService = new RestaurantService(
                new RestaurantFactory(),
                restaurantRepository,
                new ProprietaireFactory(),
                new RestaurantAssembler(),
                new FiltreRestaurantFactory()
        );

       final ReservationService  reservationService= new ReservationService(
                restaurantRepository,
                new ReservationFactory(
                        new CustomerFactory(),
                        new ReservationTimeFactory()
                ),
                new InMemoryReservationRepository(),
               new ReservationAssembler()
        );

        final RestaurantRessource restaurantRessource = new RestaurantRessource(
                restaurantService,
                restaurantDtoAssembler,
                reservationService
        );

        final RechercheRessource rechercheRessource = new RechercheRessource(
                restaurantService,
                restaurantDtoAssembler
        );

        final ReservationResource reservationResource = new ReservationResource(reservationService);

        return new ResourceConfig()
                .register(rechercheRessource)
                .register(restaurantRessource)
                .register(reservationResource)
                .register(AccessInterditExceptionMapper.class)
                .register(ParametreInvalideExceptionMapper.class)
                .register(ParametreManquantExceptionMapper.class)
                .register(NotFoundExceptionMapper.class);
       }
}
