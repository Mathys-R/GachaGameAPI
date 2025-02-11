package com.imt.GachaGameAPI.auth.dao;

import com.imt.GachaGameAPI.auth.model.AuthToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthTokenDAO extends MongoRepository<AuthToken, String> {
    Optional<AuthToken> findByToken(String token);
    void deleteByUserId(int userId);
}
