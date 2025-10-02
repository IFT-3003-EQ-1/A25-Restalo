package ca.ulaval.glo2003.domain.dtos;


public class RestaurantDto {
    public String id;
    public ProprietaireDto proprietaire;
    public String nom;
    public String horaireOuverture;
    public String horaireFermeture;
    public int capacite;

    public RestaurantDto() {

    }

    public RestaurantDto(String id,
                         ProprietaireDto proprietaire,
                         String nom,
                         String horaireOuverture,
                         String horaireFermeture,
                         int capacite) {
        this.id = id;
        this.proprietaire = proprietaire;
        this.nom = nom;
        this.horaireOuverture = horaireOuverture;
        this.horaireFermeture = horaireFermeture;
        this.capacite = capacite;
    }
}
