package com.example.projetosd.controller;

import com.example.projetosd.model.User;
import com.example.projetosd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showProfile(Model model) {
        // TODO: Get the current logged-in user
        // For now, we'll use a mock user for testing
        // User user = userRepository.findById(1).orElse(null);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        
        User user = userRepository.findByMail(email);
        if (user != null) {
            model.addAttribute("user", user);
            return "perfil";
        }
        return "redirect:/login";
    }

    @PostMapping("/atualizar")
    @ResponseBody
    // public ResponseEntity<?> updateProfile(@ModelAttribute User updatedUser, HttpSession session) {
    public ResponseEntity<?> updateProfile(@ModelAttribute User updatedUser) {
        try {
            //User currentUser = (User) session.getAttribute("user");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            
            User currentUser = userRepository.findByMail(email);
            if (currentUser == null) {
                return ResponseEntity.badRequest().body("Usuário não autenticado");
            }

            // Update user information
            currentUser.setNome(updatedUser.getNome());
            currentUser.setMail(updatedUser.getMail());
            currentUser.setNif(updatedUser.getNif());
            currentUser.setMorada(updatedUser.getMorada());
            currentUser.setCodigoPostal(updatedUser.getCodigoPostal());
            currentUser.setPais(updatedUser.getPais());
            currentUser.setTelefone(updatedUser.getTelefone());

            // Update password if provided
            if (updatedUser.getSenha() != null && !updatedUser.getSenha().isEmpty()) {
                if (!updatedUser.getSenha().equals(updatedUser.getConfirmarSenha())) {
                    Map<String, String> response = new HashMap<>();
                    response.put("error", "As senhas não coincidem.");
                    return ResponseEntity.badRequest().body(response);
                }
                currentUser.setSenha(updatedUser.getSenha());
            }

            // Save the updated user
            userRepository.save(currentUser);
            
            // Update session
            //session.setAttribute("user", currentUser);

            Map<String, String> response = new HashMap<>();
            response.put("success", "Perfil atualizado com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Erro ao atualizar perfil. Por favor, tente novamente.");
            return ResponseEntity.badRequest().body(response);
        }
    }
} 