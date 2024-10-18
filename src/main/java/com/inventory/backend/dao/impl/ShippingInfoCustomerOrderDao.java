package com.inventory.backend.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.Customer;
import com.inventory.backend.entities.CustomerOrder;
import com.inventory.backend.entities.Employee;
import com.inventory.backend.entities.ShippingInfoCustomerOrder;

@Repository
public class ShippingInfoCustomerOrderDao implements IDao<ShippingInfoCustomerOrder, Long> {


    private final JdbcTemplate jdbcTemplate;

    public ShippingInfoCustomerOrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(ShippingInfoCustomerOrder a) {
        String sql = "INSERT INTO shipping_info_customer_order (shipping_date, expected_delivery_date, status, order_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, a.getShippingDate(), a.getExpectedDeliveryDate(), a.getStatus().name(), a.getOrder().getOrderId());
    }

    @Override
    public Optional<ShippingInfoCustomerOrder> findById(Long id) {
        String sql = "SELECT sico.*, co.* FROM shipping_info_customer_order sico JOIN customer_orders co ON sico.order_id = co.order_id WHERE sico.order_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ShippingInfoCustomerOrderRowMapper(), id));
    }

    @Override
    public List<ShippingInfoCustomerOrder> findAll() {
        String sql = "SELECT sico.*, co.* FROM shipping_info_customer_order sico JOIN customer_orders co ON sico.order_id = co.order_id";
        return jdbcTemplate.query(sql, new ShippingInfoCustomerOrderRowMapper());
    }

    @Override
    public void update(ShippingInfoCustomerOrder a, Long id) {
        String sql = "UPDATE shipping_info_customer_order SET shipping_date = ?, expected_delivery_date = ?, status = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, a.getShippingDate(), a.getExpectedDeliveryDate(), a.getStatus(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM shipping_info_customer_order WHERE order_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class ShippingInfoCustomerOrderRowMapper implements RowMapper<ShippingInfoCustomerOrder> {
        @Override
        public ShippingInfoCustomerOrder mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return ShippingInfoCustomerOrder.builder()
                    .order(CustomerOrder.builder()
                            .orderId(rs.getLong("order_id"))
                            .dateOfOrder(rs.getDate("date_of_order"))
                            .customer(Customer.builder()
                                    .emailId(rs.getString("customer_email"))
                                    .build())
                            .paymentMethod(CustomerOrder.PaymentMethod.valueOf(rs.getString("payment_method")))
                            .build()
                    )
                    .shippingDate(rs.getDate("shipping_date"))
                    .expectedDeliveryDate(rs.getDate("expected_delivery_date"))
                    .status(ShippingInfoCustomerOrder.Status.valueOf(rs.getString("status")))
                    .build();
        }
    }
    
}
