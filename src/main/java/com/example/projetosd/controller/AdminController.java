package com.example.projetosd.controller;

import com.example.projetosd.logic.ProductFormDTO;
import com.example.projetosd.model.Product;

import com.example.projetosd.model.Purchase;
import com.example.projetosd.model.PurchaseProduct;
import com.example.projetosd.repository.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
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

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

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

        Iterable<Purchase> purchases = purchaseRepository.findAll();

        long stockCount = ((List<Product>) products).size();

        
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
        model.addAttribute("ListPurchases", purchases);
        model.addAttribute("stockCount", stockCount);

        double totalSales = 0.0;
        for (Purchase purchase : purchases) {
            for (PurchaseProduct pp : purchase.getPurchaseProducts()) {
                totalSales += pp.getProduct().getPrice() * pp.getQuantity();
            }
        }

        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalIVA", totalSales * 0.23);
        model.addAttribute("totalcIVA", totalSales * 1.23);

        Map<Month, Double> salesByMonth = new TreeMap<>();

        for (Purchase purchase : purchases) {
            Month month = purchase.getDate().getMonth(); //

            double totalPurchase = 0.0;
            for (PurchaseProduct pp : purchase.getPurchaseProducts()) {
                totalPurchase += pp.getProduct().getPrice() * pp.getQuantity() * 1.23;
            }

            salesByMonth.merge(month, totalPurchase, Double::sum);
        }


        model.addAttribute("salesByMonth", salesByMonth);

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

    @PostMapping("/product/edit")
    @Transactional
    public String editProduct(@RequestParam Integer productId, @ModelAttribute ProductFormDTO productForm, RedirectAttributes redirectAttributes) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not Found"));

        System.out.println(">>> EDITANDO PRODUTO ID: " + productId);
        System.out.println(">>> Nome: " + productForm.getName());
        System.out.println(">>> Preço: " + productForm.getPrice());

        product.setName(productForm.getName());
        product.setDescription(productForm.getDescription());
        product.setImageURL(productForm.getImageURL());
        product.setDoorCount(productForm.getDoorCount());
        product.setPrice(productForm.getPrice());

        typeRepository.findById(productForm.getTypeId()).ifPresent(product::setType);
        colorRepository.findById(productForm.getColorId()).ifPresent(product::setColor);
        brandRepository.findById(productForm.getBrandId()).ifPresent(product::setBrand);

        productRepository.save(product);

        redirectAttributes.addFlashAttribute("message", "Product edited successfully!");

        return "redirect:/admin";

    }


}
