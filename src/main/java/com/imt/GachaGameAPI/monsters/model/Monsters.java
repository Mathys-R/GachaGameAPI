package com.imt.GachaGameAPI.monsters.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.imt.GachaGameAPI.monsters.dto.Competence;

@Document(collection = "monsters")
public class Monsters {

    @Id
    private String id;
    private String typeElementaire;
    private int hp;
    private int atk;
    private int def;
    private int vit;
    private Competence[] competences;
    private double lootRate;

    public Monsters() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeElementaire() {
        return typeElementaire;
    }

    public void setTypeElementaire(String typeElementaire) {
        this.typeElementaire = typeElementaire;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getVit() {
        return vit;
    }

    public void setVit(int vit) {
        this.vit = vit;
    }

    public Competence[] getCompetences() {
        return competences;
    }

    public void setCompetences(Competence[] competences) {
        this.competences = competences;
    }

    public double getLootRate() {
        return lootRate;
    }

    public void setLootRate(double lootRate) {
        this.lootRate = lootRate;
    }
}
