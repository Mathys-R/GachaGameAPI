package com.imt.GachaGameAPI.player.service;

import java.util.List;
import java.util.Optional;

import com.imt.GachaGameAPI.player.dao.PlayerDao;
import com.imt.GachaGameAPI.player.model.Player;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    
    private final PlayerDao playerDao;

    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void savePlayer(Player player) {
        playerDao.save(player);
    }

    public List<Player> findAllPlayers() {
        return playerDao.findAll();
    }

    public List<Player> findPlayerById(int id) {
        return playerDao.findOneById(id);
    }

    // public boolean addExperience(int playerId, int xp) {
    //     Optional <Player> playerOpt = findPlayerById(playerId);
    //     if (playerOpt.isPresent()) {
    //         Player player = playerOpt.get();
    //         boolean leveledUp = player.addExperience(xp);
    //         playerDao.save(player);
    //         return leveledUp;
    //     }
    //     return false;
    // }

    // public boolean addMonster(int playerId, String monsterId) {
    //     Optional<Player> playerOpt = findPlayerById(playerId);
    //     if (playerOpt.isPresent()) {
    //         Player player = playerOpt.get();
    //         if (player.addMonster(monsterId)) {
    //             playerDao.save(player);
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // public boolean removeMonster(int playerId, String monsterId) {
    //     Optional<Player> playerOpt = findPlayerById(playerId);
    //     if (playerOpt.isPresent()) {
    //         Player player = playerOpt.get();
    //         if (player.removeMonster(monsterId)) {
    //             playerDao.save(player);
    //             return true;
    //         }
    //     }
    //     return false;
    // }
}
