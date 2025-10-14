package ca.ulaval.glo2003.restaurants.domain;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.Duration;


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

    public int getReservationDuration() {
        LocalTime openTime = LocalTime.parse(hours.getOpen());
        LocalTime closeTime = LocalTime.parse(hours.getClose());
        // Calculer la durée totale d'ouverture en minutes
         return (int) Duration.between(openTime, closeTime).toMinutes();
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", hours=" + hours +
                '}';
    }
}
