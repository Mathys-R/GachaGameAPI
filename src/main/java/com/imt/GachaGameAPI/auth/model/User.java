package com.imt.GachaGameAPI.auth.model;

import com.imt.GachaGameAPI.auth.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Représente un utilisateur dans le système d'authentification.
 * Cette entité est stockée dans la collection MongoDB "users".
 * Elle contient les informations d'authentification comme le nom d'utilisateur,
 * le mot de passe, le token d'authentification et les dates associées.
 */
@Getter
@Document(collection = "users")
public class User {
    private LocalDateTime creationDate;
    @Setter
    @Id
    private String id;
    @Setter
    private String username;
    @Setter
    private String password;
    @Setter
    private String token;
    @Setter
    private LocalDateTime lastLoginDate;

    /**
     * Crée un nouvel utilisateur avec un nom d'utilisateur et un mot de passe spécifiés.
     * Initialise les dates de création et de dernière connexion à l'heure actuelle,
     * et génère un token d'authentification.
     *
     * @param username Le nom d'utilisateur du nouvel utilisateur
     * @param password Le mot de passe du nouvel utilisateur
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        LocalDateTime date = LocalDateTime.now();
        this.creationDate = date;
        this.lastLoginDate = date;
        this.token = generateToken();
    }

    public User() {
    }

    /**
     * Génère un token d'authentification à partir du nom d'utilisateur et de la date de création.
     * Le token est calculé en appliquant un hachage SHA-256.
     *
     * @return Le token généré sous forme de chaîne hexadécimale
     * @throws RuntimeException Si l'algorithme de hachage n'est pas disponible
     */
    private String generateToken() {
        String tokenData = this.username + this.creationDate.toString();
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256")
                    .digest(tokenData.getBytes(StandardCharsets.UTF_8));
            return String.format("%064x", new java.math.BigInteger(1, hash));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de génération du token", e);
        }
    }

    /**
     * Vérifie si le token de l'utilisateur est toujours valide.
     * Un token est considéré comme valide si moins de 60 minutes se sont écoulées
     * depuis la dernière connexion. Met à jour la date de dernière connexion.
     *
     * @return true si le token est valide, false sinon
     */
    public boolean isTokenValid() {
        long minutesElapsed = ChronoUnit.MINUTES.between(this.lastLoginDate, LocalDateTime.now());
        setLastLoginDate(LocalDateTime.now());
        return minutesElapsed < 60;
    }

    /**
     * Réauthentifie un utilisateur en vérifiant son nom d'utilisateur et son mot de passe.
     * Met à jour la date de dernière connexion si l'authentification est réussie.
     *
     * @param username Le nom d'utilisateur à vérifier
     * @param password Le mot de passe à vérifier
     * @return Un UserDTO contenant l'ID et le token de l'utilisateur si l'authentification est réussie
     * @throws IllegalArgumentException Si le nom d'utilisateur ou le mot de passe est incorrect
     */
    public UserDTO reAuthenticate(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            setLastLoginDate(LocalDateTime.now());
            // return "Authentification réussie & Token de nouveau valide. Veuillez réessayer.";
            return new UserDTO(this.id, this.token);
        } else {
            throw new IllegalArgumentException("Nom d'utilisateur ou mot de passe incorrect");
        }
    }
}