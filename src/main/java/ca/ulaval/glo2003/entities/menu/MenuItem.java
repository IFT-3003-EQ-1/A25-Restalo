package ca.ulaval.glo2003.entities.menu;

public class MenuItem {
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
}
