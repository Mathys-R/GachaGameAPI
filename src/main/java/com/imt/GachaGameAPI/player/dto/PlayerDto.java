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
public class PlayerDto {

    @NotNull private int id;
    @NotNull @Min(0) private int level;
    @NotNull @Min(0) private int experience;
    private List<String> inventory; // Liste des IDs des monstres

    public PlayerDto(int id, int level, int experience, List<String> inventory) {
        this.id = id;
        this.level = level;
        this.experience = experience;
        this.inventory = inventory;
    }

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
        return "PlayerDto{" +
            "id='" + id + '\'' +
            ", level=" + level +
            ", experience='" + experience + '\'' +
            ", inventory='" + inventory + '\'' +
            '}';
    }

}


// package com.imt.GachaGameAPI.player.dto;


// import java.util.List;

// import com.imt.GachaGameAPI.monsters.model.Monsters;

// import jakarta.validation.constraints.Min;
// import jakarta.validation.constraints.NotNull;
// import lombok.NoArgsConstructor;
// import lombok.Setter;


// @Setter
// @NoArgsConstructor
// public class PlayerDto {
    
//     @NotNull private int id;
//     @NotNull @Min(0) private int level;
//     private List<Monsters> inventory;

    
//     public int getId() {
//         return id;
//     }

    
//     public int getLevel() {
//         return level;
//     }

//     public void setLevel(int level) {
//         this.level = level;
//     }

    
//     public void levelUp() {
//         if (this.level < 50) {
//             this.level = this.level+=1;
//         }
//     }
    
//     public List<Monsters> getInventory() {
//         return inventory;
//     }

//     public PlayerDto(int id, int level, List<Monsters> inventory){
//         this.id = id;
//         this.level = level;
//         this.inventory = inventory;
//     }

    
//     // public List<Monsters> getInventory() {
//     //     return inventory;
//     // }

//     public void setInventory(List<Monsters> inventory) {
//         this.inventory = inventory;
//     }

//     public void addMonster(Monsters monster){
//         this.inventory.add(monster);
//     }

    
// }
