package com.imt.GachaGameAPI.auth.dao;

import com.imt.GachaGameAPI.auth.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserDAO extends MongoRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
