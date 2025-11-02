package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.ReservationResource;
import ca.ulaval.glo2003.api.SearchResource;
import ca.ulaval.glo2003.api.RestaurantResource;
import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.api.response.exceptions.ForbiddenAccessExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.NotFoundExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.InvalideParameterExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.MissingParameterExceptionMapper;
import ca.ulaval.glo2003.domain.ReservationService;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.entities.CustomerFactory;
import ca.ulaval.glo2003.entities.assemblers.ReservationAssembler;
import ca.ulaval.glo2003.entities.reservation.ReservationFactory;
import ca.ulaval.glo2003.entities.reservation.ReservationTimeFactory;
import ca.ulaval.glo2003.infra.persistence.*;
import ca.ulaval.glo2003.entities.restaurant.OwnerFactory;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.entities.filters.FilterRestaurantFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class AppContext extends ResourceConfig {

    public static ResourceConfig getRessources() {
        var databaseFactory = new DatabaseFactory(System.getProperty("persistence", "inmemory"));
       final RestaurantRepository restaurantRepository = databaseFactory.createRestaurantRepository();
       final ReservationRepository reservationRepository = databaseFactory.createReservationRepository();

       final  RestaurantDtoAssembler restaurantDtoAssembler = new RestaurantDtoAssembler();

       final RestaurantService restaurantService = new RestaurantService(
                new RestaurantFactory(),
                restaurantRepository,
                new OwnerFactory(),
                new RestaurantAssembler(),
                new FilterRestaurantFactory()
        );

       final ReservationService  reservationService= new ReservationService(
                restaurantRepository,
                new ReservationFactory(
                        new CustomerFactory(),
                        new ReservationTimeFactory()
                ),
               reservationRepository,
               new ReservationAssembler()
        );

        final RestaurantResource restaurantRessource = new RestaurantResource(
                restaurantService,
                restaurantDtoAssembler,
                reservationService
        );

        final SearchResource searchRessource = new SearchResource(
                restaurantService,
                restaurantDtoAssembler
        );

        final ReservationResource reservationResource = new ReservationResource(reservationService);

        return new ResourceConfig()
                .register(searchRessource)
                .register(restaurantRessource)
                .register(reservationResource)
                .register(ForbiddenAccessExceptionMapper.class)
                .register(InvalideParameterExceptionMapper.class)
                .register(MissingParameterExceptionMapper.class)
                .register(NotFoundExceptionMapper.class);
       }
}
