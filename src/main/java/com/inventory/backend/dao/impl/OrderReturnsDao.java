package com.inventory.backend.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.Customer;
import com.inventory.backend.entities.CustomerOrder;
import com.inventory.backend.entities.Employee;
import com.inventory.backend.entities.OrderReturns;
import com.inventory.backend.entities.Product;

@Repository
public class OrderReturnsDao implements IDao<OrderReturns, CustomerOrder> {

    private final JdbcTemplate jdbcTemplate;

    public OrderReturnsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(OrderReturns a) {
        String sql = "INSERT INTO orders_returned (order_id, return_date, return_reason) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, a.getOrder().getOrderId(), a.getReturnDate(), a.getReturnReason());
    }

    @Override
    public Optional<OrderReturns> findById(CustomerOrder id) {
        String sql = "SELECT ors.*, co.* FROM orders_returned ors JOIN customer_orders co ON ors.order_id = co.order_id WHERE ors.order_id = ?";
        List<OrderReturns> results = jdbcTemplate.query(sql, new OrderReturnsNormalRowMapper(), id.getOrderId());
        return results.stream().findFirst();
    }

    @Override
    public List<OrderReturns> findAll() {
        String sql = "SELECT ors.*, co.*, c.*, p.product_id, p.product_name, p.maximum_retail_price, cop.quantity FROM orders_returned ors JOIN customer_orders co ON ors.order_id = co.order_id JOIN customers c ON co.customer_email = c.email JOIN customer_orders_products cop ON co.order_id = cop.order_id JOIN products p ON cop.product_id = p.product_id";
        TreeMap<Long, OrderReturns> orderReturns = jdbcTemplate.query(sql, new OrderReturnsRowMapper());
        return List.copyOf(orderReturns.values());
    }

    @Override
    public void update(OrderReturns a, CustomerOrder id) {
        String sql = "UPDATE orders_returned SET return_date = ?, return_reason = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, a.getOrder().getOrderId(), a.getReturnDate(), a.getReturnReason(), id.getOrderId());
    }

    @Override
    public void delete(CustomerOrder id) {
        String sql = "DELETE FROM orders_returned WHERE order_id = ?";
        jdbcTemplate.update(sql, id.getOrderId());
    }

    public List<Long> findOrderIds() {
        String sql = "SELECT order_id FROM orders_returned";
        return jdbcTemplate.queryForList(sql, Long.class);
    }

    public static class OrderReturnsNormalRowMapper implements RowMapper<OrderReturns> {
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

    public static class OrderReturnsRowMapper implements ResultSetExtractor<TreeMap<Long, OrderReturns>> {

        @Override
        public TreeMap<Long, OrderReturns> extractData(java.sql.ResultSet rs) throws java.sql.SQLException,
                org.springframework.dao.DataAccessException {
            TreeMap<Long, OrderReturns> orderReturns = new TreeMap<>();
            
            while(rs.next())
            {
                Long orderId = rs.getLong("order_id");

                if (!orderReturns.containsKey(orderId)) {
                   Customer customer = Customer.builder()
                            .emailId(rs.getString("email"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .phoneNumber(rs.getString("phone_number"))
                            .shippingAddress(rs.getString("shipping_address"))
                            .billingAddress(rs.getString("billing_address"))
                            .build();
                    Employee employee = Employee.builder()
                            .employeeId(rs.getLong("processed_by_employee_id"))
                            .build();

                    Set<CustomerOrder.Pair<Product, Integer>> products = new HashSet<>();

                    CustomerOrder.Pair<Product, Integer> product = new CustomerOrder.Pair<>(Product.builder()
                        .productId(rs.getLong("product_id"))
                        .productName(rs.getString("product_name"))
                        .maximumRetailPrice(rs.getDouble("maximum_retail_price"))
                        .build(), rs.getInt("quantity"));

                    products.add(product);


                    CustomerOrder customerOrder = CustomerOrder.builder()
                            .orderId(orderId)
                            .customer(customer)
                            .dateOfOrder(rs.getDate("date_of_order"))
                            .paymentMethod(CustomerOrder.PaymentMethod.valueOf(rs.getString("payment_method")))
                            .processorEmployee(employee)
                            .products(products)
                            .build();

                    OrderReturns orderReturn = OrderReturns.builder()
                            .order(customerOrder)
                            .returnDate(rs.getDate("return_date"))
                            .returnReason(rs.getString("return_reason"))
                            .build();

                    orderReturns.put(orderId, orderReturn);
                }
                else{
                    OrderReturns orderReturn = orderReturns.get(orderId);
                    CustomerOrder.Pair<Product, Integer> product = new CustomerOrder.Pair<>(Product.builder()
                        .productId(rs.getLong("product_id"))
                        .productName(rs.getString("product_name"))
                        .maximumRetailPrice(rs.getDouble("maximum_retail_price"))
                        .build(), rs.getInt("quantity"));
                    orderReturn.getOrder().getProducts().add(product);
                }
            }
            return orderReturns;
        }
    }
    
}
