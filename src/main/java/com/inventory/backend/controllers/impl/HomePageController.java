package com.inventory.backend.controllers.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.inventory.backend.entities.Category;
import com.inventory.backend.entities.Product;
import com.inventory.backend.services.impl.CategoryServiceImpl;
import com.inventory.backend.services.impl.ProductService;

@Controller
public class HomePageController {
    

    private CategoryServiceImpl categoryService;

    private ProductService productService;

    public HomePageController(CategoryServiceImpl categoryService, ProductService productService) {
        this.categoryService = categoryService;
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

    @GetMapping("/")
    public String index(Model model) {

        List<Category> allCategories = categoryService.findAll();
        model.addAttribute("categories", allCategories);
        List<Product> dealsOfTheDay = productService.findDealsOfTheDay();
        model.addAttribute("deals", dealsOfTheDay);

        model.addAttribute("categoryCatchphrase", categoryCatchphrase);

        System.out.println(categoryCatchphrase);

        return "index";
    }
}
