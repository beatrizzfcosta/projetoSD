package com.example.projetosd.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.time.LocalDate;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchaseId", nullable = false)
    private Integer purchaseId;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PurchaseProduct> purchaseProducts = new LinkedHashSet<>();

    public Purchase() {}

    public LocalDateTime  getDate() {return date;}

    public void setDate(LocalDateTime  date) {this.date = date;}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public Integer getPurchaseId() {return purchaseId;}

    public void setPurchaseId(Integer purchaseId) {this.purchaseId = purchaseId;}

    public Set<PurchaseProduct> getPurchaseProducts() {return purchaseProducts;}

    public void setPurchaseProducts(Set<PurchaseProduct> purchaseProducts) {this.purchaseProducts = purchaseProducts;}

}
