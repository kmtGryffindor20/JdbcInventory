package com.inventory.backend.dao.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.Category;
import com.inventory.backend.entities.Manufacturer;
import com.inventory.backend.entities.Product;


import java.nio.file.Files;
import java.nio.file.Path;
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
        String sql = "INSERT INTO products (product_name, expiry_date, stock_quantity, selling_price, maximum_retail_price, category_id, description, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, a.getProductName());
            preparedStatement.setDate(2, a.getExpiryDate());
            preparedStatement.setLong(3, a.getStockQuantity());
            preparedStatement.setDouble(4, a.getSellingPrice());
            preparedStatement.setDouble(5, a.getMaximumRetailPrice());
            preparedStatement.setLong(6, a.getCategory().getCategoryId());
            preparedStatement.setString(7, a.getDescription());
            preparedStatement.setString(8, a.getImageUrl());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next())
                {

                    int insertId = generatedKeys.getInt(1);
    
                    String sql2 = "INSERT INTO product_manufacturers (product_id, manufacturer_id) VALUES (?, ?, ?)";
                    jdbcTemplate.batchUpdate(sql2, a.getManufacturers().stream().map(pair -> new Object[]{insertId, pair.getFirst().getManufacturerId(), pair.getSecond()}).toList());
                }


            }
        }catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void create(Product a, TreeMap<Long, Double> manufacturersCostPricesMap) {
        String sql = "INSERT INTO products (product_name, expiry_date, stock_quantity, selling_price, maximum_retail_price, category_id, description, image_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, a.getProductName());
            preparedStatement.setDate(2, a.getExpiryDate());
            preparedStatement.setLong(3, a.getStockQuantity());
            preparedStatement.setDouble(4, a.getSellingPrice());
            preparedStatement.setDouble(5, a.getMaximumRetailPrice());
            preparedStatement.setLong(6, a.getCategory().getCategoryId());
            preparedStatement.setString(7, a.getDescription());
            preparedStatement.setString(8, a.getImageUrl());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next())
                {

                    int insertId = generatedKeys.getInt(1);
    
                    String sql2 = "INSERT INTO product_manufacturers (product_id, manufacturer_id, cost_price) VALUES (?, ?, ?)";
                    jdbcTemplate.batchUpdate(sql2, manufacturersCostPricesMap.entrySet().stream().map(entry -> new Object[]{insertId, entry.getKey(), entry.getValue()}).toList());
                }
            }
        }catch (Exception e) {
                e.printStackTrace();
            }
        }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT p.*, c.*, m.*, pm.cost_price FROM products p JOIN categories c ON p.category_id = c.category_id JOIN product_manufacturers pm ON p.product_id = pm.product_id JOIN manufacturers m ON pm.manufacturer_id = m.manufacturer_id WHERE p.product_id = ?";
        Map<Long, Product> productMap = jdbcTemplate.query(sql, new ProductRowMapper(), id);
        return Optional.ofNullable(productMap.get(id));
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT p.*, c.*, m.*, pm.cost_price FROM products p JOIN categories c ON p.category_id = c.category_id JOIN product_manufacturers pm ON p.product_id = pm.product_id JOIN manufacturers m ON pm.manufacturer_id = m.manufacturer_id";
        Map<Long, Product> productMap = jdbcTemplate.query(sql, new ProductRowMapper());
        return List.copyOf(productMap.values());
    }

    public List<Product> findDealsOfTheDay(){
        String sql = "SELECT p.*, c.category_name FROM products p JOIN categories c ON p.category_id = c.category_id ORDER BY (p.maximum_retail_price - p.selling_price) DESC LIMIT 15";
        List<Product> productMap = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product product = Product.builder()
                    .productId(rs.getLong("product_id"))
                    .productName(rs.getString("product_name"))
                    .expiryDate(rs.getDate("expiry_date"))
                    .description(rs.getString("description"))
                    .stockQuantity(rs.getInt("stock_quantity"))
                    .sellingPrice(rs.getDouble("selling_price"))
                    .imageUrl(rs.getString("image_url"))
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
        String sql = "UPDATE products SET product_name = ?, expiry_date = ?, stock_quantity = ?, selling_price = ?, maximum_retail_price = ?, category_id = ?, description = ?, image_url = ? WHERE product_id = ?";
        String sql2 = "DELETE FROM product_manufacturers WHERE product_id = ?";
        String sql3 = "INSERT INTO product_manufacturers (product_id, manufacturer_id, cost_price) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql2, id);
        if (a.getImageUrl() != null)
        {
            deletePreviousImages(id);
            jdbcTemplate.update(sql, a.getProductName(), a.getExpiryDate(), a.getStockQuantity(), a.getSellingPrice(), a.getMaximumRetailPrice(), a.getCategory().getCategoryId(), a.getDescription(), a.getImageUrl(), id);
        }
        else{
            sql = "UPDATE products SET product_name = ?, expiry_date = ?, stock_quantity = ?, selling_price = ?, maximum_retail_price = ?, category_id = ?, description = ? WHERE product_id = ?";
            jdbcTemplate.update(sql, a.getProductName(), a.getExpiryDate(), a.getStockQuantity(), a.getSellingPrice(), a.getMaximumRetailPrice(), a.getCategory().getCategoryId(), a.getDescription(), id);
        }
        for (Product.Pair<Manufacturer, Double> pair : a.getManufacturers()) {
            jdbcTemplate.update(sql3, id, pair.getFirst().getManufacturerId(), pair.getSecond());
        }
    }

    public void deletePreviousImages(Long id) {
        String sql = "SELECT image_url FROM products WHERE product_id = ?";
        
        try{
            String imageUrl = jdbcTemplate.queryForObject(sql, String.class, id);
            Path path = Path.of("src/main/resources/static/images/" + imageUrl);
            Files.delete(path);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Product> getByCategory(Long categoryId) {
        String sql = "SELECT p.*, c.*, m.*, pm.cost_price FROM products p LEFT JOIN categories c ON p.category_id = c.category_id LEFT JOIN product_manufacturers pm ON p.product_id = pm.product_id LEFT JOIN manufacturers m ON pm.manufacturer_id = m.manufacturer_id WHERE c.category_id = ?";
        Map<Long, Product> productMap = jdbcTemplate.query(sql, new ProductRowMapper(), categoryId);
        return List.copyOf(productMap.values());
    }

    public List<Product> getByCategory(Long categoryId, int limit) {
        String sql = "SELECT p.*, c.*, m.*, pm.cost_price FROM products p LEFT JOIN categories c ON p.category_id = c.category_id LEFT JOIN product_manufacturers pm ON p.product_id = pm.product_id LEFT JOIN manufacturers m ON pm.manufacturer_id = m.manufacturer_id WHERE c.category_id = ? ORDER BY RAND() LIMIT ?";
        Map<Long, Product> productMap = jdbcTemplate.query(sql, new ProductRowMapper(), categoryId, limit);
        return List.copyOf(productMap.values());
    }
    public void updateProductQuantity(Long productId, int quantityBought) {
        String sql = "UPDATE products SET stock_quantity = stock_quantity - ? WHERE product_id = ?";
        jdbcTemplate.update(sql, quantityBought, productId);
    }

    public List<Product> searchProducts(String keyword)
    {
        String sql = "SELECT p.*, pm.cost_price," +
                        "MATCH (product_name, description) AGAINST (? WITH QUERY EXPANSION) AS relevance "+
                    "FROM products p "+
                    "LEFT JOIN product_manufacturers pm ON p.product_id = pm.product_id "+
                    "WHERE (MATCH (product_name, description) AGAINST (? WITH QUERY EXPANSION) > 0.5"+
                        "OR p.product_name LIKE ? "+ 
                        "OR p.description LIKE ? )";
        List<Pair<Double,Product>> products = jdbcTemplate.query(sql, new RelevanceProductRowMapper(), keyword, keyword, "%" + keyword + "%", "%" + keyword + "%");
        // Sort on relevance
        products.sort((a, b) -> Double.compare(b.first, a.first));
        return products.stream().map(Pair::getSecond).toList();
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
                            .description(rs.getString("description"))
                            .sellingPrice(rs.getDouble("selling_price"))
                            .maximumRetailPrice(rs.getDouble("maximum_retail_price"))
                            .category(Category.builder()
                                    .categoryId(rs.getLong("category_id"))
                                    .categoryName(rs.getString("category_name"))
                                    .categoryDescription(rs.getString("category_description"))
                                    .build())
                            .manufacturers(new HashSet<>())
                            .imageUrl(rs.getString("image_url"))
                            .build();
                    productMap.put(productId, product);
                }
                Manufacturer manufacturer = Manufacturer.builder()
                        .manufacturerId(rs.getLong("manufacturer_id"))
                        .manufacturerName(rs.getString("manufacturer_name"))
                        .build();
                Double costPrice = rs.getDouble("cost_price");
                product.getManufacturers().add(new Product.Pair<>(manufacturer, costPrice));
            }
            return productMap;
        }
        
    }
    
    public static class RelevanceProductRowMapper implements RowMapper<Pair<Double, Product>> {
        @Override
        public Pair<Double, Product> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = Product.builder()
                    .productId(rs.getLong("product_id"))
                    .productName(rs.getString("product_name"))
                    .expiryDate(rs.getDate("expiry_date"))
                    .description(rs.getString("description"))
                    .stockQuantity(rs.getInt("stock_quantity"))
                    .sellingPrice(rs.getDouble("selling_price"))
                    .imageUrl(rs.getString("image_url"))
                    .maximumRetailPrice(rs.getDouble("maximum_retail_price"))
                    .category(Category.builder()
                            .categoryId(rs.getLong("category_id"))
                            .build())
                    .manufacturers(new HashSet<>())
                    .build();
            return new Pair<>(rs.getDouble("relevance"), product);
        }
    }

    public static class Pair<T, U> {
        public final T first;
        public final U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public U getSecond() {
            return second;
        }
    }
}
