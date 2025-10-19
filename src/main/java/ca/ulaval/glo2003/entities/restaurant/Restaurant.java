package ca.ulaval.glo2003.entities.restaurant;


import java.time.Duration;
import java.time.LocalTime;

public class Restaurant {
    private final String id;
    private final Owner owner;
    private final String name;
    private final int capacity;
    private final String hoursOpen;
    private final String hoursClose;
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

    public String getHoursOpen() {
        return hoursOpen;
    }

    public String getHoursClose() {
        return hoursClose;
    }

    public ConfigReservation getConfigReservation() {
        return configReservation;
    }


    public Restaurant(String id, Owner owner, String name, int capacity, String hoursOpen, String hoursClose, ConfigReservation configReservation) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.capacity = capacity;
        this.hoursOpen = hoursOpen;
        this.hoursClose = hoursClose;
        this.configReservation = configReservation;
    }

    public int getReservationDuration() {
        LocalTime openTime = LocalTime.parse(hoursOpen);
        LocalTime closeTime = LocalTime.parse(hoursClose);
        // Calculer la durée totale d'ouverture en minutes
        return (int) Duration.between(openTime, closeTime).toMinutes();
    }
}
