package ca.ulaval.glo2003.restaurants.domain.dtos;

/**
 * Modèle de données pour la (dé)sérialisation JSON-B.
 * Champs publics + constructeur par défaut obligatoires pour éviter le 500.
 */
public class Restaurant {
    private String id;
    private String ownerId;
    private String name;
    private int capacity;
    private Hours hours;

    public Restaurant() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
