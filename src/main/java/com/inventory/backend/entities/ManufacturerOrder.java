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
public class ManufacturerOrder{

    private Long orderId;

    private Long manufacturerOrderId;

    private Long processedByEmployeeId;

    private Date dateOfOrder;

    private Long processorEmployeeId;

    private Set<CustomerOrder.Pair<Long, Integer>> products;

}