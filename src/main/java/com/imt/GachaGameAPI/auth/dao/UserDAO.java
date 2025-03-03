package com.imt.GachaGameAPI.auth.dao;

import com.imt.GachaGameAPI.auth.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserDAO extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);
    Optional<User> findByToken(String token);
}
