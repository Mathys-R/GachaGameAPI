package com.imt.GachaGameAPI.auth.service;

import com.imt.GachaGameAPI.auth.dao.UserDAO;
import com.imt.GachaGameAPI.auth.dto.UserDTO;
import com.imt.GachaGameAPI.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public UserDTO createUser(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username ne peut pas être vide");
        }
        if (userDAO.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username déjà pris");
        }
        User user = new User(username, password);
        User savedUser = userDAO.save(user);
        return new UserDTO(savedUser.getToken());
    }

    public Optional<String> getUserByUsername(String username) {
        return userDAO.findByUsername(username)
                .map(User::toString);
    }

    public void deleteUser(String username) {
        userDAO.deleteByUsername(username);
    }

    public Optional<String> getUsernameFromToken(String token) {
        return userDAO.findByToken(token)
                .filter(User::isTokenValid)
                .map(User::getUsername);
    }
}