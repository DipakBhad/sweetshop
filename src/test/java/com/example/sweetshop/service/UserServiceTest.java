package com.example.sweetshop.service;

import com.example.sweetshop.entity.User;
import com.example.sweetshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("Dipak", "dipak@example.com", "password", false);
        user.setId(1L);
    }

    @Test
    void testRegisterUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User saved = userService.registerUser(user);
        assertThat(saved.getEmail()).isEqualTo("dipak@example.com");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindByEmail() {
        when(userRepository.findByEmail("dipak@example.com")).thenReturn(Optional.of(user));

        User found = userService.findByEmail("dipak@example.com");
        assertThat(found.getName()).isEqualTo("Dipak");
        verify(userRepository, times(1)).findByEmail("dipak@example.com");
    }
}

