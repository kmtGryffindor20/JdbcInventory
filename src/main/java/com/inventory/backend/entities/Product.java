package com.inventory.backend.entities;

import java.sql.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long productId;

    private String productName;

    private Date expiryDate;

    private Long stockQuantity;

    private Double costPrice;

    private Double maximumRetailPrice;

    private Category category;

    private Set<Manufacturer> manufacturers;


}
