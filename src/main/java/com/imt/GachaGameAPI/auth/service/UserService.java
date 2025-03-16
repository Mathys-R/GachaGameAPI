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

    // @Autowired
    // private PlayerService playerService ;

    // @Transactional
    public UserDTO createUser(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("'Username' ne peut pas être vide");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("'Password' ne peut pas être vide");
        }
        if (userDAO.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Le nom d'utilisateur est déjà pris");
        }
    
        // Création et sauvegarde de l'utilisateur
        User user = new User(username, password);
        User savedUser = userDAO.save(user);
    
        // // Création et sauvegarde du joueur
        // Player player = new Player(savedUser.getId());
        // playerService.savePlayer(player);

        return new UserDTO(savedUser.getId(), savedUser.getToken());
    }
    

    public Optional<String> getUserByUsername(String username) {
        return userDAO.findByUsername(username)
                .map(user -> String.format(
                        "User{id='%s', username='%s', password='%s', token='%s', creationDate=%s, lastLoginDate=%s}",
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getToken(),
                        user.getCreationDate(),
                        user.getLastLoginDate()
                ));
    }
    public Optional<String> getIdByUsername(String username) {
        return userDAO.findByUsername(username)
                .map(user -> String.format(
                        "User{id='%s'}",
                        user.getId()
                ));
    }
    // public Optional<String> getIdByUsername(String username) {
    //     return userDAO.findByUsername(username)
    //             .map(user -> new UserDTO(user.getId(), user.getToken()));
    // }
    

    public void deleteUser(String username) {
        userDAO.deleteByUsername(username);
    }

    public Optional<UserDTO> reAuthenticateUser(String username, String password) {
        return userDAO.findByUsername(username)
                .map(user -> {
                    try {
                        // UserDTO result = user.reAuthenticate(username, password);
                        userDAO.save(user);
                        // return result;
                        return user.reAuthenticate(username, password);
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Échec de l'authentification : " + e.getMessage());
                    }
                });
    }

    public Optional<User> findUserByToken(String token) {
        return userDAO.findByToken(token);
    }

    public void saveUser(User user) {
        userDAO.save(user);
    }
}