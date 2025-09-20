package com.example.sweetshop.repository;

import com.example.sweetshop.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User user = new User("Dipak", "dipak@example.com", "password123", false);
        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("dipak@example.com");
    }

    @Test
    void testFindByEmail() {
        User user = new User("Ankitha", "ankitha@example.com", "pass123", true);
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("ankitha@example.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("Ankitha");
    }
}
