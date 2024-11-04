package com.inventory.backend.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerOrder{

    private Long orderId;

    private Manufacturer manufacturer;

    private Employee processedByEmployee;

    private Date dateOfOrder;

    @Builder.Default
    private Set<CustomerOrder.Pair<Product, Integer>> products = new HashSet<>();

}