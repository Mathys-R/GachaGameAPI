package com.imt.GachaGameAPI.monsters.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.imt.GachaGameAPI.monsters.model.Monsters;

@Repository
public interface MonstersDao extends MongoRepository<Monsters, String> {}
