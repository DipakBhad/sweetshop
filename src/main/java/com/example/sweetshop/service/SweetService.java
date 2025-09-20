package com.example.sweetshop.service;

import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.repository.SweetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SweetService {

    private final SweetRepository sweetRepository;

    public SweetService(SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    public Sweet addSweet(Sweet sweet) {
        return sweetRepository.save(sweet);
    }

    public Sweet getSweetById(Long id) {
        return sweetRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Sweet not found"));
    }

    public Sweet purchaseSweet(Long id, int quantity) {
        Sweet sweet = getSweetById(id);
        if (sweet.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient quantity in stock");
        }
        sweet.setQuantity(sweet.getQuantity() - quantity);
        return sweetRepository.save(sweet);
    }

    public Sweet restockSweet(Long id, int quantity) {
        Sweet sweet = getSweetById(id);
        sweet.setQuantity(sweet.getQuantity() + quantity);
        return sweetRepository.save(sweet);
    }

    public List<Sweet> getAllSweets() {
        return sweetRepository.findAll();
    }

    // ✅ Add delete method
    public void deleteSweet(Long id) {
        Sweet sweet = getSweetById(id); // ensures sweet exists
        sweetRepository.delete(sweet);
    }
    public List<Sweet> searchSweets(String name, String category, Double minPrice, Double maxPrice) {
        return sweetRepository.findAll().stream()
                .filter(s -> (name == null || s.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(s -> (category == null || s.getCategory().equalsIgnoreCase(category)))
                .filter(s -> (minPrice == null || s.getPrice() >= minPrice))
                .filter(s -> (maxPrice == null || s.getPrice() <= maxPrice))
                .toList();
    }

}
