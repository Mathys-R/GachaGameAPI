package com.imt.GachaGameAPI.player.dto;

import com.imt.GachaGameAPI.monsters.model.Monsters;

public class MobDetails {
    private int uniqueId;
    private Monsters monsterDetails;
    
    public MobDetails(int uniqueId, Monsters monsterDetails) {
        this.uniqueId = uniqueId;
        this.monsterDetails = monsterDetails;
    }
    
    public int getUniqueId() {
        return uniqueId;
    }
    
    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }
    
    public Monsters getMonsterDetails() {
        return monsterDetails;
    }
    
    public void setMonsterDetails(Monsters monsterDetails) {
        this.monsterDetails = monsterDetails;
    }
}
