package com.example.projetosd.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Carrinho {

    private Integer userId;
    private HashMap<Integer, Integer> products;

    public Carrinho(Integer userId) {
        this.userId = userId;
        this.products = new HashMap<>();
    }

    public synchronized void addProduct(Integer prodId) {
        products.put(prodId, products.getOrDefault(prodId, 0) + 1);
    }

    public synchronized void incrementProduct(Integer prodId) {
        if (products.containsKey(prodId)) {
            products.put(prodId, products.get(prodId) + 1);
        } else {
            products.put(prodId, 1);
        }
    }

    public synchronized void decrementProduct(Integer prodId) {
        if (products.containsKey(prodId)) {
            int currentQty = products.get(prodId);
            if (currentQty <= 1) {
                products.remove(prodId);
            } else {
                products.put(prodId, currentQty - 1);
            }
        }
    }

    public synchronized void removeProduct(Integer prodId) {
        products.remove(prodId);
    }
    public synchronized void clear() {
        products.clear();
    }

    public synchronized Map<Integer, Integer> getProducts() {
        return new HashMap<>(products);
    }

    public Integer getUserId() {
        return userId;
    }
}
