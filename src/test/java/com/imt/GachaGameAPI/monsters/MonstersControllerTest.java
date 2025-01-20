package com.imt.GachaGameAPI.monsters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MonstersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllMonsters() throws Exception {
        mockMvc.perform(get("/monsters")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
