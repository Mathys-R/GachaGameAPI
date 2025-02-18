package com.imt.GachaGameAPI.auth.service;

import com.imt.GachaGameAPI.auth.dao.TokenDAO;
import com.imt.GachaGameAPI.auth.dto.TokenDTO;
import com.imt.GachaGameAPI.auth.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private TokenDAO tokenDAO;

    public TokenDTO createToken(String username) {
        Token token = new Token(username);
        Token savedToken = tokenDAO.save(token);
        return new TokenDTO(savedToken.getTokenValue(), savedToken.getUsername(), savedToken.getExpirationDate());
    }


    public boolean isTokenValid(String tokenValue) {
        Optional<Token> token = tokenDAO.findByTokenValue(tokenValue);
        return token.isPresent() && token.get().getExpirationDate().isAfter(LocalDateTime.now());
    }

    public void refreshToken(String tokenValue) {
        Optional<Token> token = tokenDAO.findByTokenValue(tokenValue);
        token.ifPresent(t -> {
            t.refreshExpiration();
            tokenDAO.save(t);
        });
    }

    public void deleteToken(String tokenValue) {
        tokenDAO.deleteByTokenValue(tokenValue);
    }
}
