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
        OwnerDto ownerDto = (OwnerDto) o;
        return Objects.equals(id, ownerDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OwnerDto{" +
                "id='" + id + '\'' +
                '}';
    }
}
