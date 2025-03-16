package com.imt.GachaGameAPI.summon.dao;

import com.imt.GachaGameAPI.summon.model.Summon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonDao extends MongoRepository<Summon, String> {
}
