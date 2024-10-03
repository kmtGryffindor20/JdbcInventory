package com.inventory.backend.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class ProductDao implements IDao<Product, Long> {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Product a) {
        String sql = "INSERT INTO products (product_name, expiry_date, stock_quantity, cost_price, maximum_retail_price, category_id) VALUES (?, ?, ?, ?, ?, ?)";
        int insertId = -1;

        try {
            PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, a.getProductName());
            preparedStatement.setDate(2, a.getExpiryDate());
            preparedStatement.setLong(3, a.getStockQuantity());
            preparedStatement.setDouble(4, a.getCostPrice());
            preparedStatement.setDouble(5, a.getMaximumRetailPrice());
            preparedStatement.setLong(6, a.getCategoryId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next())
                {

                    insertId = generatedKeys.getInt(1);
    
                    String sql2 = "INSERT INTO product_manufacturers (product_id, manufacturer_id) VALUES (?, ?)";
                    for (Long manufacturerId : a.getManufacturerIds()) {
                        jdbcTemplate.update(sql2, insertId, manufacturerId);
                    }
                }


            }
        }catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        List<Product> results = jdbcTemplate.query(sql, new ProductRowMapper(), id);
        return results.stream().findFirst();
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    @Override
    public void update(Product a, Long id) {
        String sql = "UPDATE products SET product_name = ?, expiry_date = ?, stock_quantity = ?, cost_price = ?, maximum_retail_price = ?, category_id = ? WHERE product_id = ?";
        jdbcTemplate.update(sql, a.getProductName(), a.getExpiryDate(), a.getStockQuantity(), a.getCostPrice(), a.getMaximumRetailPrice(), a.getCategoryId(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return Product.builder()
                    .productId(rs.getLong("product_id"))
                    .productName(rs.getString("product_name"))
                    .expiryDate(rs.getDate("expiry_date"))
                    .stockQuantity(rs.getLong("stock_quantity"))
                    .costPrice(rs.getDouble("cost_price"))
                    .maximumRetailPrice(rs.getDouble("maximum_retail_price"))
                    .categoryId(rs.getLong("category_id"))
                    .build();
        }
    }
    
}
