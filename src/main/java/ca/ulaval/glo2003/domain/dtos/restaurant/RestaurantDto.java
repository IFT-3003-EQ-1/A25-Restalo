package ca.ulaval.glo2003.domain.dtos.restaurant;


import java.util.Objects;

public class RestaurantDto {
    public String id;
    public OwnerDto owner;
    public String name;
    public String hoursOpen;
    public String hoursClose;
    public int capacity;
    public ConfigReservationDto configReservation;

    public RestaurantDto() {

    }

    public RestaurantDto(String id,
                         OwnerDto owner,
                         String name,
                         String hoursOpen,
                         String hoursClose,
                         int capacity,
                         ConfigReservationDto configReservation) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.hoursOpen = hoursOpen;
        this.hoursClose = hoursClose;
        this.capacity = capacity;
        this.configReservation = configReservation;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDto that = (RestaurantDto) o;
        return capacity == that.capacity && Objects.equals(id, that.id) && Objects.equals(owner, that.owner) && Objects.equals(name, that.name) && Objects.equals(hoursOpen, that.hoursOpen) && Objects.equals(hoursClose, that.hoursClose) && Objects.equals(configReservation, that.configReservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, name, hoursOpen, hoursClose, capacity, configReservation);
    }
}
