package com.imt.GachaGameAPI.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String token;

    public UserDTO(String token) {
        this.token = token;
    }
}