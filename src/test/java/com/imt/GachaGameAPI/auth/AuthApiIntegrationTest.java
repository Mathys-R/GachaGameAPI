package com.imt.GachaGameAPI.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imt.GachaGameAPI.auth.controller.AuthController;
import com.imt.GachaGameAPI.auth.dto.UserDTO;
import com.imt.GachaGameAPI.auth.model.User;
import com.imt.GachaGameAPI.auth.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Map;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(PassedTestLoggerExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
class AuthApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void testSuccessfulRegister() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setToken("7f8d9a63b4e2c1f0");

        when(userService.createUser("testUser1", "testPassword1")).thenReturn(userDTO);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "username", "testUser1",
                                "password", "testPassword1"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("7f8d9a63b4e2c1f0"));

        verify(userService).createUser("testUser1", "testPassword1");
    }

    @Test
    void testRegisterWithExistingUsername() throws Exception {
        // Set up mock to throw exception for existing user
        when(userService.createUser("testUser1", "testPassword1"))
                .thenThrow(new IllegalArgumentException("Le nom d'utilisateur est déjà pris"));

        // Test register with existing username
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "username", "testUser1",
                                "password", "testPassword1"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Erreur").value("Le nom d'utilisateur est déjà pris"));
    }

    @Test
    void testGetUser() throws Exception {
        // Set up mock response for existing user
        User mockUser = new User();
        mockUser.setUsername("testUser1");
        mockUser.setPassword("testPassword1");
        mockUser.setToken("7f8d9a63b4e2c1f0");

        String userInfo = "User{id='60f8a12c3d4e5f67890abcde', username='testUser1', password='testPassword1', " +
                "token='7f8d9a63b4e2c1f0...', creationDate=2023-07-12T10:15:30, lastLoginDate=2023-07-12T10:15:30}";

        when(userService.getUserByUsername("testUser1")).thenReturn(Optional.of(userInfo));

        // Test get user success
        mockMvc.perform(get("/auth/get/testUser1"))
                .andExpect(status().isOk())
                .andExpect(content().string(userInfo));

        verify(userService).getUserByUsername("testUser1");
    }

    @Test
    void testGetNonExistentUser() throws Exception {
        // Set up mock for non-existent user
        when(userService.getUserByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // Test get non-existent user
        mockMvc.perform(get("/auth/get/nonExistentUser"))
                .andExpect(status().isNotFound());

        verify(userService).getUserByUsername("nonExistentUser");
        verify(userService, never()).deleteUser("nonExistentUser");
    }

//    @Test
//    void testValidateToken() throws Exception {
//        // Set up mock for valid token
//        User mockUser = new User();
//        mockUser.setUsername("testUser1");
//        mockUser.setPassword("hashedPassword");
//        mockUser.setToken("7f8d9a63b4e2c1f0");
//        when(mockUser.isTokenValid()).thenReturn(true);
//
//        when(userService.findUserByToken("7f8d9a63b4e2c1f0")).thenReturn(Optional.of(mockUser));
//
//        // Test validate token success
//        mockMvc.perform(get("/auth/validate/7f8d9a63b4e2c1f0"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("testUser1"));
//
//        verify(userService).findUserByToken("7f8d9a63b4e2c1f0");
//        verify(userService).saveUser(mockUser);
//    }

//    @Test
//    void testValidateExpiredToken() throws Exception {
//        // Set up mock for expired token
//        User mockUser = new User();
//        mockUser.setUsername("testUser1");
//        mockUser.setToken("expiredToken");
//        when(mockUser.isTokenValid()).thenReturn(false);
//
//        when(userService.findUserByToken("expiredToken")).thenReturn(Optional.of(mockUser));
//
//        // Test validate expired token
//        mockMvc.perform(get("/auth/validate/expiredToken"))
//                .andExpect(status().isUnauthorized())
//                .andExpect(jsonPath("$.Erreur").value("Token expiré : Veuillez vous authentifier à nouveau"));
//
//        verify(userService).findUserByToken("expiredToken");
//        verify(userService, never()).saveUser(any(User.class));
//    }

    @Test
    void testReAuthenticate() throws Exception {
        // Set up mock for re-authentication
        when(userService.reAuthenticateUser("testUser1", "testPassword1"))
                .thenReturn(Optional.of("Token mis à jour avec succès"));

        // Test re-authenticate success
        mockMvc.perform(post("/auth/re-authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "username", "testUser1",
                                "password", "testPassword1"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Succès").value("Token mis à jour avec succès"));

        verify(userService).reAuthenticateUser("testUser1", "testPassword1");
    }

    @Test
    void testReAuthenticateWithWrongCredentials() throws Exception {
        // Set up mock for failed re-authentication
        when(userService.reAuthenticateUser("testUser1", "wrongPassword"))
                .thenThrow(new IllegalArgumentException("Échec de l'authentification : Nom d'utilisateur ou mot de passe incorrect"));

        // Test re-authenticate with wrong credentials
        mockMvc.perform(post("/auth/re-authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "username", "testUser1",
                                "password", "wrongPassword"))))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.Erreur").value("Échec de l'authentification : Nom d'utilisateur ou mot de passe incorrect"));

        verify(userService).reAuthenticateUser("testUser1", "wrongPassword");
    }

    @Test
    void testDeleteUser() throws Exception {
        // Set up mock for existing user
        when(userService.getUserByUsername("testUser1")).thenReturn(Optional.of("userInfo"));
        doNothing().when(userService).deleteUser("testUser1");

        // Test delete user success
        mockMvc.perform(delete("/auth/delete/testUser1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Succès").value("Utilisateur testUser1 supprimé avec succès"));

        verify(userService).getUserByUsername("testUser1");
        verify(userService).deleteUser("testUser1");
    }

    @Test
    void testDeleteNonExistentUser() throws Exception {
        // Set up mock for non-existent user
        when(userService.getUserByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // Test delete non-existent user
        mockMvc.perform(delete("/auth/delete/nonExistentUser"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.Erreur").value("L'utilisateur nonExistentUser n'existe pas"));

        verify(userService).getUserByUsername("nonExistentUser");
        verify(userService, never()).deleteUser("nonExistentUser");
    }
}