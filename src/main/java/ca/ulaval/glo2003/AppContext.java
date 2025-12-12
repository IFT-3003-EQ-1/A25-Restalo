package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.HealthResource;
import ca.ulaval.glo2003.api.ReservationResource;
import ca.ulaval.glo2003.api.SearchResource;
import ca.ulaval.glo2003.api.RestaurantResource;
import ca.ulaval.glo2003.api.assemblers.ReservationDtoAssembler;
import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.api.requests.AutorizationRequestFilter;
import ca.ulaval.glo2003.api.response.exceptions.ForbiddenAccessExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.NotFoundExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.InvalideParameterExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.MissingParameterExceptionMapper;
import ca.ulaval.glo2003.domain.MenuService;
import ca.ulaval.glo2003.domain.ReservationService;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.domain.SecurityService;
import ca.ulaval.glo2003.entities.CustomerFactory;
import ca.ulaval.glo2003.entities.menu.MenuFactory;
import ca.ulaval.glo2003.entities.menu.MenuRepository;
import ca.ulaval.glo2003.entities.reservation.ReservationRepository;
import ca.ulaval.glo2003.entities.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.entities.assemblers.ReservationAssembler;
import ca.ulaval.glo2003.entities.filters.FilterReservationFactory;
import ca.ulaval.glo2003.entities.reservation.ReservationFactory;
import ca.ulaval.glo2003.entities.reservation.ReservationTimeFactory;
import ca.ulaval.glo2003.infra.persistence.*;
import ca.ulaval.glo2003.entities.restaurant.OwnerFactory;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.entities.filters.FilterRestaurantFactory;
import ca.ulaval.glo2003.infra.persistence.DBConfig;
import com.google.common.base.Strings;
import org.glassfish.jersey.server.ResourceConfig;

public class AppContext extends ResourceConfig {

    public ResourceConfig getRessources() {
        DatabaseFactory databaseFactory = new DatabaseFactory(getConfigFromEnv());
        final RestaurantRepository restaurantRepository = databaseFactory.getRestaurantRepository();
        final ReservationRepository reservationRepository = databaseFactory.getReservationRepository();
        final MenuRepository menuRepository = databaseFactory.getMenuRepository();

        final  RestaurantDtoAssembler restaurantDtoAssembler = new RestaurantDtoAssembler();

        final MenuService menuService = new MenuService(
                menuRepository,
                new MenuFactory(),
                new RestaurantFactory(),
                new OwnerFactory()
        );

        final RestaurantService restaurantService = new RestaurantService(
                new RestaurantFactory(),
                restaurantRepository,
                reservationRepository,
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
               new ReservationAssembler(),
                new FilterReservationFactory()
        );

         final RestaurantResource restaurantRessource = new RestaurantResource(
                restaurantService,
                restaurantDtoAssembler,
                reservationService,
                 menuService
        );

         final SearchResource searchRessource = new SearchResource(
                restaurantService,
                restaurantDtoAssembler
        );

         final ReservationResource reservationResource =
                 new ReservationResource(reservationService, new ReservationDtoAssembler());

         final HealthResource healthResource = new HealthResource();

         final AutorizationRequestFilter autorizationRequestFilter = new AutorizationRequestFilter(
                 new SecurityService(restaurantRepository, new RestaurantAssembler())
         );

         return new ResourceConfig()
                 .register(autorizationRequestFilter)
                 .register(healthResource)
                 .register(searchRessource)
                 .register(restaurantRessource)
                 .register(reservationResource)
                 .register(ForbiddenAccessExceptionMapper.class)
                 .register(InvalideParameterExceptionMapper.class)
                 .register(MissingParameterExceptionMapper.class)
                 .register(NotFoundExceptionMapper.class);
    }

    private DBConfig getConfigFromEnv() {
        DBConfig.PersistenceType persistenceType;
        String persistence = System.getProperty("persistence", "inmemory");
        if (persistence.equals("mongo")) {
            persistenceType = DBConfig.PersistenceType.MONGO_DB;
        } else  {
            persistenceType = DBConfig.DEFAULT_PERSISTENCE_TYPE;
        }

        String dbName = System.getenv().get("MONGO_DATABASE_NAME");
        if (Strings.isNullOrEmpty(dbName)) {
            dbName = DBConfig.DEFAULT_DATABASE_NAME;
        }

        String host = System.getenv().get("MONGO_HOST");
        if (Strings.isNullOrEmpty(host)) {
            host = DBConfig.DEFAULT_HOST;
        }

        String port_str = System.getenv().get("MONGO_PORT");
        if (Strings.isNullOrEmpty(port_str)) {
            port_str = Integer.toString(DBConfig.DEFAULT_PORT);
        }
        return new DBConfig(
                host,
                Integer.parseInt(port_str),
                dbName,
                persistenceType
        );
    }
}
