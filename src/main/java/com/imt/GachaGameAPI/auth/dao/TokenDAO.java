package com.imt.GachaGameAPI.auth.dao;

import com.imt.GachaGameAPI.auth.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface TokenDAO extends MongoRepository<Token, String> {
    Optional<Token> findByTokenValue(String tokenValue);
    void deleteByTokenValue(String tokenValue);
}
