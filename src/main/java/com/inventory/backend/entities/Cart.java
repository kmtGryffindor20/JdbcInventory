package com.inventory.backend.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.Triple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    
    private Long cartId;

    private Customer customer;

    @Builder.Default
    private Set<Triple<Product, Integer, Date>> products = new HashSet<>();

}
