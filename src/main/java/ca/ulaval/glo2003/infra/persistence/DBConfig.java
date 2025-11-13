package ca.ulaval.glo2003.infra.persistence;

public class DBConfig {

    public enum PersistenceType {
        MONGO_DB,
        IN_MEMORY
    }

    public static final String DEFAULT_HOST = "localhost";

    public static final int DEFAULT_PORT = 27017;

    public static final String DEFAULT_DATABASE_NAME = "restaloDB";

    public static final PersistenceType DEFAULT_PERSISTENCE_TYPE = PersistenceType.IN_MEMORY;

    private final String host;

    private final int port;

    private final String databaseName;

    private final PersistenceType persistenceType;

    public DBConfig(String host, int port, String databaseName, PersistenceType persistenceType) {
        this.host = host;
        this.port = port;
        this.databaseName = databaseName;
        this.persistenceType = persistenceType;
    }

    public DBConfig() {
        this(DEFAULT_HOST, DEFAULT_PORT, DEFAULT_DATABASE_NAME, PersistenceType.IN_MEMORY);
    }

    public String getConnectionString() {
        return "mongodb://" + host + ":" + port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public PersistenceType getPersistenceType() {
        return persistenceType;
    }
}
