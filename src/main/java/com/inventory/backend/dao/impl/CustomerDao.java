package com.inventory.backend.dao.impl;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.Customer;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao implements IDao<Customer, String> {


    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Customer customer) {
        String sql = "INSERT INTO customers (email, first_name, last_name, phone_number, shipping_address, billing_address) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, customer.getEmailId(), customer.getFirstName(), customer.getLastName(), customer.getPhoneNumber(), customer.getShippingAddress(), customer.getBillingAddress());
    }

    @Override
    public Optional<Customer> findById(String id) {
        String sql = "SELECT * FROM customers WHERE email = ?";
        List<Customer> results = jdbcTemplate.query(sql, new CustomerRowMapper(), id);
        return results.stream().findFirst();
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customers";
        return jdbcTemplate.query(sql, new CustomerRowMapper());
    }

    @Override
    public void update(Customer customer, String id) {
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, phone_number = ?, shipping_address = ?, billing_address = ? WHERE email = ?";
        jdbcTemplate.update(sql, customer.getFirstName(), customer.getLastName(), customer.getPhoneNumber(), customer.getShippingAddress(), customer.getBillingAddress(), id);
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM customers WHERE email = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return Customer.builder()
                    .emailId(rs.getString("email"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .phoneNumber(rs.getString("phone_number"))
                    .shippingAddress(rs.getString("shipping_address"))
                    .billingAddress(rs.getString("billing_address"))
                    .build();
        }
    }
    
}
