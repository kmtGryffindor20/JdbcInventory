package com.inventory.backend.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.OrderReturns;

@Repository
public class OrderReturnsDao implements IDao<OrderReturns, Long> {

    private final JdbcTemplate jdbcTemplate;

    public OrderReturnsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(OrderReturns a) {
        String sql = "INSERT INTO order_returns (order_id, return_date, return_reason) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, a.getOrderId(), a.getReturnDate(), a.getReturnReason());
    }

    @Override
    public Optional<OrderReturns> findById(Long id) {
        String sql = "SELECT * FROM order_returns WHERE order_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new OrderReturnsRowMapper(), id));
    }

    @Override
    public List<OrderReturns> findAll() {
        String sql = "SELECT * FROM order_returns";
        return jdbcTemplate.query(sql, new OrderReturnsRowMapper());
    }

    @Override
    public void update(OrderReturns a, Long id) {
        String sql = "UPDATE order_returns SET return_date = ?, return_reason = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, a.getOrderId(), a.getReturnDate(), a.getReturnReason(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM order_returns WHERE order_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class OrderReturnsRowMapper implements RowMapper<OrderReturns> {
        @Override
        public OrderReturns mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return OrderReturns.builder()
                    .orderId(rs.getLong("order_id"))
                    .returnDate(rs.getDate("return_date"))
                    .returnReason(rs.getString("return_reason"))
                    .build();
        }
    }
    
}
