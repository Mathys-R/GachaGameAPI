package com.imt.GachaGameAPI.player.controller;

import com.imt.GachaGameAPI.player.dto.PlayerJsonDto;
import com.imt.GachaGameAPI.player.model.Player;
import com.imt.GachaGameAPI.player.service.PlayerService;
import com.imt.GachaGameAPI.monsters.model.Monsters;
import com.imt.GachaGameAPI.player.dto.Mob;
import com.imt.GachaGameAPI.player.dto.MobDetails;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping("/player")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true") 
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
    public ResponseEntity<List<PlayerJsonDto>> getPlayers(@PathVariable String id) {
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

    
    // @GetMapping("{id}/getMonsters")
    // public ResponseEntity<List<Mob>> getMonsters(@PathVariable String id) {
    //     List<Player> players = playerService.findPlayerById(id);
        
    //     if (!players.isEmpty()) {
    //         // Assuming a player is found, we just return their inventory (list of monster IDs)
    //         Player player = players.get(0);
    //         return ResponseEntity.ok(player.getInventory());
    //     }
    //     return ResponseEntity.notFound().build();
    @GetMapping("{playerId}/inventory/{token}")
    public ResponseEntity<List<MobDetails>> getInventory(@PathVariable String playerId, @PathVariable String token) {
        List<Mob> inventory = playerService.getMonstersID(playerId);
        
        if (inventory == null || inventory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RestTemplate restTemplate = new RestTemplate();
        List<MobDetails> detailedInventory = new ArrayList<>();
        
        for (Mob mob : inventory) {
            try {
                // Set up headers with Bearer token
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token);
                System.out.println("Bearer token: " + token);
                HttpEntity<String> entity = new HttpEntity<>(headers);
                
                // Get monster details from monster API with authorization
                ResponseEntity<Monsters> response = restTemplate.exchange(
                    "http://api-monsters:8083/monsters/" + mob.getMonsterId(), 
                    HttpMethod.GET,
                    entity,
                    Monsters.class
                );
                
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    detailedInventory.add(new MobDetails(mob.getUniqueId(), response.getBody()));
                    System.out.println("Monster details fetched: " + response.getBody());
                }
            } catch (Exception e) {
                System.err.println("Error fetching monster details: " + e.getMessage());
                // Continue with next monster even if one fails
            }
        }
        
        return ResponseEntity.ok(detailedInventory);
    }

    @PostMapping("/{id}/add-xp/{xp}")
    public ResponseEntity<Map<String, String>> addExperience(@PathVariable String id, @PathVariable int xp) {
        boolean leveledUp = playerService.addExperience(id, xp);
        return ResponseEntity.ok(Map.of("message", leveledUp ? "Level up!" : "XP added"));
    }

    @PostMapping("/{id}/add-monster/{monsterId}")
    public ResponseEntity<Map<String, String>> addMonster(@PathVariable String id, @PathVariable String monsterId) {
        boolean success = playerService.addMonster(id, monsterId);
        return success 
            ? ResponseEntity.ok(Map.of("message", "Monster added!")) 
            : ResponseEntity.badRequest().body(Map.of("error", "Inventory full"));
    }

    @DeleteMapping("/{id}/remove-monster/{uniqueId}")
    public ResponseEntity<Map<String, String>> removeMonster(@PathVariable String id, @PathVariable int uniqueId) {
        boolean success = playerService.removeMonster(id, uniqueId);

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
