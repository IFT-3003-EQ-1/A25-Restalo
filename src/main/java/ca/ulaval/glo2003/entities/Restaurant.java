package ca.ulaval.glo2003.entities;

public class Restaurant {
    private final String id;
    private final Proprietaire proprietaire;
    private final String nom;
    private final int capacite;
    private final String horaireOuverture;
    private final String horaireFermeture;

    public String getId() {
        return id;
    }



    public Proprietaire getProprietaire() {
        return proprietaire;
    }

    public String getNom() {
        return nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public String getHoraireOuverture() {
        return horaireOuverture;
    }

    public String getHoraireFermeture() {
        return horaireFermeture;
    }

    public Restaurant(String id,
                      Proprietaire proprietaire,
                      String nom,
                      int capacite,
                      String horaireOuverture,
                      String horaireFermeture) {
        this.id = id;
        this.proprietaire = proprietaire;
        this.nom = nom;
        this.capacite = capacite;
        this.horaireOuverture = horaireOuverture;
        this.horaireFermeture = horaireFermeture;
    }
}
