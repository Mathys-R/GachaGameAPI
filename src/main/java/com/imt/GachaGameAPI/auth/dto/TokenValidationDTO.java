package com.imt.GachaGameAPI.auth.dto;

public class TokenValidationDTO {
    private String token;

    public TokenValidationDTO() {}

    public TokenValidationDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
