package com.inventory.backend.controllers.impl;

import java.sql.Date;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.inventory.backend.entities.Category;
import com.inventory.backend.services.impl.CategoryServiceImpl;
import com.inventory.backend.services.impl.CustomerOrderService;
import com.inventory.backend.services.impl.EmployeeService;
import com.inventory.backend.services.impl.ManufacturerService;
import com.inventory.backend.services.impl.ProductService;
import com.inventory.backend.services.impl.SalesReportService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
public class AdminController {
    
    private ManufacturerService manufacturerService;

    private ProductService productService;

    private EmployeeService employeeService;

    private CategoryServiceImpl categoryService;

    private CustomerOrderService customerOrderService;

    private SalesReportService salesReportService;

    public AdminController(ManufacturerService manufacturerService,
                                 ProductService productService,
                                  EmployeeService employeeService,
                                   CategoryServiceImpl categoryService,
                                    CustomerOrderService customerOrderService,
                                     SalesReportService salesReportService) {
        this.manufacturerService = manufacturerService;
        this.productService = productService;
        this.employeeService = employeeService;
        this.categoryService = categoryService;
        this.customerOrderService = customerOrderService;
        this.salesReportService = salesReportService;
    }

    @GetMapping("/admin/categories")
    public String showCategories(Model model) {

        model.addAttribute("categories", categoryService.findAll());
        return "admin/categories";
    }

    @GetMapping("admin/dashboard")
    public String showDashboard(Model model) {
        Map<String, Double> totalSalesByCategory = customerOrderService.totalSalesByCategory();
        model.addAttribute("totalSalesByCategory", totalSalesByCategory);
        return "admin/dashboard";
    }

    @GetMapping("admin/categories/create")
    public String createCategory(Model model) {
        model.addAttribute("category", new Category());
        return "admin/create/categoryCreateForm.html";
    }

    @RequestMapping(path = "admin/categories/create", method = RequestMethod.POST)
    public String createCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/admin/categories";
    }
    
    @GetMapping("admin/sales/weekly")
    public ResponseEntity<Map<Date, Double>> weeklySales(@RequestParam String date) {
        System.out.println(date);
        Date startDate = Date.valueOf(date);
        Date endDate = Date.valueOf(startDate.toLocalDate().plusDays(7));
        return ResponseEntity.ok(salesReportService.weeklySales(startDate, endDate));
    }


}
