package com.inventory.backend.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.CustomerOrder;
import com.inventory.backend.entities.ManufacturerOrder;

@Repository
public class ManufacturerOrderDao implements IDao<ManufacturerOrder, Long> {

    private final JdbcTemplate jdbcTemplate;

    public ManufacturerOrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(ManufacturerOrder a) {
        String sql = "INSERT INTO manufacturer_orders (manufacturer_order_id, processed_by_employee_id, date_of_order) VALUES (?, ?, ?)";
        
        int insertId = -1;

        try{
            PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, a.getManufacturerOrderId());
            preparedStatement.setLong(2, a.getProcessedByEmployeeId());
            preparedStatement.setDate(3, a.getDateOfOrder());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0)
            {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                
                if (generatedKeys.next())
                {
                    insertId = generatedKeys.getInt(1);
                    String sql2 = "INSERT INTO manufacturer_order_products (manufacturer_order_id, product_id, quantity) VALUES (?, ?, ?)";
                    for (CustomerOrder.Pair<Long, Integer> product : a.getProducts()) {
                        jdbcTemplate.update(sql2, insertId, product.first, product.second);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<ManufacturerOrder> findById(Long id) {
        String sql = "SELECT * FROM manufacturer_orders WHERE manufacturer_order_id = ?";
        List<ManufacturerOrder> results = jdbcTemplate.query(sql, new ManufacturerOrderRowMapper(), id);
        return results.stream().findFirst();
    }

    @Override
    public List<ManufacturerOrder> findAll() {
        String sql = "SELECT * FROM manufacturer_orders";
        return jdbcTemplate.query(sql, new ManufacturerOrderRowMapper());
    }

    @Override
    public void update(ManufacturerOrder a, Long id) {
        String sql = "UPDATE manufacturer_orders SET processed_by_employee_id = ?, date_of_order = ? WHERE manufacturer_order_id = ?";
        jdbcTemplate.update(sql, a.getProcessedByEmployeeId(), a.getDateOfOrder(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM manufacturer_orders WHERE manufacturer_order_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class ManufacturerOrderRowMapper implements RowMapper<ManufacturerOrder> {
        @Override
        public ManufacturerOrder mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return ManufacturerOrder.builder()
                    .manufacturerOrderId(rs.getLong("manufacturer_order_id"))
                    .processedByEmployeeId(rs.getLong("processed_by_employee_id"))
                    .dateOfOrder(rs.getDate("date_of_order"))
                    .build();
        }
    }

    
}
