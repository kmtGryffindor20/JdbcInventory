package com.inventory.backend.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inventory.backend.services.impl.PaymentService;


@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Value("${razorpay.key_id}")
    private String razorpayKeyId;

    @PostMapping("/razorpay/payment")
    public String handlePayment(@RequestParam("totalAmount") double totalAmount, Model model) {
        // Process the payment and create the Razorpay order
        try {
            String razorpayOrderId = paymentService.createOrder(totalAmount); // Assuming this method creates the order
            model.addAttribute("razorpayOrderId", razorpayOrderId);
            model.addAttribute("totalAmount", totalAmount);
            model.addAttribute("razorpayKeyId", razorpayKeyId); // Add Razorpay key to the model

            // Return to a page where you can call Razorpay's checkout API
            return "payment"; // Redirect to the payment page to handle Razorpay checkout
        } catch (Exception e) {
            model.addAttribute("error", "Payment processing failed: " + e.getMessage());
            return "error"; // Show an error page if payment processing fails
        }
    }
}
