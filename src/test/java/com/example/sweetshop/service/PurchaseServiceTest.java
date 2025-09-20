package com.example.sweetshop.service;

import com.example.sweetshop.entity.Purchase;
import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.entity.User;
import com.example.sweetshop.repository.PurchaseRepository;
import com.example.sweetshop.repository.SweetRepository;
import com.example.sweetshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private SweetRepository sweetRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    private User user;
    private Sweet sweet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("Dipak", "dipak@example.com", "pass", false);
        user.setId(1L);

        sweet = new Sweet("Ladoo", "Indian", "Delicious", 50.0, 100);
        sweet.setId(1L);
    }

    @Test
    void testCreatePurchase() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(sweetRepository.findById(1L)).thenReturn(java.util.Optional.of(sweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(sweet);
        when(purchaseRepository.save(any(Purchase.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Purchase saved = purchaseService.createPurchase(1L, 1L, 10);

        assertThat(saved.getQuantity()).isEqualTo(10);
        assertThat(sweet.getQuantity()).isEqualTo(90); // Sweet stock reduced

        verify(purchaseRepository, times(1)).save(any(Purchase.class));
    }

}
