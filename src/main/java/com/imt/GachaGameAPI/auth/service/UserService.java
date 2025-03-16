package com.imt.GachaGameAPI.auth.service;

import com.imt.GachaGameAPI.auth.dao.UserDAO;
import com.imt.GachaGameAPI.auth.dto.UserDTO;
import com.imt.GachaGameAPI.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service pour gérer les opérations liées aux utilisateurs.
 * Fournit des méthodes pour créer, récupérer, supprimer et réauthentifier des utilisateurs.
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    /**
     * Crée un nouvel utilisateur avec le nom d'utilisateur et le mot de passe spécifiés.
     *
     * @param username Le nom d'utilisateur du nouvel utilisateur
     * @param password Le mot de passe du nouvel utilisateur
     * @return Un UserDTO contenant l'ID et le token de l'utilisateur créé
     * @throws IllegalArgumentException Si le nom d'utilisateur ou le mot de passe est vide, ou si le nom d'utilisateur est déjà pris
     */
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

        User user = new User(username, password);
        User savedUser = userDAO.save(user);

        return new UserDTO(savedUser.getId(), savedUser.getToken());
    }

    /**
     * Récupère les informations d'un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur à rechercher
     * @return Une chaîne formatée avec les informations de l'utilisateur, ou un Optional vide si l'utilisateur n'existe pas
     */
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

    /**
     * Récupère l'identifiant d'un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur à rechercher
     * @return Une chaîne formatée avec l'ID de l'utilisateur, ou un Optional vide si l'utilisateur n'existe pas
     */
    public Optional<String> getIdByUsername(String username) {
        return userDAO.findByUsername(username)
                .map(user -> String.format(
                        "User{id='%s'}",
                        user.getId()
                ));
    }

    /**
     * Supprime un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur de l'utilisateur à supprimer
     */
    public void deleteUser(String username) {
        userDAO.deleteByUsername(username);
    }

    /**
     * Réauthentifie un utilisateur avec son nom d'utilisateur et son mot de passe.
     *
     * @param username Le nom d'utilisateur à vérifier
     * @param password Le mot de passe à vérifier
     * @return Un UserDTO contenant l'ID et le token de l'utilisateur si l'authentification est réussie
     * @throws IllegalArgumentException Si le nom d'utilisateur ou le mot de passe est incorrect
     */
    public Optional<UserDTO> reAuthenticateUser(String username, String password) {
        return userDAO.findByUsername(username)
                .map(user -> {
                    try {
                        userDAO.save(user);
                        return user.reAuthenticate(username, password);
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Échec de l'authentification : " + e.getMessage());
                    }
                });
    }

    /**
     * Trouve un utilisateur par son token d'authentification.
     *
     * @param token Le token à rechercher
     * @return Un Optional contenant l'utilisateur si trouvé, sinon un Optional vide
     */
    public Optional<User> findUserByToken(String token) {
        return userDAO.findByToken(token);
    }

    /**
     * Sauvegarde un utilisateur dans la base de données.
     *
     * @param user L'utilisateur à sauvegarder
     */
    public void saveUser(User user) {
        userDAO.save(user);
    }
}