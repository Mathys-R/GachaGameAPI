package com.imt.GachaGameAPI.player.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.imt.GachaGameAPI.player.dto.Mob;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Document(collection = "player")
@NoArgsConstructor
public class Player {
    
    @MongoId
    private String id;
    private int level;
    private int experience;
    private int nextMonsterId = 1; // Compteur d'ID unique pour les monstres
    private List<Mob> inventory; // Stocke les IDs des monstres
    
    public Player(String id, int level, int experience, List<Mob> inventory) {
        this.id = id;
        this.level = level;
        this.experience = experience;
        this.inventory = (inventory != null) ? inventory : new ArrayList<Mob>();
    }

    public Player(String id) {
        this.id = id;
        this.level = 0;
        this.experience = 0;
        this.inventory = new ArrayList<Mob>();
    }

    public int getXpThreshold() {
        return (int) (50 * Math.pow(1.1, level - 1));
    }

    public boolean addExperience(int xp) {
        this.experience += xp;
        boolean leveledUp = false;
    
        while (this.experience >= getXpThreshold() && this.level < 50) {
            this.experience -= getXpThreshold(); // Retire l'XP nécessaire pour le level-up
            this.level++;
            leveledUp = true;
        }
    
        return leveledUp;
    }

    public boolean addMonster(String monsterId) {
        if (inventory.size() < 10 + level) { // Taille max = 10 + level
            inventory.add(new Mob(nextMonsterId, monsterId));
            nextMonsterId++;
            return true;
        }
        return false;
    }

    public boolean removeMonster(int uniqueId) {
        return inventory.removeIf(monster -> monster.getUniqueId() == uniqueId);
    }
}
