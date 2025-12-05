package ca.ulaval.glo2003.domain.dtos.restaurant;


import java.util.Objects;

public class RestaurantDto {
    public String id;
    public OwnerDto owner;
    public String name;
    public HourDto hours;
    public int capacity;
    public ConfigReservationDto reservation;

    public RestaurantDto() {
        reservation = new ConfigReservationDto();
        hours = new HourDto();
    }

    public RestaurantDto(String id,
                         OwnerDto owner,
                         String name,
                         HourDto hours,
                         int capacity,
                         ConfigReservationDto reservation) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.hours = hours;
        this.capacity = capacity;
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDto that = (RestaurantDto) o;
        return capacity == that.capacity && Objects.equals(id, that.id) && Objects.equals(owner, that.owner) && Objects.equals(name, that.name) && Objects.equals(hours, that.hours) && Objects.equals(reservation, that.reservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, name,hours, capacity, reservation);
    }
}
