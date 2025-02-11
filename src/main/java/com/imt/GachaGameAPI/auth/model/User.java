package com.imt.GachaGameAPI.auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class User {

    @Id
    private int id;

    @Field("username")
    private String username;
    private String password;
    private AuthToken token;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.token = null;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }

    public void generateToken() {
        AuthToken token = new AuthToken(this.id);
        setToken(token);
    }
}
