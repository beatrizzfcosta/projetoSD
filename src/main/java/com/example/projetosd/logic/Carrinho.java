package com.example.projetosd.logic;

import java.util.ArrayList;

public class Carrinho {

    private long userId;
    private ArrayList<Long> products;

    public Carrinho(long userid){
        this.userId = userid;
        products = new ArrayList<>();
    }

    public synchronized void addProduct(long prodId){
        products.add(prodId);
    }

    public synchronized ArrayList<Long> getProducts(){
        return new ArrayList<>(products);
    }

    public long getUserId(){
        return userId;
    }
}
