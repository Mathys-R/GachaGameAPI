package com.imt.GachaGameAPI.summon.service;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.imt.GachaGameAPI.monsters.model.Monsters;
import com.imt.GachaGameAPI.monsters.service.MonstersService;
import com.imt.GachaGameAPI.summon.dao.SummonDao;
import com.imt.GachaGameAPI.summon.dto.SummonDto;
import com.imt.GachaGameAPI.summon.model.Summon;
import com.imt.GachaGameAPI.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 * Service gérant la logique métier des invocations de monstres.
 * Permet de réaliser des invocations aléatoires basées sur le taux de drop et de régénérer des invocations passées.
 */
@Service
public class SummonService {

    @Autowired
    private SummonDao summonDao;

    @Autowired
    private MonstersService monstersService;

    @Autowired
    private PlayerService playerService;
    
    /**
     * Invoque un monstre aléatoire pour un joueur
     * @param userId Identifiant du joueur qui fait l'invocation
     * @param token Token d'authentification pour les appels inter-services
     * @return Les informations sur le monstre invoqué
     */
    public SummonDto summonMonster(String userId, String token) {
        System.out.println("\n\n\nToken : "+token);
        // Récupération de tous les monstres disponibles pour l'invocation
        List<Monsters> allMonsters = getAllMonsters(token);
        
    
        if (allMonsters == null || allMonsters.isEmpty()) {
            throw new IllegalStateException("Aucun monstre disponible pour l'invocation.");
        }
    
        // Sélection aléatoire d'un monstre selon ses taux de drop
        Monsters summonedMonster = getRandomMonster(allMonsters);
    
        // Enregistrement de l'invocation avec horodatage
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Summon summon = new Summon(summonedMonster.getId(), userId, timestamp);
        summonDao.save(summon);
    
        // Ajout du monstre invoqué à l'inventaire du joueur
        addMonster(userId, summonedMonster.getId(), token);
    
        return new SummonDto(summonedMonster.getId(), timestamp);
    }

    /**
     * Récupère tous les monstres disponibles depuis l'API de monstres.
     * Utilise l'authentification par token pour sécuriser la communication inter-services.
     * @param token Token d'authentification
     * @return Liste de tous les monstres disponibles
     */
    public List<Monsters> getAllMonsters(String token) {
        // Préparation des en-têtes avec authentification
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api-monsters:8083/monsters/";

        // Appel au service de monstres
        ResponseEntity<Monsters[]> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            Monsters[].class
        );

        Monsters[] monstersArray = response.getBody();
        System.err.println("\n\n\n");
        System.out.println(monstersArray);
        List<Monsters> monstersList = monstersArray != null ? List.of(monstersArray) : new ArrayList<>();

        System.out.println("SummonService.getAllMonsters() appelé depuis l'API Monsters. Trouvé: " + monstersList.size() + " monstres");
        return monstersList;
    }    

    /**
     * Ajoute un monstre à l'inventaire d'un joueur via l'API de gestion des joueurs.
     * Communication inter-services sécurisée par token.
     * @param userId Identifiant du joueur
     * @param monsterId Identifiant du monstre à ajouter
     * @param token Token d'authentification
     */
    public void addMonster(String userId, String monsterId, String token) {
        // Configuration des en-têtes avec le token d'authentification
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api-player:8082/player/" + userId + "/add-monster/" + monsterId;

        // Appel au service de joueurs pour ajouter le monstre à l'inventaire
        ResponseEntity<Void> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            Void.class
        );
        
        System.out.println("Monstre " + monsterId +"ajouté au joueur " + userId);
    }   

    /**
     * Sélectionne un monstre aléatoire en tenant compte des taux de drop.
     * Utilise une approche de probabilité pondérée basée sur les taux de loot.
     * @param allMonsters Liste de tous les monstres disponibles
     * @return Monstre aléatoirement sélectionné selon sa probabilité
     */
    private Monsters getRandomMonster(List<Monsters> allMonsters) {
        Random random = new Random();
    
        // Calcul du taux de drop total
        double totalLootRate = allMonsters.stream().mapToDouble(Monsters::getLootRate).sum();
    
        if (totalLootRate <= 0) {
            throw new IllegalStateException("Le taux de loot total doit être une valeur positive");
        }
    
        // Sélection pondérée par le taux de loot
        double randomLoot = random.nextDouble() * totalLootRate;
        double cumulativeLoot = 0.0;
    
        for (Monsters monster : allMonsters) {
            cumulativeLoot += monster.getLootRate();
            if (randomLoot < cumulativeLoot) {
                return monster;
            }
        }
    
        // Fallback si problème dans la logique ci-dessus
        return allMonsters.get(random.nextInt(allMonsters.size()));
    }
    
    public List<SummonDto> regenerateSummons(String userId, List<SummonDto> pastSummons) {
        List<SummonDto> regeneratedSummons = new ArrayList<>();
    
        for (SummonDto pastSummon : pastSummons) {
            // Récupération du monstre correspondant à l'ancienne invocation
            Monsters originalMonster = monstersService.getMonsterById(pastSummon.getMonsterId());
    
            if (originalMonster != null) {
                // Création d'un nouvel enregistrement d'invocation avec le même monstre
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                Summon summon = new Summon(originalMonster.getId(), userId, timestamp);
                summonDao.save(summon);
    
                // Ajout du monstre à l'inventaire du joueur
                playerService.addMonster(userId, originalMonster.getId());
    
                // Ajout à la liste des invocations re-générées
                regeneratedSummons.add(new SummonDto(originalMonster.getId(), timestamp));
            }
        }
    
        return regeneratedSummons;
    }
    
}
