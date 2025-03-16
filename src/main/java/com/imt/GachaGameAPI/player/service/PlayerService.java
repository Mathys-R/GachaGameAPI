package com.imt.GachaGameAPI.player.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.imt.GachaGameAPI.player.dao.PlayerDao;
import com.imt.GachaGameAPI.player.dto.Mob;
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

    public List<Player> getAllPlayers() {
        return playerDao.findAll();
    }
    
    public List<Player> findPlayerById(String id) {
        return playerDao.findOneById(id);
    }

    public boolean addExperience(String playerId, int xp) {
        List<Player> players = findPlayerById(playerId);
    
        if (!players.isEmpty()) { // Vérifie si la liste contient au moins un élément
            Player player = players.get(0); // Prend le premier joueur trouvé
            boolean leveledUp = player.addExperience(xp);
            playerDao.save(player);
            return leveledUp;
        }
    
        return false; // Retourne false si aucun joueur n'est trouvé
    }

    public boolean addMonster(String playerId, String monsterId) {
        List<Player> players = findPlayerById(playerId);
    
        if (!players.isEmpty()) { // Vérifie si la liste contient au moins un joueur
            Player player = players.get(0); // Prend le premier joueur trouvé
            if (player.addMonster(monsterId)) {
                playerDao.save(player);
                return true;
            }
        }
        return false; // Retourne false si aucun joueur n'est trouvé ou si l'ajout échoue
    }
    
    public boolean removeMonster(String playerId, int uniqueId) {
        List<Player> players = findPlayerById(playerId);
    
        if (!players.isEmpty()) { // Vérifie si la liste contient au moins un joueur
            Player player = players.get(0); // Prend le premier joueur trouvé
            if (player.removeMonster(uniqueId)) {
                playerDao.save(player);
                return true;
            }
        }
        return false; // Retourne false si aucun joueur n'est trouvé ou si la suppression échoue
    }
    
    public List<Mob> getMonstersID(String playerId) {
        List<Player> players = findPlayerById(playerId);
    
        if (!players.isEmpty()) { // Vérifie si la liste contient au moins un joueur
            Player player = players.get(0); // Prend le premier joueur trouvé
            List<Mob> inventory = player.getInventory();
           
            return inventory;
        }

        return null; // Retourne null si aucun joueur n'est trouvé
    }

    public boolean removeAllPlayers() {
        List<Player> players = getAllPlayers();
    
        if (!players.isEmpty()) { // Vérifie s'il y a des joueurs à supprimer
            for (Player player : players) {
                playerDao.delete(player); // Supprime chaque joueur
            }
            return true;
        }
        return false; // Retourne false si aucun joueur n'est trouvé
    }
    
}
