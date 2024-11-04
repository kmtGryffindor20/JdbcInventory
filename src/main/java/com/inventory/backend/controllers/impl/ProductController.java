package com.inventory.backend.controllers.impl;
import java.util.Optional;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.inventory.backend.entities.Product;
import com.inventory.backend.services.impl.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public String showProductDetails(@PathVariable("id") Long id, Model model) {
        Optional<Product> productOptional = productService.findById(id);
        
        // If product is not found, return to a custom "product not found" page or throw an exception
        if (productOptional.isPresent()) {
            model.addAttribute("product", productOptional.get());
            return "product-details"; // Render product details page
        } else {
            return "notfound"; // You can handle this as you like
        }
    }
}
