package com.imt.GachaGameAPI.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String token;
    private String testUser;

    public UserDTO(String token) {
        this.token = token;
    }

    public UserDTO() {
    }

    public void setUsername(String testUser) {
        this.testUser = testUser;
    }
}