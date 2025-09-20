package com.example.sweetshop.repository;

import com.example.sweetshop.entity.Sweet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class SweetRepositoryTest {

    @Autowired
    private SweetRepository sweetRepository;

    @Test
    void testSaveSweet() {
        Sweet sweet = new Sweet("Ladoo", "Indian", "Delicious ladoo", 50.0, 100);
        Sweet savedSweet = sweetRepository.save(sweet);

        assertThat(savedSweet).isNotNull();
        assertThat(savedSweet.getId()).isNotNull();
        assertThat(savedSweet.getName()).isEqualTo("Ladoo");
    }

    @Test
    void testFindSweetById() {
        Sweet sweet = new Sweet("Barfi", "Indian", "Milk based sweet", 80.0, 50);
        Sweet savedSweet = sweetRepository.save(sweet);

        Optional<Sweet> foundSweet = sweetRepository.findById(savedSweet.getId());
        assertThat(foundSweet).isPresent();
        assertThat(foundSweet.get().getName()).isEqualTo("Barfi");
    }

    @Test
    void testFindAllSweets() {
        Sweet sweet1 = new Sweet("Jalebi", "Indian", "Crispy sweet", 40.0, 200);
        Sweet sweet2 = new Sweet("Rasgulla", "Bengali", "Syrup sweet", 60.0, 150);

        sweetRepository.save(sweet1);
        sweetRepository.save(sweet2);

        List<Sweet> sweets = sweetRepository.findAll();
        assertThat(sweets.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testDeleteSweet() {
        Sweet sweet = new Sweet("Kaju Katli", "Indian", "Cashew sweet", 120.0, 80);
        Sweet savedSweet = sweetRepository.save(sweet);

        sweetRepository.delete(savedSweet);

        Optional<Sweet> deletedSweet = sweetRepository.findById(savedSweet.getId());
        assertThat(deletedSweet).isEmpty();
    }
}
