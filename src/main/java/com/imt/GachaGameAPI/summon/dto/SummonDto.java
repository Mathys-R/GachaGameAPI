package com.imt.GachaGameAPI.summon.dto;

public class SummonDto {
    private String monsterId;
    private String timestamp;

    // Constructeurs
    public SummonDto() {}

    public SummonDto(String monsterId, String timestamp) {
        this.monsterId = monsterId;
        this.timestamp = timestamp;
    }

    // Getters et Setters
    public String getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(String monsterId) {
        this.monsterId = monsterId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {    
        this.timestamp = timestamp;
    }
}
