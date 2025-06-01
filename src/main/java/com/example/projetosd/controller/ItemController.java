package com.example.projetosd.controller;

import com.example.projetosd.model.Product;
import com.example.projetosd.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/item")
public class ItemController {

    @Autowired
    private ProductRepository prodRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{id}")
    public String getItem(@PathVariable Long id, Model model) {
        Product produto = productRepository.getByProductId(id);
        model.addAttribute("product", produto);
        return "item";
    }

    @PostMapping("/carrinho")
    public String Btn_addProduct(Model model, Product produto) {
        return "redirect:/loja";
    }
}