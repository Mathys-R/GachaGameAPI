package com.imt.GachaGameAPI.monsters.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.imt.GachaGameAPI.monsters.model.Monsters;

public interface MonstersDao extends MongoRepository<Monsters, String> {}
