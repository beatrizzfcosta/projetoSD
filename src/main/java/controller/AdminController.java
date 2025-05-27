package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.ColorRepository;
import repository.ProductRepository;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private ProductRepository prodRepository;

    @Autowired
    private ColorRepository colorRepository;

    public String getAllDeps (Model model) {
        model.addAttribute("ListProds" , prodRepository.findAll());
        return "admin";
    }

    public String getAllColors(Model model) {
        model.addAttribute("ListColors", colorRepository.findAll());
        return "admin";
    }
}
