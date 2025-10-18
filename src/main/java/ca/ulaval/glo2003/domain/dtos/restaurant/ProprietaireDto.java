package ca.ulaval.glo2003.domain.dtos.restaurant;


import java.util.Objects;

public class ProprietaireDto {
    public String id;

    public ProprietaireDto(String id) {
        this.id = id;
    }

    public ProprietaireDto() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProprietaireDto that = (ProprietaireDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProprietaireDto{" +
                "id='" + id + '\'' +
                '}';
    }
}
