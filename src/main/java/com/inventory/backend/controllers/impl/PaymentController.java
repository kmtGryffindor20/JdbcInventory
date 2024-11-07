package com.inventory.backend.controllers.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inventory.backend.entities.Cart;
import com.inventory.backend.entities.Customer;
import com.inventory.backend.entities.CustomerOrder;
import com.inventory.backend.entities.Employee;
import com.inventory.backend.entities.Product;
import com.inventory.backend.services.impl.CartService;
import com.inventory.backend.services.impl.CustomerOrderService;
import com.inventory.backend.services.impl.PaymentService;
import com.inventory.backend.services.impl.ProductService;


@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Value("${razorpay.key_id}")
    private String razorpayKeyId;

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private ProductService productService;

    @PostMapping("/razorpay/payment")
    public String handlePayment(@RequestParam("totalAmount") double totalAmount, Model model,
                                @RequestParam("products") List<Long> productIds,
                                @RequestParam("quantities") List<Integer> quantities,
                                @RequestParam("cartId") Long cartId,
                                @RequestParam("paymentMethod") String paymentMethod) {

        Cart cart = cartService.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        Customer customer = cart.getCustomer();

        if (paymentMethod.equals("CASH"))
        {
            CustomerOrder customerOrder = CustomerOrder.builder()
                                                        .customer(customer)
                                                        .dateOfOrder(Date.valueOf(LocalDate.now()))
                                                        .processorEmployee(Employee.builder().employeeId(1L).build())
                                                        .paymentMethod(CustomerOrder.PaymentMethod.CASH)
                                                        .build();
            for (int i = 0; i < productIds.size(); i++) {
                Product product = productService.findById(productIds.get(i)).get();
                customerOrder.getProducts().add(new CustomerOrder.Pair<>(product, quantities.get(i)));
            }

            customerOrderService.save(customerOrder);

            cartService.delete(cartId);

            return "redirect:/orders";
        }

        // Process the payment and create the Razorpay order
        try {
            String razorpayOrderId = paymentService.createOrder(totalAmount); // Assuming this method creates the order
            model.addAttribute("razorpayOrderId", razorpayOrderId);
            model.addAttribute("totalAmount", totalAmount);
            model.addAttribute("razorpayKeyId", razorpayKeyId); // Add Razorpay key to the model
            model.addAttribute("paymentMethod", paymentMethod);

            // Return to a page where you can call Razorpay's checkout API
            return "payment"; // Redirect to the payment page to handle Razorpay checkout
        } catch (Exception e) {
            model.addAttribute("error", "Payment processing failed: " + e.getMessage());
            return "error"; // Show an error page if payment processing fails
        }
    }
}
