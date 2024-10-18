package com.inventory.backend.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.ManufacturerOrder;
import com.inventory.backend.entities.ShippingInfoCustomerOrder;
import com.inventory.backend.entities.ShippingInfoManufacturerOrder;

@Repository
public class ShippingInfoManufacturerOrderDao implements IDao<ShippingInfoManufacturerOrder, Long> {


    private final JdbcTemplate jdbcTemplate;

    public ShippingInfoManufacturerOrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(ShippingInfoManufacturerOrder a) {
        String sql = "INSERT INTO shipping_info_manufacturer_orders (shipping_date, expected_delivery_date, status, manufacturer_order_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, a.getShippingDate(), a.getExpectedDeliveryDate(), a.getStatus().name(), a.getManufacturerOrder().getOrderId());
    }

    @Override
    public Optional<ShippingInfoManufacturerOrder> findById(Long id) {
        String sql = "SELECT simo.*, mo.* FROM shipping_info_manufacturer_orders simo JOIN manufacturer orders mo ON simo.manufacturer_order_id = mo.manufacturer_order_id WHERE simo.manufacturer_order_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ShippingInfoManufacturerOrderRowMapper(), id));
    }

    @Override
    public List<ShippingInfoManufacturerOrder> findAll() {
        String sql = "SELECT simo.*, mo.* FROM shipping_info_manufacturer_orders simo JOIN manufacturer orders mo ON simo.manufacturer_order_id = mo.manufacturer_order_id";
        return jdbcTemplate.query(sql, new ShippingInfoManufacturerOrderRowMapper());
    }

    @Override
    public void update(ShippingInfoManufacturerOrder a, Long id) {
        String sql = "UPDATE shipping_info_manufacturer_orders SET shipping_date = ?, expected_delivery_date = ?, status = ? WHERE manufacturer_order_id = ?";
        jdbcTemplate.update(sql, a.getShippingDate(), a.getExpectedDeliveryDate(), a.getStatus(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM shipping_info_manufacturer_orders WHERE manufacturer_order_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class ShippingInfoManufacturerOrderRowMapper implements RowMapper<ShippingInfoManufacturerOrder> {
        @Override
        public ShippingInfoManufacturerOrder mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return ShippingInfoManufacturerOrder.builder()
                    .manufacturerOrder(ManufacturerOrder.builder()
                            .orderId(rs.getLong("manufacturer_order_id"))
                            .dateOfOrder(rs.getDate("date_of_order"))
                            .build())
                    .shippingDate(rs.getDate("shipping_date"))
                    .expectedDeliveryDate(rs.getDate("expected_delivery_date"))
                    .status(ShippingInfoCustomerOrder.Status.valueOf(rs.getString("status")))
                    .build();
        }
    }
    
}
