package com.example.projetosd.controller;

import com.example.projetosd.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/loja")
public class LojaController {

    @Autowired
    private ProductRepository prodRepository;

    @GetMapping
    public String getLoja(Model model) {
        model.addAttribute("Products", prodRepository.findAll());
        return "loja";
    }
}
