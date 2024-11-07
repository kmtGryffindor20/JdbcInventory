package com.inventory.backend.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.Cart;
import com.inventory.backend.entities.Customer;
import com.inventory.backend.entities.Product;

@Repository
public class CartDao implements IDao<Cart, Long> {

    private final JdbcTemplate jdbcTemplate;
    
    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Cart a) {
        String sql = "INSERT INTO carts (customer_email) VALUES (?)";
        jdbcTemplate.update(sql, a.getCustomer().getEmailId());
    }

    @Override
    public Optional<Cart> findById(Long id) {
        String sql = "SELECT c.*, p.*, cp.* FROM carts c JOIN cart_products cp ON c.cart_id = cp.cart_id JOIN products p ON cp.product_id = p.product_id WHERE c.cart_id = ?";
        Map <Long, Cart> cartMap = jdbcTemplate.query(sql, new CartRowMapper(), id);
        if (cartMap.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(cartMap.values().iterator().next());
    }

    @Override
    public List<Cart> findAll() {
        String sql = "SELECT c.*, p.*, cp.* FROM carts c JOIN cart_products cp ON c.cart_id = cp.cart_id JOIN products p ON cp.product_id = p.product_id";
        Map <Long, Cart> cartMap = jdbcTemplate.query(sql, new CartRowMapper());
        return cartMap.values().stream().toList();
    }

    @Override
    public void update(Cart a, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public Boolean isProductInCart(Long cartId, Long productId) {
        String sql = "SELECT COUNT(*) FROM cart_products WHERE cart_id = ? AND product_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cartId, productId);
        return count > 0;
    }

    public void addProduct(Long cartId, Long productId, Integer quantity) {
        Boolean isProductInCart = isProductInCart(cartId, productId);
        if (isProductInCart) {
            String sql = "UPDATE cart_products SET quantity = ? WHERE cart_id = ? AND product_id = ?";
            jdbcTemplate.update(sql, quantity, cartId, productId);
            return;
        }
        String sql = "INSERT INTO cart_products (cart_id, product_id, quantity, added_on) VALUES (?, ?, ?, NOW())";
        jdbcTemplate.update(sql, cartId, productId, quantity);
    }

    public void removeProduct(Long cartId, Long productId) {
        String sql = "DELETE FROM cart_products WHERE cart_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, cartId, productId);
    }

    public Long getCartIdByCustomerEmail(String email) {
        String sql = "SELECT cart_id FROM carts WHERE customer_email = ?";
        List<Long> results = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("cart_id"), email);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM carts WHERE cart_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class CartRowMapper implements ResultSetExtractor<Map<Long, Cart>> {

        @Override
        public Map<Long, Cart> extractData(ResultSet rs) throws SQLException, DataAccessException {
            
            Map <Long, Cart> cartMap = new HashMap<>();
            while (rs.next()) {
                Long cartId = rs.getLong("cart_id");
                Cart cart = cartMap.get(cartId);
                if (cart == null) {
                    cart = new Cart();
                    cart.setCartId(cartId);
                    
                    Customer customer = Customer.builder()
                        .emailId(rs.getString("customer_email"))
                        .build();
                    cart.setCustomer(customer);

                    Product product = Product.builder()
                        .productId(rs.getLong("product_id"))
                        .productName(rs.getString("product_name"))
                        .sellingPrice(rs.getDouble("selling_price"))
                        .maximumRetailPrice(rs.getDouble("maximum_retail_price"))
                        .stockQuantity(rs.getInt("stock_quantity"))
                        .build();
                    cart.getProducts().add(Triple.of(product, rs.getInt("quantity"), rs.getDate("added_on")));
                    cartMap.put(cartId, cart);
                } else {
                    Product product = Product.builder()
                        .productId(rs.getLong("product_id"))
                        .productName(rs.getString("product_name"))
                        .sellingPrice(rs.getDouble("selling_price"))
                        .maximumRetailPrice(rs.getDouble("maximum_retail_price"))
                        .build();
                    cart.getProducts().add(Triple.of(product, rs.getInt("quantity"), rs.getDate("added_on")));
                }
            
            }
            return cartMap;
        }

    }
}
