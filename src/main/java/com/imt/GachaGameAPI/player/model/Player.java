package com.imt.GachaGameAPI.player.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Document(collection = "player")
@NoArgsConstructor
public class Player {
    
    @MongoId
    private int id;
    private int level;
    private int experience;
    private List<String> inventory; // Stocke les IDs des monstres
    
    public Player(int id, int level, int experience, List<String> inventory) {
        this.id = id;
        this.level = level;
        this.experience = experience;
        this.inventory = inventory;
    }

    public Player(int id) {
        this.id = id;
        this.level = 0;
        this.experience = 0;
        this.inventory = new ArrayList<String>();
    }

    public int getXpThreshold() {
        return (int) (50 * Math.pow(1.1, level - 1));
    }

    public boolean addExperience(int xp) {
        this.experience += xp;
        if (this.experience >= getXpThreshold()) {
            levelUp();
            return true;
        }
        return false;
    }

    private void levelUp() {
        this.experience = 0;
        this.level++;
    }

    public boolean addMonster(String monsterId) {
        if (inventory.size() < 10 + level) { // Taille max = 10 + level
            inventory.add(monsterId);
            return true;
        }
        return false;
    }

    public boolean removeMonster(String monsterId) {
        return inventory.remove(monsterId);
    }
}
