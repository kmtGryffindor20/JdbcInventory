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
public class ShippingInfoCustomerOrder {

    private Long shippingInfoId;

    private Date shippingDate;

    private Date expectedDeliveryDate;

    private String status;

    private Long orderId;
}
