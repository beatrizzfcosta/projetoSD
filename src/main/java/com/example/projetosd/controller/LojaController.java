package com.example.projetosd.controller;

import com.example.projetosd.model.Brand;
import com.example.projetosd.model.Color;
import com.example.projetosd.model.Product;
import com.example.projetosd.repository.BrandRepository;
import com.example.projetosd.repository.ColorRepository;
import com.example.projetosd.repository.ProductRepository;
import com.example.projetosd.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;


@Controller
@RequestMapping(value = "/loja")
public class LojaController {
    @Autowired
    private ProductRepository prodRepository;

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TypeRepository typeRepository;

    @GetMapping
    public String getLoja(Model model) {
        model.addAttribute("products", prodRepository.findAll());
        return "loja";
    }
}
