package com.imt.GachaGameAPI.auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "tokens")
public class AuthToken {
    @Id
    private String token;
    private int userId;
    private LocalDateTime lastAccess;
    private LocalDateTime expiration;

    public AuthToken() {}

    public AuthToken(int id) {
        this.token = UUID.randomUUID().toString();
        this.userId = id;
        this.lastAccess = LocalDateTime.now();
        this.expiration = LocalDateTime.now().plusHours(1);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = id;
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(LocalDateTime lastAccess) {
        this.lastAccess = lastAccess;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}