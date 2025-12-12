package ca.ulaval.glo2003.entities;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("sales")
public class Sales {

    @Id
    private String id;
    private String date;
    private float salesAmount;

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public float getSalesAmount() {
        return salesAmount;
    }

    public Sales(String id, String date, float salesAmount) {
        this.id = id;
        this.date = date;
        this.salesAmount = salesAmount;
    }

    public Sales() {
    }
}
