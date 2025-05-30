package com.example.projetosd.controller;

import com.example.projetosd.model.User;
import com.example.projetosd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, 
                             @RequestParam String password,
                             Model model) {
        User user = userRepository.findByMail(email);
        
        if (user != null && user.getSenha().equals(password)) {
            // Login successful
            return "redirect:/";
        } else {
            // Login failed
            model.addAttribute("erro", "Email ou senha inválidos");
            return "login";
        }
    }
}