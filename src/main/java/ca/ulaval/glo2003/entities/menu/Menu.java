package ca.ulaval.glo2003.entities.menu;

import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity("menus")
public class Menu {

    @Id
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

    public List<MenuItem> getMenuItems() {
        return menuItems;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
