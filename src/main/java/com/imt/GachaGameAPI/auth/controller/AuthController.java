package com.imt.GachaGameAPI.auth.controller;

import com.imt.GachaGameAPI.auth.dto.TokenDTO;
import com.imt.GachaGameAPI.auth.dto.UserDTO;
import com.imt.GachaGameAPI.auth.service.UserService;
import com.imt.GachaGameAPI.auth.service.TokenService;
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

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO.getUsername(), userDTO.getPassword());
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/user")
    public ResponseEntity<?> getUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        Optional<UserDTO> userDTO = userService.getUserByUsername(username);

        return userDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body((UserDTO) Map.of("message", "Utilisateur non trouvé")));
    }

    @PostMapping("/token")
    public ResponseEntity<TokenDTO> generateToken(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        TokenDTO tokenDTO = tokenService.createToken(username);
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/user/delete")
    public ResponseEntity<Map<String, String>> deleteUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        userService.deleteUser(username);
        return ResponseEntity.ok(Map.of("message", username + "supprimé avec succès"));
    }

    @PostMapping("/token/validate")
    public ResponseEntity<Map<String, Boolean>> validateToken(@RequestBody Map<String, String> request) {
        String tokenValue = request.get("tokenValue");
        boolean isValid = tokenService.isTokenValid(tokenValue);
        return ResponseEntity.ok(Map.of("isValid", isValid));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        tokenService.refreshToken(token);
        return ResponseEntity.ok(Map.of("message", "Token rafraîchi avec succès"));
    }
}
