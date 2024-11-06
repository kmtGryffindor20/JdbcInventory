package com.inventory.backend.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    private String emailId;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String shippingAddress;

    private String billingAddress;

    private String username;

}
