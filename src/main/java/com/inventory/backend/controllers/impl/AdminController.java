package com.inventory.backend.controllers.impl;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.inventory.backend.entities.Category;
import com.inventory.backend.entities.Manufacturer;
import com.inventory.backend.entities.Product;
import com.inventory.backend.services.impl.CategoryServiceImpl;
import com.inventory.backend.services.impl.CustomerOrderService;
import com.inventory.backend.services.impl.EmployeeService;
import com.inventory.backend.services.impl.ManufacturerService;
import com.inventory.backend.services.impl.ProductService;
import com.inventory.backend.services.impl.SalesReportService;

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
        return "admin/create/categoryCreateForm";
    }

    @RequestMapping(path = "admin/categories/create", method = RequestMethod.POST)
    public String createCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("admin/categories/update")
    public String updateCategory(@RequestParam Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id).get());
        return "admin/update/categoryUpdateForm";
    }

    @PostMapping("admin/categories/update")
    public String updateCategory(@ModelAttribute("category") Category category, @RequestParam Long id) {
        categoryService.update(category, id);
        return "redirect:/admin/categories";
    }

    @GetMapping("admin/categories/delete")
    public String deleteCategory(@RequestParam Long id) {
        categoryService.delete(id);
        return "redirect:/admin/categories";
    }
    
    @GetMapping("admin/products")
    public String showProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/products";
    }

    @GetMapping("admin/products/create")
    public String createProduct(Model model) {
        model.addAttribute("product", new Product());
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        List<Manufacturer> manufacturers = manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);
        return "admin/create/productCreateForm";
    }

    @PostMapping("admin/products/create")
    public String createProduct(@ModelAttribute("product") Product product,
                                @RequestParam("manufacturerIds") List<Long> manufacturerIds,
                                @RequestParam("categoryId") Long categoryId) {
        product.setCategory(categoryService.findById(categoryId).get());
        Set<Manufacturer> manufacturers = new HashSet<>();
        for (Long manufacturerId : manufacturerIds) {
            manufacturers.add(manufacturerService.findById(manufacturerId).get());
        }
        product.setManufacturers(manufacturers);
        productService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("admin/products/update")
    public String updateProduct(@RequestParam Long id, Model model) {
        Product product = productService.findById(id).get();
        model.addAttribute("product", product);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        List<Manufacturer> manufacturers = manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);

        Set<Long> manufacturerIds = new HashSet<>();
        for (Manufacturer manufacturer : product.getManufacturers()) {
            manufacturerIds.add(manufacturer.getManufacturerId());
        }
        model.addAttribute("manufacturerIds", manufacturerIds);

        return "admin/update/productUpdateForm";
    }

    @PostMapping("admin/products/update")
    public String updateProduct(@ModelAttribute("product") Product product,
                                @RequestParam("manufacturerIds") List<Long> manufacturerIds,
                                @RequestParam("categoryId") Long categoryId,
                                @RequestParam Long id) {
        product.setCategory(categoryService.findById(categoryId).get());
        Set<Manufacturer> manufacturers = new HashSet<>();
        for (Long manufacturerId : manufacturerIds) {
            manufacturers.add(manufacturerService.findById(manufacturerId).get());
        }
        product.setManufacturers(manufacturers);
        productService.update(product, id);
        return "redirect:/admin/products";
    }

    @GetMapping("admin/products/delete")
    public String deleteProduct(@RequestParam Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
   
    @GetMapping("admin/manufacturers")
    public String showManufacturers(Model model) {
        model.addAttribute("manufacturers", manufacturerService.findAll());
        return "admin/manufacturers";
    }

    @GetMapping("admin/manufacturers/create")
    public String createManufacturer(Model model) {
        model.addAttribute("manufacturer", new Manufacturer());
        return "admin/create/manufacturerCreateForm";
    }

    @PostMapping("admin/manufacturers/create")
    public String createManufacturer(@ModelAttribute("manufacturer") Manufacturer manufacturer) {
        manufacturerService.save(manufacturer);
        return "redirect:/admin/manufacturers";
    }

    @GetMapping("admin/manufacturers/update")
    public String updateManufacturer(@RequestParam Long id, Model model) {
        model.addAttribute("manufacturer", manufacturerService.findById(id).get());
        return "admin/update/manufacturerUpdateForm";
    }

    @PostMapping("admin/manufacturers/update")
    public String updateManufacturer(@ModelAttribute("manufacturer") Manufacturer manufacturer, @RequestParam Long id) {
        manufacturerService.update(manufacturer, id);
        return "redirect:/admin/manufacturers";
    }

    @GetMapping("admin/manufacturers/delete")
    public String deleteManufacturer(@RequestParam Long id) {
        manufacturerService.delete(id);
        return "redirect:/admin/manufacturers";
    }


    @GetMapping("admin/sales/weekly")
    public ResponseEntity<Map<Date, Double>> weeklySales(@RequestParam String date) {
        System.out.println(date);
        Date startDate = Date.valueOf(date);
        Date endDate = Date.valueOf(startDate.toLocalDate().plusDays(7));
        return ResponseEntity.ok(salesReportService.weeklySales(startDate, endDate));
    }



}
