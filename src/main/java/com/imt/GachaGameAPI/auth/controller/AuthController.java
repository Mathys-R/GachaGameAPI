package com.imt.GachaGameAPI.auth.controller;

import com.imt.GachaGameAPI.auth.dto.UserDTO;
import com.imt.GachaGameAPI.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        UserDTO createdUser = userService.createUser(username, password);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        Optional<String> userInfo = userService.getUserByUsername(username);
        return userInfo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(Map.of("erreur", "L'utilisateur " + username + " n'existe pas").toString()));
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String username) {
        if (userService.getUserByUsername(username).isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("erreur", "L'utilisateur " + username + " n'existe pas"));
        }
        userService.deleteUser(username);
        return ResponseEntity.ok(Map.of("message", username + " supprimé avec succès"));
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<?> validateToken(@PathVariable String token) {
        if (token == null || token.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erreur", "Le token ne peut pas être vide"));
        }
        return userService.getUsernameFromToken(token)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401)
                        .body(Map.of("erreur", "Veuillez vous authentifier à nouveau").toString()));
    }
}