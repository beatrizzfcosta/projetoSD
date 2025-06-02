package com.example.projetosd.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId", nullable = false)
    private Integer productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "imageURL", nullable = false)
    private String imageURL;

    @Column(name = "doorCount", nullable = false)
    private Integer doorCount;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "typeId")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "colorId")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PurchaseProduct> purchaseProducts = new LinkedHashSet<>();

    public Product() {}

    public Integer getProductId() {return productId;}

    public void setProductId(Integer prodId) {this.productId = prodId;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getImageURL() {return imageURL;}

    public void setImageURL(String imageURL) {this.imageURL = imageURL;}

    public Integer getDoorCount() {return doorCount;}

    public void setDoorCount(Integer doorCount) {this.doorCount = doorCount;}

    public Double getPrice() {return price;}

    public void setPrice(Double price) {this.price = price;}

    public Type getType() {return type;}

    public void setType(Type type) {this.type = type;}

    public Color getColor() {return color;}

    public void setColor(Color color) {this.color = color;}

    public Brand getBrand() {return brand;}

    public void setBrand(Brand brand) {this.brand = brand;}
}
