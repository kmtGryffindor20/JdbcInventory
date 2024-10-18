package com.inventory.backend.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.Customer;
import com.inventory.backend.entities.CustomerOrder;
import com.inventory.backend.entities.Employee;
import com.inventory.backend.entities.OrderReturns;

@Repository
public class OrderReturnsDao implements IDao<OrderReturns, CustomerOrder> {

    private final JdbcTemplate jdbcTemplate;

    public OrderReturnsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(OrderReturns a) {
        String sql = "INSERT INTO order_returns (order_id, return_date, return_reason) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, a.getOrder().getOrderId(), a.getReturnDate(), a.getReturnReason());
    }

    @Override
    public Optional<OrderReturns> findById(CustomerOrder id) {
        String sql = "SELECT or.*, co.* FROM order_returns or JOIN customer_orders co ON or.order_id = co.order_id WHERE or.order_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new OrderReturnsRowMapper(), id.getOrderId()));
    }

    @Override
    public List<OrderReturns> findAll() {
        String sql = "SELECT or.*, co.* FROM order_returns or JOIN customer_orders co ON or.order_id = co.order_id";

        return jdbcTemplate.query(sql, new OrderReturnsRowMapper());

    }

    @Override
    public void update(OrderReturns a, CustomerOrder id) {
        String sql = "UPDATE order_returns SET return_date = ?, return_reason = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, a.getOrder().getOrderId(), a.getReturnDate(), a.getReturnReason(), id.getOrderId());
    }

    @Override
    public void delete(CustomerOrder id) {
        String sql = "DELETE FROM order_returns WHERE order_id = ?";
        jdbcTemplate.update(sql, id.getOrderId());
    }

    public static class OrderReturnsRowMapper implements RowMapper<OrderReturns> {
        @Override
        public OrderReturns mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            CustomerOrder order = CustomerOrder.builder()
                                    .orderId(rs.getLong("order_id"))
                                    .dateOfOrder(rs.getDate("date_of_order"))
                                    .customer(Customer.builder().emailId(rs.getString("customer_email")).build())
                                    .processorEmployee(Employee.builder().employeeId(rs.getLong("processed_by_employee_id")).build())
                                    .paymentMethod(CustomerOrder.PaymentMethod.valueOf(rs.getString("payment_method")))
                                    .build();
            return OrderReturns.builder()
                    .order(order)
                    .returnDate(rs.getDate("return_date"))
                    .returnReason(rs.getString("return_reason"))
                    .build();
        }
    }
    
}
