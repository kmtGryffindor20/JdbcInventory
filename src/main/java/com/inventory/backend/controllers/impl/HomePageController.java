package com.inventory.backend.controllers.impl;

import java.util.List;

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

    @GetMapping("/")
    public String index(Model model) {

        List<Category> allCategories = categoryService.findAll();
        model.addAttribute("categories", allCategories);
        List<Product> dealsOfTheDay = productService.findDealsOfTheDay();
        model.addAttribute("deals", dealsOfTheDay);
        return "index";
    }
}
