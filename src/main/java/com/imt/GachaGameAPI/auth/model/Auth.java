package com.imt.GachaGameAPI.auth.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Auth {

    @MongoId
    private static int lastId = 0;
    private final int id;
    private String username;
    private String password;

    public Auth(String username, String password) {
        this.id = ++lastId;
        this.username = username;
        this.password = password;
    }
}
