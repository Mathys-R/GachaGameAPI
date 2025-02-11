package com.imt.GachaGameAPI.auth.controller;

import com.imt.GachaGameAPI.auth.dto.UserDTO;
import com.imt.GachaGameAPI.auth.service.UserService;
import com.imt.GachaGameAPI.auth.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Bienvenue sur l'API d'authentification User !");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        Optional<User> userOpt = userService.findById(id);

        return userOpt.map(user -> ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
