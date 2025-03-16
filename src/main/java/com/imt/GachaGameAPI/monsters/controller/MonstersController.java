package com.imt.GachaGameAPI.monsters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.imt.GachaGameAPI.monsters.model.Monsters;
import com.imt.GachaGameAPI.monsters.service.MonstersService;

import com.imt.GachaGameAPI.player.dto.Mob;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des monstres.
 * Cette classe expose les endpoints pour manipuler les données des monstres dans l'application.
 */
@RestController
@RequestMapping("/monsters")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true") 
public class MonstersController {

    @Autowired
    private MonstersService monstersService;

    /**
     * Récupère la liste de tous les monstres disponibles
     * @return Liste complète des monstres dans la base de données
     */
    @GetMapping("/")
    public ResponseEntity<List<Monsters>> getAllMonsters() {
        List<Monsters> monsters = monstersService.getAllMonsters();
        return ResponseEntity.ok(monsters);
    }

    /**
     * Récupère un monstre spécifique par son identifiant
     * @param id Identifiant unique du monstre recherché
     * @return Le monstre correspondant à l'identifiant ou une erreur 404 si non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<Monsters> getMonsterById(@PathVariable String id) {
        try {
            Monsters monster = monstersService.getMonsterById(id);
            return ResponseEntity.ok(monster);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Récupère les détails de plusieurs monstres à partir de leurs identifiants
     * Utile pour afficher l'inventaire complet d'un joueur
     * @param monsterIds Liste des identifiants des monstres à récupérer
     * @return Liste des monstres correspondants ou erreur 404 si non trouvés
     */
    @GetMapping("/list")
    public ResponseEntity<List<Monsters>> getMonstersByIds(@RequestParam List<String> monsterIds) {
        try {
            List<Monsters> monsters = monstersService.getMonstersByIds(monsterIds);
            return ResponseEntity.ok(monsters);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crée un nouveau monstre dans le système
     * @param monster Données du monstre à créer
     * @return Le monstre créé avec son identifiant généré ou une erreur en cas d'échec
     */
    @PostMapping("/save")
    public ResponseEntity<Monsters> createMonster(@RequestBody Monsters monster) {
        try {
            Monsters createdMonster = monstersService.createMonster(monster);
            return ResponseEntity.ok(createdMonster);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Met à jour les informations d'un monstre existant
     * @param id Identifiant unique du monstre à mettre à jour
     * @param monster Nouvelles données du monstre
     * @return Le monstre mis à jour ou une erreur 404 si non trouvé
     */
    @PutMapping("/{id}")
    public ResponseEntity<Monsters> updateMonster(@PathVariable String id, @RequestBody Monsters monster) {
        try {
            Monsters updatedMonster = monstersService.updateMonster(id, monster);
            return ResponseEntity.ok(updatedMonster);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Supprime un monstre du système
     * @param id Identifiant unique du monstre à supprimer
     * @return Une réponse vide avec un statut 200 si la suppression a réussi ou une erreur 404 si non trouvé
     */
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