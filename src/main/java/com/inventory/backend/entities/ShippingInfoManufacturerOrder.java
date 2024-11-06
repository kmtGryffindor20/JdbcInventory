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
public class ShippingInfoManufacturerOrder {

    private Long shippingInfoId;

    private Date shippingDate;

    private Date expectedDeliveryDate;

    private Status status;

    private ManufacturerOrder manufacturerOrder;

    public enum Status {
        SHIPPED,
        ARRIVED,
        PENDING,
        CANCELLED
    }
}
