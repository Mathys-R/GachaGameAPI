package com.imt.GachaGameAPI.auth.dto;

import java.time.LocalDateTime;

public class TokenDTO {
    private String tokenValue;
    private String username;
    private LocalDateTime expirationDate;

    public TokenDTO() {}

    public TokenDTO(String tokenValue, String username, LocalDateTime expirationDate) {
        this.tokenValue = tokenValue;
        this.username = username;
        this.expirationDate = expirationDate;
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
}
