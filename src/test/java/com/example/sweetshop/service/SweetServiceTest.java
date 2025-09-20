package com.example.sweetshop.service;

import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.repository.SweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SweetServiceTest {

    @Autowired
    private SweetService sweetService;

    @Autowired
    private SweetRepository sweetRepository;

    @BeforeEach
    void setup() {
        sweetRepository.deleteAll();
    }

    @Test
    void testAddSweetAndGetAll() {
        Sweet sweet = new Sweet("Chocolate", "Candy", "Dark chocolate", 2.5, 10);
        sweetService.addSweet(sweet);

        List<Sweet> sweets = sweetService.getAllSweets();
        assertThat(sweets).hasSize(1);
        assertThat(sweets.get(0).getName()).isEqualTo("Chocolate");
    }

    @Test
    void testPurchaseSweet() {
        Sweet sweet = new Sweet("Toffee", "Candy", "Caramel toffee", 1.5, 10);
        Sweet saved = sweetService.addSweet(sweet);

        Sweet purchased = sweetService.purchaseSweet(saved.getId(), 5);
        assertThat(purchased.getQuantity()).isEqualTo(5);
    }

    @Test
    void testRestockSweet() {
        Sweet sweet = new Sweet("Candy", "Candy", "Sweet candy", 1.0, 5);
        Sweet saved = sweetService.addSweet(sweet);

        Sweet restocked = sweetService.restockSweet(saved.getId(), 10);
        assertThat(restocked.getQuantity()).isEqualTo(15);
    }

    @Test
    void testDeleteSweet() {
        Sweet sweet = new Sweet("Lollipop", "Candy", "Fruit lollipop", 0.5, 20);
        Sweet saved = sweetService.addSweet(sweet);

        sweetService.deleteSweet(saved.getId());
        assertThat(sweetRepository.findById(saved.getId())).isEmpty();
    }
}
