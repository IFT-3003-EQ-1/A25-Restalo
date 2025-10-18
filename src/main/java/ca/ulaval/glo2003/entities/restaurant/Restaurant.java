package ca.ulaval.glo2003.entities.restaurant;

import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Optional;

public class Restaurant {
    private final String id;
    private final Proprietaire proprietaire;
    private final String nom;
    private final int capacite;
    private final String horaireOuverture;
    private final String horaireFermeture;
    private ConfigReservation configReservation;

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

    public ConfigReservation getConfigReservation() {
        return configReservation;
    }


    public Restaurant(String id, Proprietaire proprietaire, String nom, int capacite, String horaireOuverture, String horaireFermeture, ConfigReservation configReservation) {
        this.id = id;
        this.proprietaire = proprietaire;
        this.nom = nom;
        this.capacite = capacite;
        this.horaireOuverture = horaireOuverture;
        this.horaireFermeture = horaireFermeture;
        this.configReservation = configReservation;
    }

    public int getReservationDuration() {
        LocalTime openTime = LocalTime.parse(horaireOuverture);
        LocalTime closeTime = LocalTime.parse(horaireFermeture);
        // Calculer la durée totale d'ouverture en minutes
        return (int) Duration.between(openTime, closeTime).toMinutes();
    }
}
