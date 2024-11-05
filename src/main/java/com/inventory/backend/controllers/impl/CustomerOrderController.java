package com.inventory.backend.controllers.impl;

import com.inventory.backend.entities.CustomerOrder;
import com.inventory.backend.services.impl.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller // Change from @RestController to @Controller

public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    @Autowired
    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    // Endpoint to display all orders for a specific customer by email
    @GetMapping("/customer/{email}")
    public String getOrdersByCustomerEmail(@PathVariable("email") String customerEmail, Model model) {
        List<CustomerOrder> orders = customerOrderService.findOrdersByCustomerEmail(customerEmail);
        if (orders.isEmpty()) {
            // Optionally add an attribute to display a message in the HTML
            model.addAttribute("message", "No orders found for this customer.");
        } else {
            model.addAttribute("orders", orders);
        }
        // Return the name of the Thymeleaf template
        return "customer-orders"; // Ensure this matches the name of your HTML file
    }
}
