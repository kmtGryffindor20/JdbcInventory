package com.inventory.backend.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.inventory.backend.services.impl.CategoryServiceImpl;
import com.inventory.backend.entities.Category;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping
    public String viewCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories";  // Render categories.html
    }

    @PostMapping("/add")
    public String addCategory(@RequestParam String categoryName) {
        Category category = Category.builder().categoryName(categoryName).build();
        categoryService.save(category);
        return "redirect:/admin/categories"; // Corrected redirect path
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/admin/categories"; // Corrected redirect path
    }

}
