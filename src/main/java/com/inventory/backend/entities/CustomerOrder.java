package com.inventory.backend.entities;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {
    

    private Long orderId;

    private String customerId;

    private Date dateOfOrder;

    private PaymentMethod paymentMethod;

    private Long processorEmployeeId;

    private Set<Pair<Long, Integer>> products;


    public class Pair<T, U> {
        public final T first;
        public final U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }

    public enum PaymentMethod {
        CASH,
        CARD,
        NET_BANKING,
        UPI
    }
    


}