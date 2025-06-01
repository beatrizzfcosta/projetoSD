package com.example.projetosd.controller;

import com.example.projetosd.logic.Carrinho;
import com.example.projetosd.logic.CarrinhoWrapper;
import com.example.projetosd.model.Product;
import com.example.projetosd.model.User;
import com.example.projetosd.repository.ProductRepository;
import com.example.projetosd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/carrinho")
public class CarrinhoController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getCarrinho(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByMail(email);
        Integer userId = user.getUserId().intValue();

        Carrinho carrinho = CarrinhoWrapper.getCarrinho(userId);

        List<Integer> productIds = carrinho.getProducts();

        List<Product> products = new ArrayList<>();
        productRepository.findAllById(productIds).forEach(products::add);

        model.addAttribute("products", products);
        BigDecimal subtotal = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("subtotal", subtotal);

        return "carrinho";
    }

    @GetMapping("/{userId}/comprar")
    public String comprar(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByMail(email);
        Integer userId = user.getUserId().intValue();

        CarrinhoWrapper.removeCarrinho(userId);

        model.addAttribute("error", "Carrinho não encontrado.");
        return "index";
    }

    @PostMapping("/increment")
    public String incrementProduct(@RequestParam("productId") Long productId , Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByMail(email);
        Integer userId = user.getUserId().intValue();

        Carrinho carrinho = CarrinhoWrapper.getCarrinho(userId);
        carrinho.addProduct(Math.toIntExact(productId));

        return "carrinho";
    }

    @PostMapping("/decrement")
    public String decrementProduct(@RequestParam("productId") Long productId , Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByMail(email);
        Integer userId = user.getUserId().intValue();

        Carrinho carrinho = CarrinhoWrapper.getCarrinho(userId);
        carrinho.removeProduct(Math.toIntExact(productId));

        return "carrinho";
    }

    private Map<Integer, Integer> aggregateProducts(List<Integer> productIds) {
        Map<Integer, Integer> quantityMap = new HashMap<>();

        for (Integer prodId : productIds) {
            quantityMap.put(prodId, quantityMap.getOrDefault(prodId, 0) + 1);
        }

        return quantityMap;
    }
}
