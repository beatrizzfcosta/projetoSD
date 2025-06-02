package com.example.projetosd.controller;

import com.example.projetosd.logic.ProductFormDTO;
import com.example.projetosd.model.Product;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.projetosd.repository.ColorRepository;
import com.example.projetosd.repository.ProductRepository;
import com.example.projetosd.repository.TypeRepository;
import com.example.projetosd.repository.BrandRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public String getAll(Model model,
                        @RequestParam(required = false) String preco,
                        @RequestParam(required = false) String colorName,
                        @RequestParam(required = false) String typeName,
                        @RequestParam(required = false) String brandName) {
        
        Iterable<Product> products = prodRepository.findAll();
        List<Product> filteredProducts = new ArrayList<>();
        
        // Convert Iterable to List for easier manipulation
        for (Product product : products) {
            filteredProducts.add(product);
        }
        
        // Apply filters one by one
        if (colorName != null && !colorName.isEmpty()) {
            filteredProducts = filteredProducts.stream()
                .filter(p -> p.getColor().getColorName().equals(colorName))
                .collect(Collectors.toList());
        }
        
        if (typeName != null && !typeName.isEmpty()) {
            filteredProducts = filteredProducts.stream()
                .filter(p -> p.getType().getTypeName().equals(typeName))
                .collect(Collectors.toList());
        }
        
        if (brandName != null && !brandName.isEmpty()) {
            filteredProducts = filteredProducts.stream()
                .filter(p -> p.getBrand().getBrandName().equals(brandName))
                .collect(Collectors.toList());
        }
        
        if (preco != null && !preco.isEmpty()) {
            filteredProducts = filteredProducts.stream()
                .filter(p -> {
                    double price = p.getPrice();

                    switch (preco) {
                        case "0-50000":
                            return price <= 50000;
                        case "50000-100000":
                            return price > 50000 && price <= 100000;
                        case "100000+":
                            return price > 100000;
                        default:
                            return true;
                    }

                })
                .collect(Collectors.toList());
        }
        
        model.addAttribute("ListProducts", filteredProducts);
        model.addAttribute("ListColors", colorRepository.findAll());
        model.addAttribute("ListType", typeRepository.findAll());
        model.addAttribute("ListBrands", brandRepository.findAll());
        model.addAttribute("productForm", new ProductFormDTO());
        
        // Adicionar os valores dos filtros ao modelo para manter o estado
        model.addAttribute("selectedColorName", colorName);
        model.addAttribute("selectedTypeName", typeName);
        model.addAttribute("selectedBrandName", brandName);
        model.addAttribute("selectedPreco", preco);
        
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
    @PostMapping("/product/delete")
    @Transactional
    public String deleteProduct(@RequestParam Integer productId, RedirectAttributes redirectAttributes) {

        prodRepository.deleteById(productId);

        redirectAttributes.addFlashAttribute("message", "Product added successfully!");

        return "redirect:/admin";
    }
}
