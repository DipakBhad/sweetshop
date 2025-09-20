package com.example.sweetshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Sweet sweet;

    private Integer quantity;

    // No-arg constructor
    public Purchase() {}

    // Parameterized constructor
    public Purchase(User user, Sweet sweet, Integer quantity) {
        this.user = user;
        this.sweet = sweet;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Sweet getSweet() { return sweet; }
    public void setSweet(Sweet sweet) { this.sweet = sweet; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
