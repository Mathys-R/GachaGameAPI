package com.example.GachaGameAPI.auth;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class authentification {

    @MongoId
    private static int lastId = 0;
    private final int id;
    private String username;
    private String password;

    public authentification(String username, String password) {
        this.id = ++lastId;
        this.username = username;
        this.password = password;
    }
}
