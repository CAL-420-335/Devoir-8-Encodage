package org.INF335;

import java.time.LocalDate;
import java.util.Objects;

public class Personne {

    private String nom                   = "";
    private LocalDate dateDeNaissance    = LocalDate.now();
    private double salaireHoraire        = 0.0;
    private double heureTravailSemaine   = 0.0;
    private int nbSemainesDeTravail      = 0;

    public Personne() {
    }

    public Personne(String nom, LocalDate dob) {
        this.nom = nom;
        this.dateDeNaissance = dob;
    }

    public Personne(String nom, LocalDate dob, double salaireHoraire, double heureTravailSemaine, int nbSemainesDeTravail) {
        this.nom = nom;
        this.dateDeNaissance = dob;
        this.salaireHoraire = salaireHoraire;
        this.heureTravailSemaine = heureTravailSemaine;
        this.nbSemainesDeTravail = nbSemainesDeTravail;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public double getSalaireHoraire() {
        return salaireHoraire;
    }

    public void setSalaireHoraire(double salaireHoraire) {
        this.salaireHoraire = salaireHoraire;
    }

    public double getHeureTravailSemaine() {
        return heureTravailSemaine;
    }

    public void setHeureTravailSemaine(double heureTravailSemaine) {
        this.heureTravailSemaine = heureTravailSemaine;
    }

    public int getNbSemainesDeTravail() {
        return nbSemainesDeTravail;
    }

    public void setNbSemainesDeTravail(int nbSemainesDeTravail) {
        this.nbSemainesDeTravail = nbSemainesDeTravail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personne personne = (Personne) o;
        return Double.compare(personne.salaireHoraire, salaireHoraire) == 0 &&
                Double.compare(personne.heureTravailSemaine, heureTravailSemaine) == 0 &&
                nbSemainesDeTravail == personne.nbSemainesDeTravail &&
                Objects.equals(nom, personne.nom) &&
                Objects.equals(dateDeNaissance, personne.dateDeNaissance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, dateDeNaissance, salaireHoraire, heureTravailSemaine, nbSemainesDeTravail);
    }
}
