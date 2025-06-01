package com.example.projetosd.logic;

import java.util.ArrayList;

public class CarrinhoWrapper {

    private static ArrayList<Carrinho> carrinhos = new ArrayList<>();

    public static Carrinho getCarrinho(Integer userId) {
        for (Carrinho c : carrinhos) {
            if (c.getUserId() == userId) {
                return c;
            }
        }

        Carrinho newCarrinho = new Carrinho(userId);
        carrinhos.add(newCarrinho);
        return newCarrinho;
    }

    public static void removeCarrinho(Integer userId) {
        for (int i = 0; i < carrinhos.size(); i++) {
            if (carrinhos.get(i).getUserId() == userId) {
                carrinhos.remove(i);
                break;
            }
        }
    }
}
