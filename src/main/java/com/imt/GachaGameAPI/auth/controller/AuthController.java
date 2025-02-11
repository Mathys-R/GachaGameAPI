package com.imt.GachaGameAPI.auth.controller;

import com.imt.GachaGameAPI.auth.dto.AuthRequestDTO;
import com.imt.GachaGameAPI.auth.dto.AuthResponseDTO;
import com.imt.GachaGameAPI.auth.dto.RegisterDTO;
import com.imt.GachaGameAPI.auth.service.UserService;
import com.imt.GachaGameAPI.auth.model.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
        User newUser = userService.registerUser(registerDTO.getUsername(), registerDTO.getPassword());

        if (newUser.getToken() != null) {
            return ResponseEntity.ok(new AuthResponseDTO(newUser.getToken().getToken()));
        } else {
            return ResponseEntity.status(500).body(new AuthResponseDTO(null));
        }
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Bienvenue sur l'API d'authentification Auth !");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        Optional<User> userOpt = userService.findByUsername(authRequestDTO.getUsername());

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(authRequestDTO.getPassword())) {
            return ResponseEntity.ok(new AuthResponseDTO(userOpt.get().getToken().getToken()));
        } else {
            return ResponseEntity.status(401).body(new AuthResponseDTO(null));
        }
    }
}
