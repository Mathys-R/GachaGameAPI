package com.imt.GachaGameAPI.summon.service;

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
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

@Service
public class SummonService {

    @Autowired
    private SummonDao summonDao;

    @Autowired
    private MonstersService monstersService;

    @Autowired
    private PlayerService playerService;

    public SummonDto summonMonster(String userId) {
        Map<Integer,List<Monsters>> monsters = monstersService.getMonstersRarity();
        Monsters summonedMonster = getRandomMonster(monsters);

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Summon summon = new Summon(summonedMonster.getId(), userId, timestamp);
        summonDao.save(summon);

        // Ajoutez le monstre à l'inventaire du joueur
        playerService.addMonster(userId, summonedMonster.getId());

        return new SummonDto(
                summonedMonster.getId(),
                timestamp
        );
    }

    private Monsters getRandomMonster(Map<Integer, List<Monsters>> monstersByRarity) {
        Random random = new Random();
        
        // Définit les poids pour chaque niveau de rareté (1 à 9)
        Map<Integer, Integer> rarityWeights = Map.of(
            1, 45,  // Plus commun
            2, 25,
            3, 15,
            4, 10,
            5, 5,
            6, 3,
            7, 2,
            8, 1,
            9, 1   // Plus rare
        );
        
        // Calcule le poids total
        int totalWeight = rarityWeights.values().stream().mapToInt(Integer::intValue).sum();
        
        // Génère un nombre aléatoire dans la plage du poids total
        int randomWeight = random.nextInt(totalWeight);
        
        // Sélectionne le niveau de rareté basé sur le poids aléatoire
        int cumulativeWeight = 0;
        int selectedRarityLevel = 1; // Par défaut au niveau de rareté le plus bas
        for (Map.Entry<Integer, Integer> entry : rarityWeights.entrySet()) {
            cumulativeWeight += entry.getValue();
            if (randomWeight < cumulativeWeight) {
                selectedRarityLevel = entry.getKey();
                break;
            }
        }
        
        // Récupère les monstres de la rareté sélectionnée
        List<Monsters> monstersOfSelectedRarity = monstersByRarity.get(selectedRarityLevel);
        
        // Retourne un monstre aléatoire de la rareté sélectionnée
        return monstersOfSelectedRarity.get(random.nextInt(monstersOfSelectedRarity.size()));
    }
}
