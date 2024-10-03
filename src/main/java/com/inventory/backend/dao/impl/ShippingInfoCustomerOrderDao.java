package com.inventory.backend.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
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
        jdbcTemplate.update(sql, a.getShippingDate(), a.getExpectedDeliveryDate(), a.getStatus().name(), a.getOrderId());
    }

    @Override
    public Optional<ShippingInfoCustomerOrder> findById(Long id) {
        String sql = "SELECT * FROM shipping_info_customer_order WHERE order_id = ?";
        List<ShippingInfoCustomerOrder> results = jdbcTemplate.query(sql, new ShippingInfoCustomerOrderRowMapper(), id);
        return results.stream().findFirst();
    }

    @Override
    public List<ShippingInfoCustomerOrder> findAll() {
        String sql = "SELECT * FROM shipping_info_customer_order";
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
                    .orderId(rs.getLong("order_id"))
                    .shippingDate(rs.getDate("shipping_date"))
                    .expectedDeliveryDate(rs.getDate("expected_delivery_date"))
                    .status(ShippingInfoCustomerOrder.Status.valueOf(rs.getString("status")))
                    .build();
        }
    }
    
}
