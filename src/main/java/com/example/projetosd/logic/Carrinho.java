package com.example.projetosd.logic;

import java.util.ArrayList;

public class Carrinho {

    private Integer userId;
    private ArrayList<Integer> products;

    public Carrinho(Integer userid){
        this.userId = userid;
        products = new ArrayList<>();
    }

    public synchronized void addProduct(Integer prodId){
        products.add(prodId);
    }

    public synchronized ArrayList<Integer> getProducts(){
        return new ArrayList<>(products);
    }

    public synchronized void removeProduct(Integer prodId) {
        products.remove(prodId);
    }

    public Integer getUserId(){
        return userId;
    }
}
