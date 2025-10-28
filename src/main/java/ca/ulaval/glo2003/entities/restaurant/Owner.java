package ca.ulaval.glo2003.entities.restaurant;

import dev.morphia.annotations.Entity;

@Entity
public class Owner {
    public Owner() {
    }

    private String id;

    public String getId() {
        return id;
    }

    public Owner(String id) {
        this.id = id;
    }
}
