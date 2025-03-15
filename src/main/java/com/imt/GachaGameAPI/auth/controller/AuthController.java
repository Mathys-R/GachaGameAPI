package com.imt.GachaGameAPI.auth.controller;

import com.imt.GachaGameAPI.auth.dto.UserDTO;
import com.imt.GachaGameAPI.auth.model.User;
import com.imt.GachaGameAPI.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8080")  // Enable CORS
// @CrossOrigin(origins = "*", allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        try {
            UserDTO createdUser = userService.createUser(username, password);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400)
                    .body(Map.of("Erreur", e.getMessage()));
        }
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        if (username.trim().isEmpty()) {
            return ResponseEntity.status(400)
                    .body(Map.of("Erreur", "Le nom d'utilisateur ne peut pas être vide"));
        }
        Optional<String> userInfo = userService.getUserByUsername(username);
        return userInfo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(Map.of("Erreur", "L'utilisateur " + username + " n'existe pas").toString()));
    }

    @GetMapping("/getId/{username}")
    public ResponseEntity<?> getId(@PathVariable String username) {
        if (username.trim().isEmpty()) {
            return ResponseEntity.status(400)
                    .body(Map.of("Erreur", "Le nom d'utilisateur ne peut pas être vide"));
        }
        Optional<String> userInfo = userService.getIdByUsername(username);
        return userInfo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(Map.of("Erreur", "L'utilisateur " + username + " n'existe pas").toString()));
    }

    @DeleteMapping({"/delete/{username}", "/delete/"})
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable(required = false) String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.status(400)
                    .body(Map.of("Erreur", "Le nom d'utilisateur ne peut pas être vide"));
        }
        if (userService.getUserByUsername(username).isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("Erreur", "L'utilisateur " + username + " n'existe pas"));
        }
        userService.deleteUser(username);
        return ResponseEntity.ok(Map.of("Succès", "Utilisateur " + username + " supprimé avec succès"));
    }

    @GetMapping({"/validate/{token}", "/validate/"})
    public ResponseEntity<?> validateToken(@PathVariable(required = false) String token) {
        if (token == null || token.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("Erreur", "Le token ne peut pas être vide"));
        }

        Optional<User> userOpt = userService.findUserByToken(token);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("Erreur", "Token inexistant : Ce token n'existe pas"));
        }

        User user = userOpt.get();
        if (!user.isTokenValid()) {
            return ResponseEntity.status(401)
                    .body(Map.of("Erreur", "Token expiré : Veuillez vous authentifier à nouveau"));
        }

        userService.saveUser(user);
        return ResponseEntity.ok(user.getUsername());
    }

    @PostMapping("/re-authenticate")
    public ResponseEntity<?> reAuthenticate(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return ResponseEntity.status(400)
                    .body(Map.of("Erreur", "Username et password sont requis"));
        }

        try {
            // Optional<String> result = userService.reAuthenticateUser(username, password);
            // Optional<String> token = userService.reAuthenticateUser(username, password);
            Optional<UserDTO> user = userService.reAuthenticateUser(username, password);

            // return result.<ResponseEntity<?>>map(s -> ResponseEntity.ok(Map.of("Succès", s))).orElseGet(() -> ResponseEntity.status(404)
            // return token.map(t -> ResponseEntity.ok(Map.of("token", t))) // Renvoie directement le token
            //     .orElseGet(() -> ResponseEntity.status(404)
            //         .body(Map.of("Erreur", "L'utilisateur " + username + " n'existe pas")));

            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401)
                    .body(Map.of("Erreur", e.getMessage()));
        }
    }
}