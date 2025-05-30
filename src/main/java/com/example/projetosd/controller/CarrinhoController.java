package com.example.projetosd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping(value = "/carrinho")
public class CarrinhoController {

    @GetMapping
    public String getCarrinho() {
        return "carrinho";
    }
}
