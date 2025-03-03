package com.imt.GachaGameAPI.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.security.*;
import java.nio.charset.StandardCharsets;


@Getter
@Document(collection = "users")
public class User {
    @Setter
    @Id
    private String id;
    @Setter
    private String username;
    @Setter
    private String password;
    private String token;
    private LocalDateTime creationDate;
    @Setter
    private LocalDateTime lastLoginDate;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        LocalDateTime date = LocalDateTime.now();
        this.creationDate = date;
        this.lastLoginDate = date;
        this.token = generateToken();
    }

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

    public boolean isTokenValid() {
        long minutesElapsed = ChronoUnit.MINUTES.between(this.lastLoginDate, LocalDateTime.now());
        setLastLoginDate(LocalDateTime.now());
        return minutesElapsed < 60;
    }

    public String reAuthenticate(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            setLastLoginDate(LocalDateTime.now());
            return "Authentification réussie & Token de nouveau valide. Veuillez réessayer.";
        } else {
            throw new IllegalArgumentException("Nom d'utilisateur ou mot de passe incorrect");
        }
    }
}