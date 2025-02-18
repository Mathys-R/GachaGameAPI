package com.imt.GachaGameAPI.player.controller;

import com.imt.GachaGameAPI.player.dto.PlayerDto;
import com.imt.GachaGameAPI.player.model.Player;
import com.imt.GachaGameAPI.player.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PlayerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    void testCreatePlayer() throws Exception {
        Player player = new Player(1, 5, 100, List.of());

        doNothing().when(playerService).savePlayer(any(Player.class));

        mockMvc.perform(post("/player/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"level\":5,\"experience\":100,\"inventory\":[]}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Player saved!"));
    }

    @Test
    void testGetAllPlayers() throws Exception {
        List<PlayerDto> players = List.of(new PlayerDto(1, 5, 100, List.of("monster1")));

        when(playerService.findAllPlayers()).thenReturn(List.of(new Player(1, 5, 100, List.of("monster1"))));

        mockMvc.perform(get("/player/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].level").value(5))
                .andExpect(jsonPath("$[0].experience").value(100))
                .andExpect(jsonPath("$[0].inventory[0]").value("monster1"));
    }

    @Test
    void testGetPlayerById_Found() throws Exception {
        Player player = new Player(1, 5, 100, List.of("monster1"));
        when(playerService.findPlayerById(1)).thenReturn(Optional.of(player));

        mockMvc.perform(get("/player/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.level").value(5))
                .andExpect(jsonPath("$.experience").value(100))
                .andExpect(jsonPath("$.inventory[0]").value("monster1"));
    }

    @Test
    void testGetPlayerById_NotFound() throws Exception {
        when(playerService.findPlayerById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/player/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddExperience_LevelUp() throws Exception {
        when(playerService.addExperience(1, 50)).thenReturn(true);

        mockMvc.perform(post("/player/1/add-xp/50"))
                .andExpect(status().isOk())
                .andExpect(content().string("Level up!"));
    }

    @Test
    void testAddExperience_XPAdded() throws Exception {
        when(playerService.addExperience(1, 10)).thenReturn(false);

        mockMvc.perform(post("/player/1/add-xp/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("XP added"));
    }

    @Test
    void testAddMonster_Success() throws Exception {
        when(playerService.addMonster(1, "monster1")).thenReturn(true);

        mockMvc.perform(post("/player/1/add-monster/monster1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Monster added!"));
    }

    @Test
    void testAddMonster_Failure() throws Exception {
        when(playerService.addMonster(1, "monster1")).thenReturn(false);

        mockMvc.perform(post("/player/1/add-monster/monster1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Inventory full"));
    }

    @Test
    void testRemoveMonster_Success() throws Exception {
        when(playerService.removeMonster(1, "monster1")).thenReturn(true);

        mockMvc.perform(delete("/player/1/remove-monster/monster1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Monster removed!"));
    }

    @Test
    void testRemoveMonster_NotFound() throws Exception {
        when(playerService.removeMonster(1, "monster1")).thenReturn(false);

        mockMvc.perform(delete("/player/1/remove-monster/monster1"))
                .andExpect(status().isNotFound());
    }
}
