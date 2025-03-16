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
        List<Monsters> allMonsters = monstersService.getAllMonsters();
    
        if (allMonsters == null || allMonsters.isEmpty()) {
            throw new IllegalStateException("No monsters available for summoning.");
        }
    
        Monsters summonedMonster = getRandomMonster(allMonsters);
    
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Summon summon = new Summon(summonedMonster.getId(), userId, timestamp);
        summonDao.save(summon);
    
        playerService.addMonster(userId, summonedMonster.getId());
    
        return new SummonDto(summonedMonster.getId(), timestamp);
    }


    private Monsters getRandomMonster(List<Monsters> allMonsters) {
        Random random = new Random();
    
        double totalLootRate = allMonsters.stream().mapToDouble(Monsters::getLootRate).sum();
    
        if (totalLootRate <= 0) {
            throw new IllegalStateException("Total lootRate must be a positive value");
        }
    
        double randomLoot = random.nextDouble() * totalLootRate;
        double cumulativeLoot = 0.0;
    
        for (Monsters monster : allMonsters) {
            cumulativeLoot += monster.getLootRate();
            if (randomLoot < cumulativeLoot) {
                return monster;
            }
        }
    
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
