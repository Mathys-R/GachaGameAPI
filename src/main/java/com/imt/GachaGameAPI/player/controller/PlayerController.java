package com.imt.GachaGameAPI.player.controller;

import com.imt.GachaGameAPI.monsters.model.Monsters;
import com.imt.GachaGameAPI.player.dto.PlayerJsonDto;
import com.imt.GachaGameAPI.player.model.Player;
import com.imt.GachaGameAPI.player.service.PlayerService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("/player")
public class PlayerController {
    
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    
    @PostMapping("/save")
    public ResponseEntity<String> createPlayer(@Valid @RequestBody PlayerJsonDto player) {
        playerService.savePlayer(
            new Player(
                player.getId(), 
                player.getLevel(), 
                player.getExperience(), 
                player.getInventory())
        );
        return ResponseEntity.ok("saved !");
    }

    @GetMapping("/allPlayers")
    public ResponseEntity<List<PlayerJsonDto>> getAllPlayers() {
        List<PlayerJsonDto> players = playerService.getAllPlayers()
            .stream()
            .map(player -> new PlayerJsonDto(
                player.getId(),
                player.getLevel(),
                player.getExperience(),  
                player.getInventory()
            ))
            .toList();

        return ResponseEntity.ok(players);
    }

    // @GetMapping("/allPlayers")
    // public ResponseEntity<List<Player>> getAllPlayers() {
    //     // return ResponseEntity.ok("test");
    //     List<Player> players = playerService.getAllPlayers();
    //     return ResponseEntity.ok(players);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<List<PlayerJsonDto>> getPlayers(@PathVariable int id) {
        List<PlayerJsonDto> playerById = playerService.findPlayerById(id)
            .stream()
            .map(player -> new PlayerJsonDto(
                player.getId(), 
                player.getLevel(), 
                player.getExperience(), 
                player.getInventory()))
            .toList();

        return ResponseEntity.ok(playerById);
    }

    @PostMapping("/{id}/add-xp/{xp}")
    public ResponseEntity<String> addExperience(@PathVariable int id, @PathVariable int xp) {
        boolean leveledUp = playerService.addExperience(id, xp);
        return ResponseEntity.ok(leveledUp ? "Level up!" : "XP added");
    }

    @PostMapping("/{id}/add-monster/{monsterId}")
    public ResponseEntity<String> addMonster(@PathVariable int id, @PathVariable String monsterId) {
        boolean success = playerService.addMonster(id, monsterId);
        return success ? ResponseEntity.ok("Monster added!") : ResponseEntity.badRequest().body("Inventory full");
    }

    @DeleteMapping("/{id}/remove-monster/{monsterId}")
    public ResponseEntity<String> removeMonster(@PathVariable int id, @PathVariable String monsterId) {
        boolean success = playerService.removeMonster(id, monsterId);
        return success ? ResponseEntity.ok("Monster removed!") : ResponseEntity.notFound().build();
    }
}
