package com.example.projetosd.controller;

import com.example.projetosd.logic.Carrinho;
import com.example.projetosd.logic.CarrinhoWrapper;
import com.example.projetosd.model.Product;
import com.example.projetosd.model.Purchase;
import com.example.projetosd.model.PurchaseProduct;
import com.example.projetosd.model.User;
import com.example.projetosd.repository.ProductRepository;
import com.example.projetosd.repository.PurchaseRepository;
import com.example.projetosd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping(value = "/carrinho")
public class CarrinhoController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

    @GetMapping
    public String getCarrinho(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByMail(email);
        Integer userId = user.getUserId().intValue();

        Carrinho carrinho = CarrinhoWrapper.getCarrinho(userId);

        Map<Integer, Integer> productQuantities = carrinho.getProducts();

        // Converte as keys do map (productIds) em lista
        List<Integer> productIds = new ArrayList<>(productQuantities.keySet());
        List<Product> products = new ArrayList<>();
        productRepository.findAllById(productIds).forEach(products::add);

        // Mapeia produto → quantidade
        Map<Product, Integer> productWithQty = new LinkedHashMap<>();
        Double subtotal = 0.0;

        for (Product product : products) {
            Integer qty = productQuantities.getOrDefault(product.getProductId(), 1);
            productWithQty.put(product, qty);
            subtotal = subtotal + (product.getPrice() * qty);
        }

        model.addAttribute("productWithQty", productWithQty);
        model.addAttribute("subtotal", subtotal);

        return "carrinho";
    }


    @PostMapping("/comprar")
    public String comprar(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByMail(email);
        Integer userId = user.getUserId().intValue();

        Carrinho carrinho = CarrinhoWrapper.getCarrinho(userId);

        // Criar a nova Purchase
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setDate(LocalDateTime.now());

        Set<PurchaseProduct> purchaseProducts = new LinkedHashSet<>();

        for (Map.Entry<Integer, Integer> entry : carrinho.getProducts().entrySet()) {
            Integer productId = entry.getKey();
            Integer quantity = entry.getValue();

            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isEmpty()) continue;

            Product product = optionalProduct.get();

            PurchaseProduct pp = new PurchaseProduct();
            pp.setProduct(product);
            pp.setPurchase(purchase);
            pp.setQuantity(quantity);

            purchaseProducts.add(pp);
        }

        purchase.setPurchaseProducts(purchaseProducts);

        // Guardar no BD (assumindo cascade nas relações)
        purchaseRepository.save(purchase);
        Integer purchaseId = purchase.getPurchaseId();
        // Limpar carrinho após compra
        carrinho.clear();

        return "redirect:/invoices/"+purchaseId; // Página de confirmação de compra
    }


    @PostMapping("/remove")
    public String removeProduct(@RequestParam("productId") Long productId , Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByMail(email);
        Integer userId = user.getUserId().intValue();

        Carrinho carrinho = CarrinhoWrapper.getCarrinho(userId);
        carrinho.removeProduct(Math.toIntExact(productId));

        return "redirect:/carrinho";
    }
    @PostMapping("/increment")
    public String incrementProduct(@RequestParam("productId") Long productId, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByMail(email);
        Integer userId = user.getUserId().intValue();

        Carrinho carrinho = CarrinhoWrapper.getCarrinho(userId);
        carrinho.incrementProduct(Math.toIntExact(productId));

        System.out.println("Incremented prodfffffffffffffffffffffffffffffffffffffffffff:"+ carrinho.getProducts() + " " + productId);

        return "redirect:/carrinho";
    }


    @PostMapping("/decrement")
    public String decrementProduct(@RequestParam("productId") Long productId , Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByMail(email);
        Integer userId = user.getUserId().intValue();

        Carrinho carrinho = CarrinhoWrapper.getCarrinho(userId);
        carrinho.decrementProduct(Math.toIntExact(productId));

        return "redirect:/carrinho";
    }

    private Map<Integer, Integer> aggregateProducts(List<Integer> productIds) {
        Map<Integer, Integer> quantityMap = new HashMap<>();

        for (Integer prodId : productIds) {
            quantityMap.put(prodId, quantityMap.getOrDefault(prodId, 0) + 1);
        }

        return quantityMap;
    }
}
