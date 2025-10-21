package ca.ulaval.glo2003.domain.dtos;

import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;

public class ReservationDto {
    public String number;
    public String date;
    public String startTime;
    public int groupSize;
    public CustomerDto customer;
    public RestaurantDto restaurant;

    public ReservationDto() {
    }

    public ReservationDto(String date, String startTime, int groupSize, CustomerDto customer) {
        this.date = date;
        this.startTime = startTime;
        this.groupSize = groupSize;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "CreateReservationDto{" +
                "date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", groupSize=" + groupSize +
                ", customer=" + customer +
                '}';
    }
}
