package ca.ulaval.glo2003.entities.restaurant;

public class Hours {
    private final String close;
    private final String open;

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
