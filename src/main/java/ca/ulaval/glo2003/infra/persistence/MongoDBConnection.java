package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class MongoDBConnection {
    private static MongoDBConnection instance;
    private final Datastore datastore;
    private static final String  CONNECTION_STRING = "mongodb://root:example@localhost:27017";
    
    private MongoDBConnection() {
        try {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                    .build();
            
            MongoClient mongoClient = MongoClients.create(settings);

            datastore = Morphia.createDatastore(mongoClient, "restaloDB");
            datastore.getMapper().getEntityModel(Restaurant.class);
            datastore.getMapper().getEntityModel(Reservation.class);

            System.out.println("MongoDB connected successfully!");
        } catch (Exception e) {
            System.err.println("Failed to connect to MongoDB: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("MongoDB connection failed", e);
        }
    }
    
    public static MongoDBConnection getInstance() {
        if (instance == null) {
            synchronized (MongoDBConnection.class) {
                if (instance == null) {
                    instance = new MongoDBConnection();
                }
            }
        }
        return instance;
    }
    
    public Datastore getDatastore() {
        return datastore;
    }
}