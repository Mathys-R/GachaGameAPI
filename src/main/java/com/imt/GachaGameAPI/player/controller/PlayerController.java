package com.imt.GachaGameAPI.player.controller;

import com.imt.GachaGameAPI.player.dto.PlayerJsonDto;
import com.imt.GachaGameAPI.player.model.Player;
import com.imt.GachaGameAPI.player.service.PlayerService;

// import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping("/player")
// @SecurityRequirement(name = "bearerAuth") 
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true") 
// @CrossOrigin(origins = "*", allowCredentials = "true")
public class PlayerController {
    
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
        public ResponseEntity<?> handleOptions() {
            return ResponseEntity.ok().build();
    }
    
    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> createPlayer(@Valid @RequestBody PlayerJsonDto player) {
        playerService.savePlayer(
            new Player(
                player.getId(), 
                player.getLevel(), 
                player.getExperience(), 
                player.getInventory())
        );
        return ResponseEntity.ok(Map.of("message", "Player saved!"));
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
    public ResponseEntity<Map<String, String>> addExperience(@PathVariable int id, @PathVariable int xp) {
        boolean leveledUp = playerService.addExperience(id, xp);
        return ResponseEntity.ok(Map.of("message", leveledUp ? "Level up!" : "XP added"));
    }

    @PostMapping("/{id}/add-monster/{monsterId}")
    public ResponseEntity<Map<String, String>> addMonster(@PathVariable int id, @PathVariable String monsterId) {
        boolean success = playerService.addMonster(id, monsterId);
        return success 
            ? ResponseEntity.ok(Map.of("message", "Monster added!")) 
            : ResponseEntity.badRequest().body(Map.of("error", "Inventory full"));
    }

    @DeleteMapping("/{id}/remove-monster/{monsterId}")
    public ResponseEntity<Map<String, String>> removeMonster(@PathVariable int id, @PathVariable String monsterId) {
        boolean success = playerService.removeMonster(id, monsterId);
        return success 
            ? ResponseEntity.ok(Map.of("message", "Monster removed!")) 
            : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/allPlayers")
    public ResponseEntity<Map<String, String>> removePlayers() {
        boolean success = playerService.removeAllPlayers();
        return success 
            ? ResponseEntity.ok(Map.of("message", "All Players removed!")) 
            : ResponseEntity.notFound().build();
    }
}
