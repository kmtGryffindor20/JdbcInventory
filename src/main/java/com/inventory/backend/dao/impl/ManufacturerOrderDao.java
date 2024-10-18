package com.inventory.backend.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.CustomerOrder;
import com.inventory.backend.entities.Employee;
import com.inventory.backend.entities.Manufacturer;
import com.inventory.backend.entities.ManufacturerOrder;
import com.inventory.backend.entities.Product;

@Repository
public class ManufacturerOrderDao implements IDao<ManufacturerOrder, Long> {

    private final JdbcTemplate jdbcTemplate;

    public ManufacturerOrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(ManufacturerOrder a) {
        String sql = "INSERT INTO manufacturer_orders (ordered_from, processed_by_employee_id, date_of_order) VALUES (?, ?, ?)";
        
        int insertId = -1;

        try{
            PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, a.getManufacturer().getManufacturerId());
            preparedStatement.setLong(2, a.getProcessedByEmployee().getEmployeeId());
            preparedStatement.setDate(3, a.getDateOfOrder());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0)
            {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                
                if (generatedKeys.next())
                {
                    insertId = generatedKeys.getInt(1);
                    String sql2 = "INSERT INTO manufacturer_order_products (manufacturer_order_id, product_id, quantity) VALUES (?, ?, ?)";
                    for (CustomerOrder.Pair<Product, Integer> product : a.getProducts()) {
                        jdbcTemplate.update(sql2, insertId, product.first.getProductId(), product.second);
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
        String sql = "SELECT mo.*, m.*, p.* FROM manufacturer_orders mo JOIN manufacturers m ON mo.ordered_from = m.manufacturer_id JOIN manufacturer_orders_products mop ON mo.manufacturer_order_id = mop.manufacturer_order_id JOIN products p ON mop.product_id = p.product_id WHERE mo.manufacturer_order_id = ?";
        Map<Long, ManufacturerOrder> manufacturerOrderMap = jdbcTemplate.query(sql, new ManufacturerOrderRowMapper(), id);
        return manufacturerOrderMap.values().stream().findFirst();
    }

    @Override
    public List<ManufacturerOrder> findAll() {
        String sql = "SELECT mo.*, m.*, p.* FROM manufacturer_orders mo JOIN manufacturers m ON mo.ordered_from = m.manufacturer_id JOIN manufacturer_orders_products mop ON mo.manufacturer_order_id = mop.manufacturer_order_id JOIN products p ON mop.product_id = p.product_id";
        Map<Long, ManufacturerOrder> manufacturerOrderMap = jdbcTemplate.query(sql, new ManufacturerOrderRowMapper());
        return List.copyOf(manufacturerOrderMap.values());
    }

    @Override
    public void update(ManufacturerOrder a, Long id) {
        String sql = "UPDATE manufacturer_orders SET processed_by_employee_id = ?, date_of_order = ? WHERE manufacturer_order_id = ?";
        jdbcTemplate.update(sql, a.getProcessedByEmployee().getEmployeeId(), a.getDateOfOrder(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM manufacturer_orders WHERE manufacturer_order_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class ManufacturerOrderRowMapper implements ResultSetExtractor<Map<Long, ManufacturerOrder>> {
        @Override
        public Map<Long, ManufacturerOrder> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, ManufacturerOrder> manufacturerOrderMap = new HashMap<>();
            while (rs.next()) {
                ManufacturerOrder manufacturerOrder = ManufacturerOrder.builder()
                        .orderId(rs.getLong("manufacturer_order_id"))
                        .manufacturer(Manufacturer.builder()
                                .manufacturerId(rs.getLong("manufacturer_id"))
                                .manufacturerName(rs.getString("manufacturer_name"))
                                .build())
                        .processedByEmployee(Employee.builder()
                                .employeeId(rs.getLong("processed_by_employee_id"))
                                .firstName(rs.getString("first_name"))
                                .lastName(rs.getString("last_name"))
                                .phoneNumber(rs.getString("phone_number"))
                                .hireDate(rs.getDate("hire_date"))
                                .designation(rs.getString("designation"))
                                .build())
                        .dateOfOrder(rs.getDate("date_of_order"))
                        .products(new HashSet<>())
                        .build();
                manufacturerOrderMap.putIfAbsent(manufacturerOrder.getOrderId(), manufacturerOrder);
                manufacturerOrderMap.get(manufacturerOrder.getOrderId()).getProducts().add(new CustomerOrder.Pair<>(Product.builder()
                        .productId(rs.getLong("product_id"))
                        .productName(rs.getString("product_name"))
                        .expiryDate(rs.getDate("expiry_date"))
                        .stockQuantity(rs.getLong("stock_quantity"))
                        .costPrice(rs.getDouble("cost_price"))
                        .maximumRetailPrice(rs.getDouble("maximum_retail_price"))
                        .categoryId(rs.getLong("category_id"))
                        .build(), rs.getInt("quantity")));
            }
            return manufacturerOrderMap;
        }
        
    }

    
}
