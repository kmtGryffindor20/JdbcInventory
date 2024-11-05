package com.inventory.backend.dao.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.Category;
import com.inventory.backend.entities.Manufacturer;
import com.inventory.backend.entities.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            preparedStatement.setLong(6, a.getCategory().getCategoryId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next())
                {

                    insertId = generatedKeys.getInt(1);
    
                    String sql2 = "INSERT INTO product_manufacturers (product_id, manufacturer_id) VALUES (?, ?)";
                    for (Manufacturer manufacturer : a.getManufacturers()) {
                        jdbcTemplate.update(sql2, insertId, manufacturer.getManufacturerId());
                    }
                }


            }
        }catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT p.*, c.*, m.* FROM products p JOIN categories c ON p.category_id = c.category_id JOIN product_manufacturers pm ON p.product_id = pm.product_id JOIN manufacturers m ON pm.manufacturer_id = m.manufacturer_id WHERE p.product_id = ?";
        Map<Long, Product> productMap = jdbcTemplate.query(sql, new ProductRowMapper(), id);
        return Optional.ofNullable(productMap.get(id));
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT p.*, c.*, m.* FROM products p JOIN categories c ON p.category_id = c.category_id JOIN product_manufacturers pm ON p.product_id = pm.product_id JOIN manufacturers m ON pm.manufacturer_id = m.manufacturer_id";
        Map<Long, Product> productMap = jdbcTemplate.query(sql, new ProductRowMapper());
        return List.copyOf(productMap.values());
    }

    public List<Product> findDealsOfTheDay(){
        String sql = "SELECT p.*, c.category_name FROM products p JOIN categories c ON p.category_id = c.category_id ORDER BY (p.maximum_retail_price - p.cost_price) DESC LIMIT 15";
        List<Product> productMap = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product product = Product.builder()
                    .productId(rs.getLong("product_id"))
                    .productName(rs.getString("product_name"))
                    .expiryDate(rs.getDate("expiry_date"))
                    .stockQuantity(rs.getInt("stock_quantity"))
                    .costPrice(rs.getDouble("cost_price"))
                    .maximumRetailPrice(rs.getDouble("maximum_retail_price"))
                    .category(Category.builder()
                            .categoryId(rs.getLong("category_id"))
                            .categoryName(rs.getString("category_name"))
                            .build())
                    .manufacturers(new HashSet<>())
                    .build();
            return product;
        });
        return productMap;
    }

    @Override
    public void update(Product a, Long id) {
        String sql = "UPDATE products SET product_name = ?, expiry_date = ?, stock_quantity = ?, cost_price = ?, maximum_retail_price = ?, category_id = ? WHERE product_id = ?";
        String sql2 = "DELETE FROM product_manufacturers WHERE product_id = ?";
        String sql3 = "INSERT INTO product_manufacturers (product_id, manufacturer_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, a.getProductName(), a.getExpiryDate(), a.getStockQuantity(), a.getCostPrice(), a.getMaximumRetailPrice(), a.getCategory().getCategoryId(), id);
        jdbcTemplate.update(sql2, id);
        jdbcTemplate.batchUpdate(sql3, a.getManufacturers().stream().map(manufacturer -> new Object[]{id, manufacturer.getManufacturerId()}).toList());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Product> getByCategory(Long categoryId) {
        String sql = "SELECT p.*, c.*, m.* FROM products p LEFT JOIN categories c ON p.category_id = c.category_id LEFT JOIN product_manufacturers pm ON p.product_id = pm.product_id LEFT JOIN manufacturers m ON pm.manufacturer_id = m.manufacturer_id WHERE c.category_id = ?";
        Map<Long, Product> productMap = jdbcTemplate.query(sql, new ProductRowMapper(), categoryId);
        return List.copyOf(productMap.values());
    }

    public void updateProductQuantity(Long productId, int quantityBought) {
        String sql = "UPDATE products SET stock_quantity = stock_quantity - ? WHERE product_id = ?";
        jdbcTemplate.update(sql, quantityBought, productId);
    }

    public Map<String, Integer> stockQuantityByCategory() {
        String sql = "SELECT c.category_name, SUM(p.stock_quantity) AS total_stock FROM products p RIGHT JOIN categories c ON p.category_id = c.category_id GROUP BY c.category_name";
        return jdbcTemplate.query(sql, (rs) -> {
            Map<String, Integer> stockQuantityByCategory = new HashMap<>();
            while (rs.next()) {
                stockQuantityByCategory.put(rs.getString("category_name"), rs.getInt("total_stock"));
            }
            return stockQuantityByCategory;
        });
    }

    public static class ProductRowMapper implements ResultSetExtractor<Map<Long, Product>> {
        @Override
        public Map<Long, Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Product> productMap = new HashMap<>();
            while (rs.next()) {
                Long productId = rs.getLong("product_id");
                Product product = productMap.get(productId);
                if (product == null) {
                    product = Product.builder()
                            .productId(productId)
                            .productName(rs.getString("product_name"))
                            .expiryDate(rs.getDate("expiry_date"))
                            .stockQuantity(rs.getInt("stock_quantity"))
                            .costPrice(rs.getDouble("cost_price"))
                            .maximumRetailPrice(rs.getDouble("maximum_retail_price"))
                            .category(Category.builder()
                                    .categoryId(rs.getLong("category_id"))
                                    .categoryName(rs.getString("category_name"))
                                    .categoryDescription(rs.getString("category_description"))
                                    .build())
                            .manufacturers(new HashSet<>())
                            .build();
                    productMap.put(productId, product);
                }
                Manufacturer manufacturer = Manufacturer.builder()
                        .manufacturerId(rs.getLong("manufacturer_id"))
                        .manufacturerName(rs.getString("manufacturer_name"))
                        .build();
                product.getManufacturers().add(manufacturer);
            }
            return productMap;
        }
        
    }
    
}
