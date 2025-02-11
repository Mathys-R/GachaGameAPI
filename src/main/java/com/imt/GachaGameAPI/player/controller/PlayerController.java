package com.imt.GachaGameAPI.player.controller;

import com.imt.GachaGameAPI.player.dto.PlayerDto;
import com.imt.GachaGameAPI.player.model.Player;
import com.imt.GachaGameAPI.player.service.PlayerService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
public class PlayerController {
    
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    

    @PostMapping("/save")
    public ResponseEntity<String> createPlayer(@Valid @RequestBody Player player) {
        playerService.savePlayer(player);
        return ResponseEntity.ok("Player saved!");
    }

    @GetMapping("/")
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        List<PlayerDto> players = playerService.findAllPlayers()
            .stream()
            .map(player -> new PlayerDto(
                player.getId(),
                player.getLevel(),
                player.getExperience(),  
                player.getInventory()
            ))
            .toList();

        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable int id) {
        Optional<Player> player = playerService.findPlayerById(id);
        return player.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
