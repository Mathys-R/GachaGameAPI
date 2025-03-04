package com.imt.GachaGameAPI.player.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.imt.GachaGameAPI.player.model.Player;


import java.util.List;

@Repository
public interface PlayerDao extends MongoRepository<Player, String> {
    
    List<Player> findOneById(int id);

}
