package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("sales")
public class Sales {

    @Id
    private String id;
    private String date;
    private float salesAmount;
    private Restaurant restaurant;

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public float getSalesAmount() {
        return salesAmount;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Sales(String id, String date, float salesAmount, Restaurant restaurant) {
        this.id = id;
        this.date = date;
        this.salesAmount = salesAmount;
        this.restaurant = restaurant;
    }

    public Sales() {
    }
}
