package com.imt.GachaGameAPI.player.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.imt.GachaGameAPI.player.model.Player;


import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerDao extends MongoRepository<Player, UUID> {
    
    @Query("{ 'id' : ?0 }") 
    List<Player> findOneById(int id);

}
