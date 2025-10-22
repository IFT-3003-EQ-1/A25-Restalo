package ca.ulaval.glo2003.domain.dtos.restaurant;

public class HourDto {
    public String open;
    public String close;

    public HourDto() {
    }

    public HourDto(String open, String close) {
        this.open = open;
        this.close = close;
    }
}
