package ca.ulaval.glo2003.infra.persistence;

public class DatabaseFactory {
    private final String persistenceType;

    public DatabaseFactory(String persistenceType) {
        System.out.println("persistenceType: " + persistenceType);
        this.persistenceType = persistenceType != null ? persistenceType : "inmemory";
    }

    public DatabaseFactory() {
        this(System.getProperty("persistence", "inmemory"));
    }

    public RestaurantRepository createRestaurantRepository() {
        return switch (persistenceType.toLowerCase()) {
            case "mongo" -> new MongoRestaurantRepository();
            default -> new InMemoryRestaurantRepository();
        };
    }

    public ReservationRepository createReservationRepository() {
        return switch (persistenceType.toLowerCase()) {
            case "mongo" -> new MongoReservationRepository();
            default -> new InMemoryReservationRepository();
        };
    }
}