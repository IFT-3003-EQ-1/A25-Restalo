package ca.ulaval.glo2003.entities.restaurant;

import dev.morphia.annotations.Entity;

@Entity
public class Hours {
    private  String close;
    private  String open;
    public Hours() {
    }

    public String getClose() {
        return close;
    }

    public String getOpen() {
        return open;
    }

    public Hours(String open, String close) {
        this.close = close;
        this.open = open;
    }
}
