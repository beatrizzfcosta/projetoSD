package com.example.projetosd.model;


import jakarta.persistence.*;

import java.util.LinkedHashMap;

@Entity
@Table(name = "purchaseProducts",
        uniqueConstraints = @UniqueConstraint(columnNames = {"purchaseId", "productId"}))
public class PurchaseProduct {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseId", nullable = false)
    private Purchase purchase;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productId", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public Integer getQuantity() {return quantity;}

    public void setQuantity(Integer quantity) {this.quantity = quantity;}

    public Product getProduct() {return product;}

    public void setProduct(Product product) {this.product = product;}

    public Purchase getPurchase() {return purchase;}

    public void setPurchase(Purchase purchase) {this.purchase = purchase;}

}
