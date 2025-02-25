package com.imt.GachaGameAPI.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Document(collection = "users")
public class User {
    @Setter @Getter @Id
    private String id;
    @Setter @Getter
    private String username;
    @Setter @Getter
    private String password;
    private final String token;
    private final LocalDateTime creationDate;
    private LocalDateTime lastLoginDate;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.creationDate = LocalDateTime.now();
        this.lastLoginDate = creationDate;
        this.token = generateToken();
    }

    public String generateToken() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss");
        String token = username + creationDate.format(formatter);
        return String.valueOf(Objects.hash(token));
    }

    public boolean isTokenValid() {
        long minutesElapsed = ChronoUnit.MINUTES.between(lastLoginDate, LocalDateTime.now());
        return minutesElapsed < 60;
    }

    public String getToken() {
        if (isTokenValid()) {
            lastLoginDate = LocalDateTime.now();
            return token;
        }
        return null;
    }

    public String toString() {
         return "User{" +
                 "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", creationDate=" + creationDate +
                ", lastLoginDate=" + lastLoginDate +
                '}';
    }
}