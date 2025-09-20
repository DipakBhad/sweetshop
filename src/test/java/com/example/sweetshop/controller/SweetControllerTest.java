package com.example.sweetshop.controller;

import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.repository.SweetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // disable Spring Security
public class SweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SweetRepository sweetRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        sweetRepository.deleteAll(); // clear table before each test
    }

    @Test
    void testAddSweet() throws Exception {
        Sweet sweet = new Sweet("Chocolate", "Candy", "Dark chocolate", 2.5, 10);

        mockMvc.perform(post("/api/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Chocolate"));
    }

    @Test
    void testGetAllSweets() throws Exception {
        sweetRepository.save(new Sweet("Candy", "Candy", "Sweet candy", 1.0, 5));

        mockMvc.perform(get("/api/sweets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testUpdateSweet() throws Exception {
        Sweet sweet = sweetRepository.save(new Sweet("Toffee", "Candy", "Caramel toffee", 1.5, 8));
        sweet.setPrice(2.0);

        mockMvc.perform(put("/api/sweets/" + sweet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(2.0));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteSweet() throws Exception {
        Sweet sweet = sweetRepository.save(new Sweet("Lollipop", "Candy", "Fruit lollipop", 0.5, 20));

        mockMvc.perform(delete("/api/sweets/" + sweet.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testPurchaseSweet() throws Exception {
        Sweet sweet = sweetRepository.save(new Sweet("Marshmallow", "Candy", "Soft marshmallow", 1.2, 15));

        mockMvc.perform(post("/api/sweets/" + sweet.getId() + "/purchase")
                        .param("quantity", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testRestockSweet() throws Exception {
        Sweet sweet = sweetRepository.save(new Sweet("Candy", "Candy", "Sweet candy", 1.0, 5));

        mockMvc.perform(post("/api/sweets/" + sweet.getId() + "/restock")
                        .param("quantity", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(15));
    }

    @Test
    void testSearchSweets() throws Exception {
        sweetRepository.save(new Sweet("Chocolate", "Candy", "Dark chocolate", 2.5, 10));

        mockMvc.perform(get("/api/sweets/search")
                        .param("name", "Chocolate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
