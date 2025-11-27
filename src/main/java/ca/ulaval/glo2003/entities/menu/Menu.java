package ca.ulaval.glo2003.entities.menu;

import ca.ulaval.glo2003.entities.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private final String id;

    private final Restaurant restaurant;

    private final String title;

    private final String startDate;

    private final List<MenuItem> menuItems;

    public final String getId() {
        return id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public Menu(String id, Restaurant restaurant, String title, String startDate, List<MenuItem> menuItems) {
        this.id = id;
        this.restaurant = restaurant;
        this.title = title;
        this.startDate = startDate;
        this.menuItems = menuItems;
    }

    public Menu() {
        this.id = null;
        this.restaurant = null;
        this.title = null;
        this.startDate = null;
        this.menuItems = new ArrayList<>();
    }
}
