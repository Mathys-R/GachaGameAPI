package com.imt.GachaGameAPI.player.dto;


import java.util.List;

import com.imt.GachaGameAPI.monsters.model.Monsters;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@NoArgsConstructor
public class PlayerDto {
    
    @NotNull private int id;
    @NotNull @Min(0) private int level;
    private List<Monsters> inventory;

    
    public int getId() {
        return id;
    }

    
    public int getLevel() {
        return level;
    }
    
    
    public List<Monsters> getInventory() {
        return inventory;
    }

    public PlayerDto(int id, int level, List<Monsters> inventory){
        this.id = id;
        this.level = level;
        this.inventory = inventory;
    }
    
}
