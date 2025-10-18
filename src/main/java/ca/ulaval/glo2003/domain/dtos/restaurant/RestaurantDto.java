package ca.ulaval.glo2003.domain.dtos.restaurant;


import java.util.Objects;

public class RestaurantDto {
    public String id;
    public ProprietaireDto proprietaire;
    public String nom;
    public String horaireOuverture;
    public String horaireFermeture;
    public int capacite;
    public ConfigReservationDto configReservation;

    public RestaurantDto() {

    }

    public RestaurantDto(String id,
                         ProprietaireDto proprietaire,
                         String nom,
                         String horaireOuverture,
                         String horaireFermeture,
                         int capacite,
                         ConfigReservationDto ConfigReservation) {
        this.id = id;
        this.proprietaire = proprietaire;
        this.nom = nom;
        this.horaireOuverture = horaireOuverture;
        this.horaireFermeture = horaireFermeture;
        this.capacite = capacite;
        this.configReservation = ConfigReservation;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDto that = (RestaurantDto) o;
        return capacite == that.capacite && Objects.equals(id, that.id) && Objects.equals(proprietaire, that.proprietaire) && Objects.equals(nom, that.nom) && Objects.equals(horaireOuverture, that.horaireOuverture) && Objects.equals(horaireFermeture, that.horaireFermeture) && Objects.equals(configReservation, that.configReservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, proprietaire, nom, horaireOuverture, horaireFermeture, capacite, configReservation);
    }
}
