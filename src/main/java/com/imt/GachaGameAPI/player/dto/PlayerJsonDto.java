package com.imt.GachaGameAPI.player.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PlayerJsonDto {

    @NotNull private int id;
    @NotNull @Min(0) private int level;
    @NotNull @Min(0) private int experience;
    private List<String> inventory; // Liste des IDs des monstres

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public List<String> getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return "PlayerJsonDto{" +
            "id='" + id + '\'' +
            ", level=" + level +
            ", experience='" + experience + '\'' +  
            ", inventory='" + inventory + '\'' +
            '}';
    }

    public PlayerJsonDto(int id, int level, int experience, List<String> inventory) {
        this.id = id;
        this.level = level;
        this.experience = experience;
        this.inventory = inventory;
    }
}
