package ca.ulaval.glo2003.domain.dtos.restaurant;


import java.util.Objects;

public class OwnerDto {
    public String id;

    public OwnerDto(String id) {
        this.id = id;
    }

    public OwnerDto() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDto that = (OwnerDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OwnerDto{" +
                "id='" + id + '\'' +
                '}';
    }
}
