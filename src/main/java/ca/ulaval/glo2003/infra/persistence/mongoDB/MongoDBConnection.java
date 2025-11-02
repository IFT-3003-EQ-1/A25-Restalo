package ca.ulaval.glo2003.infra.persistence.mongoDB;

import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

import java.util.concurrent.TimeUnit;


public class MongoDBConnection {
    private static MongoDBConnection instance;
    private final Datastore datastore;
    private static final String  CONNECTION_STRING = "mongodb://root:example@localhost:27017";
    private static final String  DATABASE_NAME = "restaloDB";

    private MongoDBConnection() {
        try {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyToClusterSettings(
                            builder -> builder.serverSelectionTimeout(10, TimeUnit.SECONDS)
                    )
                    .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                    .build();
            
            MongoClient mongoClient = MongoClients.create(settings);

            datastore = Morphia.createDatastore(mongoClient, DATABASE_NAME);
            datastore.getMapper().getEntityModel(Restaurant.class);
            datastore.getMapper().getEntityModel(Reservation.class);

            System.out.println("MongoDB connected successfully!");
        } catch (Exception e) {
            System.err.println("Failed to connect to MongoDB: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("MongoDB connection failed", e);
        }
    }
    
    public static Datastore getDatastore() {
        if (instance == null) {
            synchronized (MongoDBConnection.class) {
                instance = new MongoDBConnection();
            }
        }
        return instance.datastore;
    }
}