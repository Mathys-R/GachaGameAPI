package com.example.GachaGameAPI.monstre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monstres")
public class MonstreController {

    @Autowired
    private MonstreService monstreService;

    @GetMapping
    public List<Monstre> getAllMonstres() {
        return monstreService.getAllMonstres();
    }

    @GetMapping("/{id}")
    public Monstre getMonstreById(@PathVariable String id) {
        return monstreService.getMonstreById(id);
    }

    @PostMapping
    public Monstre createMonstre(@RequestBody Monstre monstre) {
        return monstreService.createMonstre(monstre);
    }

    @PutMapping("/{id}")
    public Monstre updateMonstre(@PathVariable String id, @RequestBody Monstre monstre) {
        return monstreService.updateMonstre(id, monstre);
    }

    @DeleteMapping("/{id}")
    public void deleteMonstre(@PathVariable String id) {
        monstreService.deleteMonstre(id);
    }
}
