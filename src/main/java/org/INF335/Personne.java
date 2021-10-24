package org.INF335;

import java.time.LocalDate;

public class Personne {
    private String nom;
    private LocalDate dateDeNaissance;
    private double salaireHoraire        = 0.0;
    private double heureTravailSemaine   = 0.0;
    private int nbSemainesDeTravail      = 0;

    public Personne(String nom, LocalDate dob) {
        this.nom = nom;
        this.dateDeNaissance = dob;
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
}
