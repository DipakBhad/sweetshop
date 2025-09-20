package com.example.sweetshop.service;

import com.example.sweetshop.entity.Purchase;
import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.entity.User;
import com.example.sweetshop.repository.PurchaseRepository;
import com.example.sweetshop.repository.SweetRepository;
import com.example.sweetshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SweetRepository sweetRepository;
    private final UserRepository userRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, SweetRepository sweetRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.sweetRepository = sweetRepository;
        this.userRepository = userRepository;
    }

    public Purchase createPurchase(Long userId, Long sweetId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new NoSuchElementException("Sweet not found"));

        if (sweet.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient sweet stock");
        }

        sweet.setQuantity(sweet.getQuantity() - quantity);
        sweetRepository.save(sweet);

        Purchase purchase = new Purchase(user, sweet, quantity);
        return purchaseRepository.save(purchase);
    }
}

