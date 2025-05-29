package com.example.projetosd.model;

import jakarta.persistence.*;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchaseId", nullable = false)
    private Integer purchaseId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Purchase() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }
}