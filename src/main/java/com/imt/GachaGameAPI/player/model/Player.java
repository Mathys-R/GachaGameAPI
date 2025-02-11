package com.imt.GachaGameAPI.player.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.imt.GachaGameAPI.monsters.model.Monsters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@Document(collection = "players")
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    
    @MongoId
    private int id;
    private int level;
    private List<Monsters> inventory;

    // public Player(int id, int level, List<Monsters> inventory){
    //     this.id = id;
    //     this.level = level;
    //     this.inventory = inventory;
    // }
    
    // public int getId() {
    //     return id;
    // }

    // public void setId(int id) {
    //     this.id = id;
    // }

    // public int getLevel() {
    //     return level;
    // }

    // public void setLevel(int level) {
    //     this.level = level;
    // }
    
    // public void levelUp() {
    //     if (this.level < 50) {
    //         this.level = this.level+=1;
    //     }
    // }
    
    // public List<Monsters> getInventory() {
    //     return inventory;
    // }

    // public void setInventory(List<Monsters> inventory) {
    //     this.inventory = inventory;
    // }

    // public void addMonster(Monsters monster){
    //     this.inventory.add(monster);
    // }

}
