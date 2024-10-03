package com.inventory.backend.entities;


import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {
    
    private Long manufacturerId;

    private String manufacturerName;

    private String address;

    private Set<String> contactNumbers;

    private Set<String> emailIds;

}
