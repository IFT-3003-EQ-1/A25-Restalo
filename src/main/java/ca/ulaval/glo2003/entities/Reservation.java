package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.entities.restaurant.Restaurant;

public class Reservation {
    private String number;
   private String  date;
   private ReservationTime time;
   private int groupSize;
   private Customer customer;
   private Restaurant restaurant;


    public Reservation(String number, String date, ReservationTime time, int groupSize, Customer customer, Restaurant restaurant) {
        this.number = number;
        this.date = date;
        this.time = time;
        this.groupSize = groupSize;
        this.customer = customer;
        this.restaurant = restaurant;
    }

    public Reservation() {
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
