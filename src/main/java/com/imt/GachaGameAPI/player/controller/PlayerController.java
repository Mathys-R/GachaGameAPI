package com.imt.GachaGameAPI.player.controller;

import com.imt.GachaGameAPI.player.dto.PlayerDto;
import com.imt.GachaGameAPI.player.model.Player;
import com.imt.GachaGameAPI.player.service.PlayerService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {
    
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> createPlayer(@Valid @RequestBody PlayerDto player) {
        playerService.savePlayer(
            new Player(
                player.getId(),
                player.getLevel(),
                player.getInventory()
                )
        );
        
        return ResponseEntity.ok("saved !");
    }

    @GetMapping("/")
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        List<PlayerDto> players = playerService.findAllPlayers()
            .stream()
            .map(player -> new PlayerDto(player.getId(), player.getLevel(), player.getInventory()))
            .toList();
        
        return ResponseEntity.ok(players);
    }

}
