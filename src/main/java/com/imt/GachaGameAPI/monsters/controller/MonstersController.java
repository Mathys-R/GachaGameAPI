package com.imt.GachaGameAPI.monsters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.imt.GachaGameAPI.monsters.model.Monsters;
import com.imt.GachaGameAPI.monsters.service.MonstersService;

// import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/monsters")
// @SecurityRequirement(name = "bearerAuth") 
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true") 
// @CrossOrigin(origins = "*", allowCredentials = "true")

public class MonstersController {

    @Autowired
    private MonstersService monstersService;

    @GetMapping("/")
    public ResponseEntity<List<Monsters>> getAllMonsters() {
        List<Monsters> monsters = monstersService.getAllMonsters();
        return ResponseEntity.ok(monsters);
    }


    @GetMapping("/rarity")
    public ResponseEntity<Map<Integer, List<Monsters>>> getMonstersRarity() {
        Map<Integer, List<Monsters>> monsters = monstersService.getMonstersRarity();
        return ResponseEntity.ok(monsters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monsters> getMonsterById(@PathVariable String id) {
        try {
            Monsters monster = monstersService.getMonsterById(id);
            return ResponseEntity.ok(monster);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/list")
    // list of monsters info for one player's inventory 
    public ResponseEntity<List<Monsters>> getMonstersByIds(@RequestParam List<String> ids) {
        try {
            List<Monsters> monsters = monstersService.getMonstersByIds(ids);
            return ResponseEntity.ok(monsters);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Monsters> createMonster(@RequestBody Monsters monster) {
        try {
            Monsters createdMonster = monstersService.createMonster(monster);
            return ResponseEntity.ok(createdMonster);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Monsters> updateMonster(@PathVariable String id, @RequestBody Monsters monster) {
        try {
            Monsters updatedMonster = monstersService.updateMonster(id, monster);
            return ResponseEntity.ok(updatedMonster);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonster(@PathVariable String id) {
        try {
            monstersService.deleteMonster(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}