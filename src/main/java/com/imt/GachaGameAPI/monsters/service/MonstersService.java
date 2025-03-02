package com.imt.GachaGameAPI.monsters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.imt.GachaGameAPI.monsters.dao.MonstersDao;
import com.imt.GachaGameAPI.monsters.model.Monsters;
import java.util.List;

@Service
public class MonstersService {

    @Autowired
    private MonstersDao monsterDao;

    public List<Monsters> getAllMonsters() {
        return monsterDao.findAll();
    }

    public Monsters getMonsterById(String id) {
        return monsterDao.findById(id).orElseThrow(() -> new RuntimeException("Monster not found with id: " + id));
    }

    public Monsters createMonster(Monsters monster) {
        return monsterDao.save(monster);
    }

    public Monsters updateMonster(String id, Monsters monster) {
        if (!monsterDao.existsById(id)) {
            throw new RuntimeException("Monster not found with id: " + id);
        }
        monster.setId(id);
        return monsterDao.save(monster);
    }

    public void deleteMonster(String id) {
        if (!monsterDao.existsById(id)) {
            throw new RuntimeException("Monster not found with id: " + id);
        }
        monsterDao.deleteById(id);
    }
}