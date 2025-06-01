package com.example.projetosd.controller;

import com.example.projetosd.logic.Carrinho;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/carrinho")
public class CarrinhoController {

    private ArrayList<Carrinho> carrinhos = new ArrayList<>();

    @GetMapping("/{userId}")
    public String getCarrinho(@PathVariable long userId, Model model) {
        for (Carrinho c : carrinhos) {
            if (c.getUserId() == userId) {
                model.addAttribute("products", c.getProducts());
                return "carrinho";
            }
        }

        model.addAttribute("products", new ArrayList<>());
        model.addAttribute("error", "Carrinho não encontrado.");
        return "carrinho";
    }

    @GetMapping("/{userId}/comprar")
    public String comprar(@PathVariable long userId, Model model) {
        for (int i = 0; i < carrinhos.size(); i++) {
            Carrinho c = carrinhos.get(i);
            if (c.getUserId() == userId) {
                ArrayList<Long> products = c.getProducts();
                carrinhos.remove(i);

                model.addAttribute("message", "Compra efetuada com sucesso!");
                model.addAttribute("products", products);

                return "compra_sucesso"; // success page?
            }
        }

        model.addAttribute("error", "Carrinho não encontrado.");
        return "index";
    }
}
