package com.inventory.backend.controllers.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.inventory.backend.services.impl.CategoryServiceImpl;
import com.inventory.backend.services.impl.EmployeeService;
import com.inventory.backend.services.impl.ManufacturerService;
import com.inventory.backend.services.impl.ProductService;

@Controller
public class AdminController {
    
    private ManufacturerService manufacturerService;

    private ProductService productService;

    private EmployeeService employeeService;

    private CategoryServiceImpl categoryService;

    public AdminController(ManufacturerService manufacturerService, ProductService productService, EmployeeService employeeService, CategoryServiceImpl categoryService) {
        this.manufacturerService = manufacturerService;
        this.productService = productService;
        this.employeeService = employeeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/categories")
    public String showCategories(Model model) {

        model.addAttribute("categories", categoryService.findAll());
        return "admin/categories";
    }

    @GetMapping("admin/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("totalProducts", productService.findAll().size());
        model.addAttribute("totalManufacturers", manufacturerService.findAll().size());
        // model.addAttribute("totalEmployees", employeeService.findAll().size());
        return "admin/dashboard";
    }

}
