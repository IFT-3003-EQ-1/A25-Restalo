package ca.ulaval.glo2003.domain.dtos;


import java.util.Objects;

public class ProprietaireDto {
    public String id;
    public String nom;

    public ProprietaireDto(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public ProprietaireDto() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProprietaireDto that = (ProprietaireDto) o;
        return Objects.equals(id, that.id) && Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom);
    }

    @Override
    public String toString() {
        return "ProprietaireDto{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }
}
