package ca.ulaval.glo2003.entities.restaurant;

import dev.morphia.annotations.Entity;

@Entity
public class ConfigReservation {
    private  int duration;

    public ConfigReservation() {
    }

    public int getDuration() {
        return duration;
    }

    public ConfigReservation(int duration) {
        this.duration = duration;
    }
}
