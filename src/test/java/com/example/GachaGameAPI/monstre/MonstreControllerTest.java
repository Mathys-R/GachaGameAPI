package com.example.GachaGameAPI.monstre;

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
public class MonstreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllMonstres() throws Exception {
        mockMvc.perform(get("/monstres")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
