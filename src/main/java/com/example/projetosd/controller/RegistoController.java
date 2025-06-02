package com.example.projetosd.controller;

import com.example.projetosd.model.User;
import com.example.projetosd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registo")
public class RegistoController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registo";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            // Verificar se o email já está em uso
            if (userRepository.findByMail(user.getMail()) != null) {
                model.addAttribute("erro", "Este email já está registrado.");
                return "registo";
            }

            // Verificar se o NIF já está em uso (se fornecido)
            if (user.getNif() != null && !user.getNif().isEmpty() && 
                userRepository.findByNif(user.getNif()) != null) {
                model.addAttribute("erro", "Este NIF já está registrado.");
                return "registo";
            }

            // Verificar se as senhas coincidem
            if (!user.getPassword().equals(user.getConfirmarPassword())) {
                model.addAttribute("erro", "As senhas não coincidem.");
                return "registo";
            }

            // Verificar se a senha tem pelo menos 6 caracteres
            if (user.getPassword().length() < 6) {
                model.addAttribute("erro", "A senha deve ter pelo menos 6 caracteres.");
                return "registo";
            }


            // Salvar o usuário
            userRepository.save(user);
            
            model.addAttribute("sucesso", "Conta criada com sucesso!");
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao criar conta. Por favor, tente novamente.");
            return "registo";
        }
    }
}
