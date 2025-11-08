package ca.ulaval.glo2003.persistence.infra;

import ca.ulaval.glo2003.entities.ReservationRepository;
import ca.ulaval.glo2003.entities.RestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.*;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryReservationRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoReservationRepository;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoRestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseFactoryTest {

    @Test
    void getRestaurantRepository_WithMongoType_ReturnsMongoRepository() {
        DBConfig config = new DBConfig(
                DBConfig.DEFAULT_HOST,
                DBConfig.DEFAULT_PORT,
                DBConfig.DEFAULT_DATABASE_NAME,
                DBConfig.PersistenceType.MONGO_DB
        );

        DatabaseFactory factory = new DatabaseFactory(config);
        RestaurantRepository repository = factory.getRestaurantRepository();
        assertNotNull(repository);
        assertInstanceOf(MongoRestaurantRepository.class, repository);
    }

    @Test
    void getRestaurantRepository_WithInMemoryType_ReturnsInMemoryRepository() {
        DBConfig config = new DBConfig(
                DBConfig.DEFAULT_HOST,
                DBConfig.DEFAULT_PORT,
                DBConfig.DEFAULT_DATABASE_NAME,
                DBConfig.PersistenceType.IN_MEMORY
        );

        DatabaseFactory factory = new DatabaseFactory(config);
        RestaurantRepository repository = factory.getRestaurantRepository();
        assertNotNull(repository);
        assertInstanceOf(InMemoryRestaurantRepository.class, repository);
    }

    @Test
    void getRestaurantRepository_WithNullType_ReturnsInMemoryRepositoryAsFallback() {
        DatabaseFactory factory = new DatabaseFactory(null);
        RestaurantRepository repository = factory.getRestaurantRepository();
        assertNotNull(repository);
        assertInstanceOf(InMemoryRestaurantRepository.class, repository);
    }

    @Test
    void getReservationRepository_WithMongoType_ReturnsMongoRepository() {
        DBConfig config = new DBConfig(
                DBConfig.DEFAULT_HOST,
                DBConfig.DEFAULT_PORT,
                DBConfig.DEFAULT_DATABASE_NAME,
                DBConfig.PersistenceType.MONGO_DB
        );
        DatabaseFactory factory = new DatabaseFactory(config);
        ReservationRepository repository = factory.getReservationRepository();
        assertNotNull(repository);
        assertInstanceOf(MongoReservationRepository.class, repository);
    }

    @Test
    void getReservationRepository_WithInMemoryType_ReturnsInMemoryRepository() {
        DBConfig config = new DBConfig(
                DBConfig.DEFAULT_HOST,
                DBConfig.DEFAULT_PORT,
                DBConfig.DEFAULT_DATABASE_NAME,
                DBConfig.PersistenceType.IN_MEMORY
        );

        DatabaseFactory factory = new DatabaseFactory(config);
        ReservationRepository repository = factory.getReservationRepository();
        assertNotNull(repository);
        assertInstanceOf(InMemoryReservationRepository.class, repository);
    }

    @Test
    void factoryCreatesNewInstancesEachTime() {
        DBConfig config = new DBConfig(
                DBConfig.DEFAULT_HOST,
                DBConfig.DEFAULT_PORT,
                DBConfig.DEFAULT_DATABASE_NAME,
                DBConfig.PersistenceType.IN_MEMORY
        );

        DatabaseFactory factory = new DatabaseFactory(config);
        RestaurantRepository repo1 = factory.getRestaurantRepository();
        RestaurantRepository repo2 = factory.getRestaurantRepository();
        assertNotSame(repo1, repo2, "Factory should create new instances each time");
    }
}