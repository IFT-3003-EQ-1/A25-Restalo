package ca.ulaval.glo2003.infra.persistence.mongoDB;

import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.reservation.Reservation;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.infra.persistence.DBConfig;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

import java.util.concurrent.TimeUnit;


public class MongoDBConnection {
    private MongoClient mongoClient;
    private Datastore datastore;
    private final DBConfig config;


    public MongoDBConnection(DBConfig config) {
        this.config = config;
    }
    
    public  Datastore getDatastore() {
        return datastore;
    }

    public void openConnection() {
        if (mongoClient != null)
            throw new IllegalStateException("MongoDB connection already open");
        try {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyToClusterSettings(
                            builder -> builder.serverSelectionTimeout(10, TimeUnit.SECONDS)
                    )
                    .applyConnectionString(new ConnectionString(config.getConnectionString()))
                    .build();

            mongoClient = MongoClients.create(settings);

            datastore = Morphia.createDatastore(mongoClient, config.getDatabaseName());

            datastore.getMapper().getEntityModel(Restaurant.class);
            datastore.getMapper().getEntityModel(Reservation.class);
            datastore.getMapper().getEntityModel(Menu.class);

            System.out.println("MongoDB connected successfully!");
        } catch (Exception e) {
            throw new RuntimeException("MongoDB connection failed", e);
        }
    }

    public void closeConnection() {
        if (mongoClient == null)
            throw new IllegalStateException("MongoDB connection has to be open");
        mongoClient.close();
    }

}