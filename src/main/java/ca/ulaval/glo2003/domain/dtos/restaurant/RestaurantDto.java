package ca.ulaval.glo2003.domain.dtos.restaurant;


import java.util.Objects;

public class RestaurantDto {
    public String id;
    public OwnerDto owner;
    public String nom;
    public String hoursOpen;
    public String hoursClose;
    public int capacity;
    public ConfigReservationDto configReservation;

    public RestaurantDto() {

    }

    public RestaurantDto(String id,
                         OwnerDto owner,
                         String nom,
                         String hoursOpen,
                         String hoursClose,
                         int capacity,
                         ConfigReservationDto configReservation) {
        this.id = id;
        this.owner = owner;
        this.nom = nom;
        this.hoursOpen = hoursOpen;
        this.hoursClose = hoursClose;
        this.capacity = capacity;
        this.configReservation = configReservation;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDto that = (RestaurantDto) o;
        return capacity == that.capacity && Objects.equals(id, that.id) && Objects.equals(owner, that.owner) && Objects.equals(nom, that.nom) && Objects.equals(hoursOpen, that.hoursOpen) && Objects.equals(hoursClose, that.hoursClose) && Objects.equals(configReservation, that.configReservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, nom, hoursOpen, hoursClose, capacity, configReservation);
    }
}
