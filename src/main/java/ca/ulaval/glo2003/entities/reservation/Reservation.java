package ca.ulaval.glo2003.entities.reservation;

import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;

public class Reservation {
    private final String number;
    private final String  date;
    private final ReservationTime time;
    private final int groupSize;
    private final Customer customer;
    private final Restaurant restaurant;


    public Reservation(String number, String date, ReservationTime time, int groupSize, Customer customer, Restaurant restaurant) {
        this.number = number;
        this.date = date;
        this.time = time;
        this.groupSize = groupSize;
        this.customer = customer;
        this.restaurant = restaurant;
    }

    public String getDate() {
        return date;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getNumber() {
        return number;
    }

    public ReservationTime getTime() {
        return time;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "number='" + number + '\'' +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", groupSize=" + groupSize +
                ", customer=" + customer +
                ", restaurant=" + restaurant +
                '}';
    }
}
