package com.imt.GachaGameAPI.monsters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.imt.GachaGameAPI.monsters.model.Monsters;
import com.imt.GachaGameAPI.monsters.service.MonstersService;

import java.util.List;

@RestController
@RequestMapping("/monsters")
public class MonstersController {

    @Autowired
    private MonstersService monstersService;

    @GetMapping
    public List<Monsters> getAllMonsters() {
        return monstersService.getAllMonsters();
    }

    @GetMapping("/{id}")
    public Monsters getMonsterById(@PathVariable String id) {
        return monstersService.getMonsterById(id);
    }

    @PostMapping
    public Monsters createMonster(@RequestBody Monsters monster) {
        return monstersService.createMonster(monster);
    }

    @PutMapping("/{id}")
    public Monsters updateMonster(@PathVariable String id, @RequestBody Monsters monster) {
        return monstersService.updateMonster(id, monster);
    }

    @DeleteMapping("/{id}")
    public void deleteMonster(@PathVariable String id) {
        monstersService.deleteMonster(id);
    }
}
