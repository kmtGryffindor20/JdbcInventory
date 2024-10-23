package com.inventory.backend.controllers.impl;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.inventory.backend.entities.Product;
import com.inventory.backend.services.impl.ProductService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/{id}")
    public String showProductDetails(@PathVariable("id") Long id, Model model) {
        Optional<Product> productOptional = productService.findById(id);
        
        // If product is found, show details page
        if (productOptional.isPresent()) {
            model.addAttribute("product", productOptional.get());
            return "product-details"; // Thymeleaf template name (product-details.html)
        } else {
            // Redirect to notfound.html if product not found
            return "redirect:/notfound";
        }
    }
}
