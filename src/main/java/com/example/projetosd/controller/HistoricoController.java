package com.example.projetosd.controller;

import com.example.projetosd.model.Purchase;
import com.example.projetosd.model.User;
import com.example.projetosd.repository.PurchaseRepository;
import com.example.projetosd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/historico")
public class HistoricoController {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getHistorico(Model model, Principal principal) {

        String email = principal.getName();
        User user = userRepository.findByMail(email);
        Integer userId = user.getUserId().intValue();

        List<Purchase> purchases = purchaseRepository.findByUserUserId(userId);

        model.addAttribute("purchases", purchases);

        return "historico";
    }


}
