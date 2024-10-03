package com.inventory.backend.entities;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReturns {

    private Long orderId;

    private Date returnDate;

    private String returnReason;

}
