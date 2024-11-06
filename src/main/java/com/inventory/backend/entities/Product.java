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
public class Product {

    private Long productId;

    private String productName;

    private String description;

    private Date expiryDate;

    private Integer stockQuantity;

    private Double sellingPrice;

    private Double maximumRetailPrice;

    private Category category;

    private Set<Pair<Manufacturer, Double>> manufacturers = new HashSet<>();

    public static class Pair<T, U> {
        public T first;
        public U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public U getSecond() {
            return second;
        }
    }


}
