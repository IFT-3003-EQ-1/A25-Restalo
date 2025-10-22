package ca.ulaval.glo2003.entities.restaurant;


import java.time.Duration;
import java.time.LocalTime;

public class Restaurant {
    private final String id;
    private final Owner owner;
    private final String name;
    private final int capacity;
    private final Hours hours;
    private final ConfigReservation configReservation;

    public String getId() {
        return id;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Hours getHours() {
        return hours;
    }

    public ConfigReservation getConfigReservation() {
        return configReservation;
    }


    public Restaurant(String id, Owner owner, String name, int capacity, Hours hours, ConfigReservation configReservation) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.configReservation = configReservation;
    }

    public int getReservationDuration() {
        LocalTime openTime = LocalTime.parse(hours.getOpen());
        LocalTime closeTime = LocalTime.parse(hours.getClose());
        // Calculer la durée totale d'ouverture en minutes
        return (int) Duration.between(openTime, closeTime).toMinutes();
    }
}
