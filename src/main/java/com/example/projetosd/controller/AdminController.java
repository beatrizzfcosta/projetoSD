package com.example.projetosd.controller;

import com.example.projetosd.logic.ProductFormDTO;
import com.example.projetosd.model.Product;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.projetosd.repository.ColorRepository;
import com.example.projetosd.repository.ProductRepository;
import com.example.projetosd.repository.TypeRepository;
import com.example.projetosd.repository.BrandRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getAll (Model model) {
        model.addAttribute("ListProds" , prodRepository.findAll());
        model.addAttribute("ListColors", colorRepository.findAll());
        model.addAttribute("ListType" , typeRepository.findAll());
        model.addAttribute("ListBrands", brandRepository.findAll());
        model.addAttribute("productForm", new ProductFormDTO());
        return "admin";
    }

    @PostMapping("/product/add")
    @Transactional
    public String addProduct(@ModelAttribute ProductFormDTO productForm, RedirectAttributes redirectAttributes) {
        Product product = new Product();

        product.setName(productForm.getName());
        product.setDescription(productForm.getDescription());
        product.setImageURL(productForm.getImageURL());
        product.setDoorCount(productForm.getDoorCount());
        product.setPrice(productForm.getPrice());

        typeRepository.findById(productForm.getTypeId()).ifPresent(product::setType);
        colorRepository.findById(productForm.getColorId()).ifPresent(product::setColor);
        brandRepository.findById(productForm.getBrandId()).ifPresent(product::setBrand);

        prodRepository.save(product);

        redirectAttributes.addFlashAttribute("message", "Product added successfully!");

        return "redirect:/admin";
    }
}
