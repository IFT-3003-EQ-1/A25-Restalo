package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.entities.reservation.ReservationRepository;
import ca.ulaval.glo2003.entities.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryReservationRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoDBConnection;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoReservationRepository;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoRestaurantRepository;
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

    private Datastore getDatastore() {
        if (mongoDBConnection == null) {
            mongoDBConnection = new MongoDBConnection(dbConfig);
            mongoDBConnection.openConnection();
        }
        return mongoDBConnection.getDatastore();
    }
}