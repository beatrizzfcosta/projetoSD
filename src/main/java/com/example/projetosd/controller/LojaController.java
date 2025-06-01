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
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


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
    public String getLoja(
        @RequestParam(required = false) String marca,
        @RequestParam(required = false) String tipo,
        @RequestParam(required = false) String preco,
        Model model) {
        
        List<Product> products;
        
        if (marca != null && !marca.isEmpty()) {
            products = prodRepository.findByBrand_BrandName(marca);
        } else if (tipo != null && !tipo.isEmpty()) {
            products = prodRepository.findByType_TypeName(tipo);
        } else {
            products = StreamSupport.stream(prodRepository.findAll().spliterator(), false)
                                   .collect(Collectors.toList());
        }
        if (preco != null && !preco.isEmpty()) {
            products = products.stream()
                .filter(product -> {
                    BigDecimal price = product.getPrice();
                    switch (preco) {
                        case "0-50000":
                            return price.compareTo(new BigDecimal("50000")) <= 0;
                        case "50000-100000":
                            return price.compareTo(new BigDecimal("50000")) > 0 && 
                                   price.compareTo(new BigDecimal("100000")) <= 0;
                        case "100000-200000":
                            return price.compareTo(new BigDecimal("100000")) > 0 && 
                                   price.compareTo(new BigDecimal("200000")) <= 0;
                        case "200000+":
                            return price.compareTo(new BigDecimal("200000")) > 0;
                        default:
                            return true;
                    }
                })
                .collect(Collectors.toList());
        }
       
        model.addAttribute("products", products);
        model.addAttribute("ListBrands", brandRepository.findAll());
        model.addAttribute("ListTypes", typeRepository.findAll());
        return "loja";
    }
}
