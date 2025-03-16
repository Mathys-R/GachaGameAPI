package com.imt.GachaGameAPI.player.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mob {
    private int uniqueId; // ID unique dans l'inventaire
    private String monsterId; // ID du monstre invoqué

    public Mob(int uniqueId, String monsterId) {
        this.uniqueId = uniqueId;
        this.monsterId = monsterId;
    }
}
