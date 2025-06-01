package com.example.projetosd.controller;


import com.example.projetosd.repository.ProductRepository;
import com.example.projetosd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.projetosd.model.User;

import java.security.Principal;

@Controller
@RequestMapping(value = "/loja")
public class LojaController {
    @Autowired
    private ProductRepository prodRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getLoja(Model model, Principal principal) {
        model.addAttribute("products", prodRepository.findAll());

        String email = principal.getName();
        User user = userRepository.findByMail(email);
        Long userId = user.getUserId();

        System.out.println("UserId: " + userId);

        model.addAttribute("userId", userId);
        return "loja";
    }
}
