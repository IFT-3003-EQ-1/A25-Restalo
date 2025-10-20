package ca.ulaval.glo2003.entities.restaurant;


import ca.ulaval.glo2003.domain.dtos.restaurant.HourDto;

import java.time.Duration;
import java.time.LocalTime;

public class Restaurant {
    private final String id;
    private final Owner owner;
    private final String name;
    private final int capacity;
    private final HourDto hours;
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

    public HourDto getHours() {
        return hours;
    }

    public ConfigReservation getConfigReservation() {
        return configReservation;
    }


    public Restaurant(String id, Owner owner, String name, int capacity, HourDto hours, ConfigReservation configReservation) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.configReservation = configReservation;
    }

    public int getReservationDuration() {
        LocalTime openTime = LocalTime.parse(hours.open);
        LocalTime closeTime = LocalTime.parse(hours.close);
        // Calculer la durée totale d'ouverture en minutes
        return (int) Duration.between(openTime, closeTime).toMinutes();
    }
}
