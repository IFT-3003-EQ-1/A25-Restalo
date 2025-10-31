package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryReservationRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoDBConnection;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoReservationRepository;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoRestaurantRepository;


public class DatabaseFactory {
    private final String persistenceType;

    public DatabaseFactory(String persistenceType) {
        this.persistenceType = persistenceType != null ? persistenceType : "inmemory";
    }

    public DatabaseFactory() {
        this(System.getProperty("persistence", "inmemory"));
    }

    public RestaurantRepository createRestaurantRepository() {
        return switch (persistenceType.toLowerCase()) {
            case "mongo" -> new MongoRestaurantRepository(MongoDBConnection.getDatastore());
            default -> new InMemoryRestaurantRepository();
        };
    }

    public ReservationRepository createReservationRepository() {
        return switch (persistenceType.toLowerCase()) {
            case "mongo" -> new MongoReservationRepository(MongoDBConnection.getDatastore());
            default -> new InMemoryReservationRepository();
        };
    }
}