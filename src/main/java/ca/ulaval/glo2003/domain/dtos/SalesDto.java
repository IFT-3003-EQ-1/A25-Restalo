package ca.ulaval.glo2003.domain.dtos;


import ca.ulaval.glo2003.entities.Sales;

import java.util.Objects;

public class SalesDto {
    public String id;
    public String date;
    public float salesAmount;

    public SalesDto(String id, String date, float salesAmount) {
        this.id = id;
        this.date = date;
        this.salesAmount = salesAmount;
    }

    public SalesDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SalesDto salesDto = (SalesDto) o;
        return Float.compare(salesAmount, salesDto.salesAmount) == 0 && Objects.equals(id, salesDto.id) && Objects.equals(date, salesDto.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, salesAmount);
    }

    public static SalesDto fromEntity(Sales sale) {
        return new SalesDto(sale.getId(), sale.getDate(), sale.getSalesAmount());
    }
}
