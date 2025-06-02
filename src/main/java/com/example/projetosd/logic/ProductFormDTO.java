package com.example.projetosd.logic;

import java.math.BigDecimal;

public class ProductFormDTO {
    private String name;
    private String description;
    private String imageURL;
    private int doorCount;
    private Double price;
    private Integer typeId;
    private Integer colorId;
    private Integer brandId;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public Integer getColorId() {
        return colorId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public int getDoorCount() {
        return doorCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setDoorCount(int doorCount) {
        this.doorCount = doorCount;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}
