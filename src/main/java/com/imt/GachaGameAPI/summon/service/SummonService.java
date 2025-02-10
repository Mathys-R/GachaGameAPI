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

@Service
public class SummonService {

    @Autowired
    private SummonDao summonDao;

    @Autowired
    private MonstersService monstersService;

    @Autowired
    private PlayerService playerService;

    public SummonDto summonMonster(String userId) {
        List<Monsters> monsters = monstersService.getAllMonsters();
        Monsters summonedMonster = getRandomMonster(monsters);

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Summon summon = new Summon(summonedMonster.getId(), userId, timestamp);
        summonDao.save(summon);

        // playerService.addMonsterToPlayer(userId, summonedMonster.getId());

        return new SummonDto(
                summonedMonster.getId(),
                summonedMonster.getTypeElementaire(),
                timestamp
        );
    }

    private Monsters getRandomMonster(List<Monsters> monsters) {
        double totalRate = monsters.stream().mapToDouble(Monsters::getLootRate).sum();
        double randomValue = Math.random() * totalRate;

        double cumulativeRate = 0;
        for (Monsters monster : monsters) {
            cumulativeRate += monster.getLootRate();
            if (randomValue <= cumulativeRate) {
                return monster;
            }
        }

        throw new IllegalStateException("Aucun monstre n'a été sélectionné ! Vérifiez les taux d'invocation.");
    }
}
