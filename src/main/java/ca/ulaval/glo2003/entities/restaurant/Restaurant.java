package ca.ulaval.glo2003.entities.restaurant;


import java.time.Duration;
import java.time.LocalTime;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.Objects;

@Entity("restaurants")
public class Restaurant {

    @Id
    private String id;

    private Owner owner;
    private String name;
    private int capacity;
    private Hours hours;

    private ConfigReservation configReservation;

    // Default constructor required by Morphia
    public Restaurant() {
    }

    public Restaurant(String id, Owner owner, String name, int capacity,
                      Hours hours, ConfigReservation configReservation) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.configReservation = configReservation;
    }

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

    public int getReservationDuration() {
        LocalTime openTime = LocalTime.parse(hours.getOpen());
        LocalTime closeTime = LocalTime.parse(hours.getClose());
        return (int) Duration.between(openTime, closeTime).toMinutes();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return capacity == that.capacity && Objects.equals(id, that.id) && Objects.equals(owner, that.owner) && Objects.equals(name, that.name) && Objects.equals(hours, that.hours) && Objects.equals(configReservation, that.configReservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, name, capacity, hours, configReservation);
    }
}
