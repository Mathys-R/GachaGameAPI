package com.imt.GachaGameAPI.auth.service;

import com.imt.GachaGameAPI.auth.dao.UserDAO;
import com.imt.GachaGameAPI.auth.dao.AuthTokenDAO;
import com.imt.GachaGameAPI.auth.model.User;
import com.imt.GachaGameAPI.auth.model.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    private final UserDAO userDAO;
    private final AuthTokenDAO authTokenDAO;

    @Autowired
    public AuthService(UserDAO userDAO, AuthTokenDAO authTokenDAO) {
        this.userDAO = userDAO;
        this.authTokenDAO = authTokenDAO;
    }

    public Optional<AuthToken> login(String username, String password) {
        Optional<User> userOpt = userDAO.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                authTokenDAO.deleteByUserId(user.getId());

                AuthToken newToken = new AuthToken(user.getId());
                authTokenDAO.save(newToken);
                return Optional.of(newToken);
            }
        }
        return Optional.empty();
    }

    public boolean validateToken(String token) {
        Optional<AuthToken> authTokenOpt = authTokenDAO.findByToken(token);
        return authTokenOpt.isPresent() && authTokenOpt.get().getExpiration().isAfter(java.time.LocalDateTime.now());
    }

    public void logout(String token) {
        authTokenDAO.findByToken(token).ifPresent(authTokenDAO::delete);
    }
}
