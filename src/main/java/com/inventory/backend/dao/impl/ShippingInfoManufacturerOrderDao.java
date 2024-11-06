package com.inventory.backend.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.ManufacturerOrder;
import com.inventory.backend.entities.ShippingInfoManufacturerOrder;

@Repository
public class ShippingInfoManufacturerOrderDao implements IDao<ShippingInfoManufacturerOrder, Long> {


    private final JdbcTemplate jdbcTemplate;

    public ShippingInfoManufacturerOrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(ShippingInfoManufacturerOrder a) {
        String sql = "INSERT INTO manufacturer_order_shipping_info (shipping_date, expected_delivery_date, status, manufacturer_order_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, a.getShippingDate(), a.getExpectedDeliveryDate(), a.getStatus().name(), a.getManufacturerOrder().getOrderId());
    }

    @Override
    public Optional<ShippingInfoManufacturerOrder> findById(Long id) {
        String sql = "SELECT simo.*, mo.* FROM manufacturer_order_shipping_info simo JOIN manufacturer_orders mo ON simo.manufacturer_order_id = mo.order_id WHERE simo.shipping_info_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ShippingInfoManufacturerOrderRowMapper(), id));
    }

    @Override
    public List<ShippingInfoManufacturerOrder> findAll() {
        String sql = "SELECT simo.*, mo.* FROM manufacturer_order_shipping_info simo JOIN manufacturer_orders mo ON simo.manufacturer_order_id = mo.order_id";
        return jdbcTemplate.query(sql, new ShippingInfoManufacturerOrderRowMapper());
    }

    @Override
    public void update(ShippingInfoManufacturerOrder a, Long id) {
        System.out.println(a.getStatus());
        String sql = "UPDATE manufacturer_order_shipping_info SET shipping_date = ?, expected_delivery_date = ?, status = ? WHERE shipping_info_id = ?";
        jdbcTemplate.update(sql, a.getShippingDate(), a.getExpectedDeliveryDate(), a.getStatus().name(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM manufacturer_order_shipping_info WHERE shipping_info_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<ShippingInfoManufacturerOrder> findByOrderId(Long orderId) {
        String sql = "SELECT simo.*, mo.* FROM manufacturer_order_shipping_info simo LEFT JOIN manufacturer_orders mo ON simo.manufacturer_order_id = mo.order_id WHERE simo.manufacturer_order_id = ?";
        List<ShippingInfoManufacturerOrder> results = jdbcTemplate.query(sql, new ShippingInfoManufacturerOrderRowMapper(), orderId);
        if (results.isEmpty()) {
            create(ShippingInfoManufacturerOrder.builder()
                    .manufacturerOrder(ManufacturerOrder.builder()
                            .orderId(orderId)
                            .build())
                    .shippingDate(null)
                    .expectedDeliveryDate(null)
                    .status(ShippingInfoManufacturerOrder.Status.PENDING)
                    .build());
            results = jdbcTemplate.query(sql, new ShippingInfoManufacturerOrderRowMapper(), orderId);
        }
        return results.stream().findFirst();
    }

    public Map<Long, String> idStatusMap() {
        String sql = "SELECT manufacturer_order_id, status FROM manufacturer_order_shipping_info";
        return jdbcTemplate.query(sql, new IdStatusRowMapper()).stream().collect(java.util.stream.Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static class IdStatusRowMapper implements RowMapper<Map.Entry<Long, String>> {
        @Override
        public Map.Entry<Long, String> mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return Map.entry(rs.getLong("manufacturer_order_id"), rs.getString("status"));
        }
    }

    public static class ShippingInfoManufacturerOrderRowMapper implements RowMapper<ShippingInfoManufacturerOrder> {
        @Override
        public ShippingInfoManufacturerOrder mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return ShippingInfoManufacturerOrder.builder()
                    .manufacturerOrder(ManufacturerOrder.builder()
                            .orderId(rs.getLong("manufacturer_order_id"))
                            .dateOfOrder(rs.getDate("date_of_order"))
                            .build())
                    .shippingInfoId(rs.getLong("shipping_info_id"))
                    .shippingDate(rs.getDate("shipping_date"))
                    .expectedDeliveryDate(rs.getDate("expected_delivery_date"))
                    .status(ShippingInfoManufacturerOrder.Status.valueOf(rs.getString("status")))
                    .build();
        }
    }
    
}
