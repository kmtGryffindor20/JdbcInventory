package com.inventory.backend.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.CustomerOrder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerOrderDao implements IDao<CustomerOrder, Long> {

    private final JdbcTemplate jdbcTemplate;

    public CustomerOrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(CustomerOrder customerOrder) {
        String sql = "INSERT INTO customer_orders (date_of_order, customer_email, processor_employee_id, payment_method) VALUES (?, ?, ?, ?, ?)";
        int insertId = -1;
        try{
            PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, customerOrder.getDateOfOrder());
            preparedStatement.setString(2, customerOrder.getCustomer().getEmailId());
            preparedStatement.setLong(3, customerOrder.getProcessorEmployeeId());
            preparedStatement.setString(4, customerOrder.getPaymentMethod());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0)
            {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                insertId = generatedKeys.getInt(1);

                String sql2 = "INSERT INTO customer_order_products (order_id, product_id, quantity) VALUES (?, ?, ?)";
                for (CustomerOrder.Pair<Long, Integer> product : customerOrder.getProducts()) {
                    jdbcTemplate.update(sql2, insertId, product.first, product.second);
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public Optional<CustomerOrder> findById(Long id) {
        String sql = "SELECT * FROM customer_orders WHERE order_id = ?";
        List<CustomerOrder> results = jdbcTemplate.query(sql, new CustomerOrderRowMapper(), id);
        return results.stream().findFirst();
    }

    @Override
    public List<CustomerOrder> findAll() {
        String sql = "SELECT * FROM customer_orders";
        return jdbcTemplate.query(sql, new CustomerOrderRowMapper());
    }

    @Override
    public void update(CustomerOrder customerOrder, Long id) {
        String sql = "UPDATE customer_orders SET date_of_order = ?, customer_email = ?, processor_employee_id = ?, payment_method = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, customerOrder.getDateOfOrder(), customerOrder.getCustomer().getEmailId(), customerOrder.getProcessorEmployeeId(), customerOrder.getPaymentMethod(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM customer_orders WHERE order_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class CustomerOrderRowMapper implements RowMapper<CustomerOrder> {
        @Override
        public CustomerOrder mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return CustomerOrder.builder()
                    .orderId(rs.getLong("order_id"))
                    .dateOfOrder(rs.getDate("date_of_order"))
                    .paymentMethod(rs.getString("payment_method"))
                    .processorEmployeeId(rs.getLong("processor_employee_id"))
                    .build();
        }
    }
    
}
