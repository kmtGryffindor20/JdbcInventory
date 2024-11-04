package com.inventory.backend.controllers.impl;

import com.inventory.backend.entities.Category;
import com.inventory.backend.entities.Product;
import com.inventory.backend.services.impl.CategoryServiceImpl;
import com.inventory.backend.services.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/categories/{categoryId}/products")
public class AdminProductsController {

    private final CategoryServiceImpl categoryService;
    private final ProductService productService;

    @Autowired
    public AdminProductsController(CategoryServiceImpl categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public String viewProductsByCategory(@PathVariable Long categoryId, Model model) {
        Optional<Category> optionalCategory = categoryService.findById(categoryId);
        if (optionalCategory.isPresent()) {
            model.addAttribute("category", optionalCategory.get());
            List<Product> products = productService.getByCategory(categoryId);
            model.addAttribute("products", products);
            return "admin-category-product";
        } else {
            return "notfound";
        }
    }

    @PostMapping("/add")
    public String addProductToCategory(@PathVariable Long categoryId, @RequestParam String productName,
                                       @RequestParam double costPrice, @RequestParam double maximumRetailPrice) {
        Optional<Category> optionalCategory = categoryService.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Product product = Product.builder()
                    .productName(productName)
                    .costPrice(costPrice)
                    .maximumRetailPrice(maximumRetailPrice)
                    .category(optionalCategory.get())
                    .build();
            productService.save(product);
            return "redirect:/categories/" + categoryId + "/products";
        } else {
            return "notfound";
        }
    }

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long categoryId, @PathVariable Long productId) {
        productService.delete(productId);
        return "redirect:/categories/" + categoryId + "/products";
    }

    @GetMapping("/{productId}")
    public String viewProductDetails(@PathVariable Long categoryId, @PathVariable Long productId, Model model) {
        Optional<Product> optionalProduct = productService.findById(productId);
        if (optionalProduct.isPresent()) {
            model.addAttribute("product", optionalProduct.get());
            return "product-details";
        } else {
            return "notfound";
        }
    }
}
