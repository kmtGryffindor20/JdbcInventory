package com.inventory.backend.controllers.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inventory.backend.entities.Product;
import com.inventory.backend.services.impl.ProductService;

@Controller
public class CategoryProductsController {
    
    private ProductService productService;

    public CategoryProductsController(ProductService productService) {
        this.productService = productService;
    }

    Map<String, String> categoryCatchphrase = new HashMap<String, String>() {{
        put("Electronics", "Innovating the Future, One Device at a Time.");
        put("Clothing", "Dress Your Best for Every Occasion.");
        put("Groceries", "Freshness Delivered, Daily.");
        put("Furniture", "Comfort and Style, Delivered.");
        put("Books", "For the Love of Learning and Leisure.");
        put("Toys", "Playtime, All the Time.");
        put("Sports Equipment", "For the Athlete in You.");
        put("Beauty Products", "Where Self-Care Meets Elegance.");
        put("Office Supplies", "Supplies for the Modern Workspace.");
        put("Automotive", "For the Road Ahead.");
        
    }};

    @GetMapping("/category/{categoryId}/products")
    public String getProductsByCategory(@PathVariable Long categoryId, Model model) {

        List<Product> products = productService.getByCategory(categoryId);

        if(products.isEmpty()) {
            return "notfound";
        }

        model.addAttribute("products", products);
        model.addAttribute("title", products.get(0).getCategory().getCategoryName());

        model.addAttribute("cathphrase", categoryCatchphrase.get(products.get(0).getCategory().getCategoryName()));

        return "category-products";
    }


}
