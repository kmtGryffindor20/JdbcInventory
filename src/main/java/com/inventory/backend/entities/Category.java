package com.inventory.backend.entities;

import com.inventory.backend.dao.impl.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categories")
public class Category {
    
    private Long categoryId;

    private String categoryName;

}
