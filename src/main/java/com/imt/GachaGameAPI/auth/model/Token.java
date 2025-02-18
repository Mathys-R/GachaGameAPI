package com.imt.GachaGameAPI.auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "tokens")
public class Token {
    @Id
    private String id;
    private String tokenValue;
    private String username;
    private LocalDateTime expirationDate;

    public Token() {
        this.tokenValue = UUID.randomUUID().toString();
        this.expirationDate = LocalDateTime.now().plusHours(1);
    }

    public Token(String username) {
        this.username = username;
        this.tokenValue = UUID.randomUUID().toString();
        this.expirationDate = LocalDateTime.now().plusHours(1);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void refreshExpiration() {
        this.expirationDate = LocalDateTime.now().plusHours(1);
    }
}
