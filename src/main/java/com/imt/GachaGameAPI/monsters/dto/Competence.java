package com.imt.GachaGameAPI.monsters.dto;

public class Competence {

    private String nom;
    private int degatsDeBase;
    private double ratioDegats;
    private int cooldown;
    private int niveauAmelioration;
    private int niveauAmeliorationMax;

    public Competence() {}

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDegatsDeBase() {
        return degatsDeBase;
    }

    public void setDegatsDeBase(int degatsDeBase) {
        this.degatsDeBase = degatsDeBase;
    }

    public double getRatioDegats() {
        return ratioDegats;
    }

    public void setRatioDegats(double ratioDegats) {
        this.ratioDegats = ratioDegats;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getNiveauAmelioration() {
        return niveauAmelioration;
    }

    public void setNiveauAmelioration(int niveauAmelioration) {
        this.niveauAmelioration = niveauAmelioration;
    }

    public int getNiveauAmeliorationMax() {
        return niveauAmeliorationMax;
    }

    public void setNiveauAmeliorationMax(int niveauAmeliorationMax) {
        this.niveauAmeliorationMax = niveauAmeliorationMax;
    }
}

