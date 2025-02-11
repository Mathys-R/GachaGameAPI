package com.imt.GachaGameAPI.summon.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "summons")
public class Summon {
    @Id
    private String id;
    private String monsterId;
    private String userId; // ID de l'utilisateur ayant invoqué
    private String timestamp;

    // Constructeurs
    public Summon() {}

    public Summon(String monsterId, String userId, String timestamp) {
        this.monsterId = monsterId;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(String monsterId) {
        this.monsterId = monsterId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
