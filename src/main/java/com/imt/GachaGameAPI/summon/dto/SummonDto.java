package com.imt.GachaGameAPI.summon.dto;

public class SummonDto {
    private String monsterId;
    private String monsterName;
    private String timestamp;

    // Constructeurs
    public SummonDto() {}

    public SummonDto(String monsterId, String monsterName, String timestamp) {
        this.monsterId = monsterId;
        this.monsterName = monsterName;
        this.timestamp = timestamp;
    }

    // Getters et Setters
    public String getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(String monsterId) {
        this.monsterId = monsterId;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {    
        this.timestamp = timestamp;
    }
}
