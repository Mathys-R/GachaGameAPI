package com.imt.GachaGameAPI.player.service;

import java.util.List;

import com.imt.GachaGameAPI.player.dao.PlayerDao;
import com.imt.GachaGameAPI.player.model.Player;

import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    
    private final PlayerDao playerDao;

    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void savePlayer(Player player){
        playerDao.save(player);
    }

    
    public List<Player> findAllPlayers(){
        return playerDao.findAll();
    }
}
