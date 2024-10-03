package com.inventory.backend.entities;

import com.inventory.backend.embeddable.SalesReportCompositeKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesReport {
    
    private SalesReportCompositeKey salesReportCompositeKey;

    private Double totalSales;

    private Integer totalOrders;

    private Long topSellingProductId;

}
