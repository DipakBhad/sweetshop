package com.example.sweetshop.controller;

import com.example.sweetshop.security.JwtUtil;
import com.example.sweetshop.service.UserService;
import com.example.sweetshop.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class) // <- specify the controller explicitly
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterUser() throws Exception {
        User user = new User("John", "john@example.com", "pass", false);
        when(userService.registerUser(user)).thenReturn(user);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginUser_Success() throws Exception {
        User user = new User("John", "john@example.com", "pass", false);
        when(userService.findByEmail("john@example.com")).thenReturn(user);
        when(jwtUtil.generateToken(user)).thenReturn("token");

        String loginJson = """
                {"email":"john@example.com","password":"pass"}
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginUser_Failure() throws Exception {
        when(userService.findByEmail("invalid@example.com"))
                .thenThrow(new RuntimeException("User not found"));

        String loginJson = """
                {"email":"invalid@example.com","password":"pass"}
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isUnauthorized());
    }
}
