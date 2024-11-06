package com.inventory.backend.controllers.impl;

import com.inventory.backend.entities.Customer;
import com.inventory.backend.entities.CustomerOrder;
import com.inventory.backend.services.impl.CustomerOrderService;
import com.inventory.backend.services.impl.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller // Change from @RestController to @Controller

public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;
    private final CustomerService customerService;

    @Autowired
    public CustomerOrderController(CustomerOrderService customerOrderService,
                                   CustomerService customerService) {
        this.customerOrderService = customerOrderService;
        this.customerService = customerService;
    }

    // Endpoint to display all orders for a specific customer by email
    @GetMapping("/orders")
    public String getOrdersByCustomerEmail(Model model, Principal principal) {
        // Get the email of the currently logged in user
        String username = principal.getName();
        // Find the customer by username
        Optional<Customer> customerOptional = customerService.findByUsername(username);
        if (customerOptional.isEmpty()) {
            // Optionally add an attribute to display a message in the HTML
            model.addAttribute("message", "No customer found for this user.");
            return "customer-orders"; // Ensure this matches the name of your HTML file
        }
        String customerEmail = customerOptional.get().getEmailId();
        List<CustomerOrder> orders = customerOrderService.findOrdersByCustomerEmail(customerEmail);
        if (orders.isEmpty()) {
            // Optionally add an attribute to display a message in the HTML
            model.addAttribute("message", "It looks like you haven't placed any orders yet.");
        } else {
            model.addAttribute("orders", orders);
        }
        // Return the name of the Thymeleaf template
        return "customer-orders"; // Ensure this matches the name of your HTML file
    }
}
