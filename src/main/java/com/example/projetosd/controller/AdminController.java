package com.example.projetosd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.projetosd.repository.ColorRepository;
import com.example.projetosd.repository.ProductRepository;
import com.example.projetosd.repository.TypeRepository;
import com.example.projetosd.repository.BrandRepository;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private ProductRepository prodRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private BrandRepository brandRepository;


    @GetMapping
    public String getAllDeps (Model model) {
        model.addAttribute("ListProds" , prodRepository.findAll());
        model.addAttribute("ListColors", colorRepository.findAll());
        model.addAttribute("ListType" , typeRepository.findAll());
        model.addAttribute("ListBrands", brandRepository.findAll());
        return "admin";
    }
}
