package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.entities.SalesRepository;
import ca.ulaval.glo2003.entities.menu.MenuRepository;
import ca.ulaval.glo2003.entities.reservation.ReservationRepository;
import ca.ulaval.glo2003.entities.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryMenuRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryReservationRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemorySalesRepository;
import ca.ulaval.glo2003.infra.persistence.mongoDB.*;
import dev.morphia.Datastore;


/**
 * This class is meant to be used as a singleton. If multiple instance of DatabaseFactory with different dbConfig exist,
 * the behavior is undefined.
 */
public class DatabaseFactory {

    private static MongoDBConnection mongoDBConnection;

    private final DBConfig dbConfig;

    public DatabaseFactory(DBConfig config) {
        if(config == null) {
            config = new DBConfig();
        }
        this.dbConfig = config;
    }

    public RestaurantRepository getRestaurantRepository() {
        return switch (dbConfig.getPersistenceType()) {
            case DBConfig.PersistenceType.MONGO_DB -> new MongoRestaurantRepository(getDatastore());
            default -> new InMemoryRestaurantRepository();
        };
    }

    public ReservationRepository getReservationRepository() {
        return switch (dbConfig.getPersistenceType()) {
            case DBConfig.PersistenceType.MONGO_DB -> new MongoReservationRepository(getDatastore());
            default -> new InMemoryReservationRepository();
        };
    }

    public MenuRepository getMenuRepository() {
        return switch (dbConfig.getPersistenceType()) {
            case DBConfig.PersistenceType.MONGO_DB -> new MongoMenuRepository(getDatastore());
            default -> new InMemoryMenuRepository();
        };
    }

    public SalesRepository getSalesRepository() {
        return switch (dbConfig.getPersistenceType()) {
            case DBConfig.PersistenceType.MONGO_DB -> new MongoSalesRepository(getDatastore());
            default -> new InMemorySalesRepository();
        };
    }

    private Datastore getDatastore() {
        if (mongoDBConnection == null) {
            mongoDBConnection = new MongoDBConnection(dbConfig);
            mongoDBConnection.openConnection();
        }
        return mongoDBConnection.getDatastore();
    }
}