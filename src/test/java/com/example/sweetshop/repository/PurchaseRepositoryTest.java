package com.example.sweetshop.repository;

import com.example.sweetshop.entity.Purchase;
import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PurchaseRepositoryTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SweetRepository sweetRepository;

    @Test
    void testSavePurchase() {
        User user = userRepository.save(new User("Dipak", "dipak@example.com", "pass", false));
        Sweet sweet = sweetRepository.save(new Sweet("Ladoo", "Indian", "Delicious", 50.0, 100));

        Purchase purchase = new Purchase(user, sweet, 5);
        Purchase savedPurchase = purchaseRepository.save(purchase);

        assertThat(savedPurchase).isNotNull();
        assertThat(savedPurchase.getId()).isNotNull();
        assertThat(savedPurchase.getQuantity()).isEqualTo(5);
    }
}
