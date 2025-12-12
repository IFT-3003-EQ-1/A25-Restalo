package ca.ulaval.glo2003.entities.menu;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.Objects;

@Entity("menuItems")
public class MenuItem {

    @Id
    private final String id;
    private final String name;
    private final float price;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public MenuItem(String id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public MenuItem() {
        id = null;
        name = null;
        price = -1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Float.compare(price, menuItem.price) == 0 && Objects.equals(id, menuItem.id) && Objects.equals(name, menuItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
