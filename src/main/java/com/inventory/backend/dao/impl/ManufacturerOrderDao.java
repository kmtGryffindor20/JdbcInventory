package com.inventory.backend.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.Category;
import com.inventory.backend.entities.CustomerOrder;
import com.inventory.backend.entities.Employee;
import com.inventory.backend.entities.Manufacturer;
import com.inventory.backend.entities.ManufacturerOrder;
import com.inventory.backend.entities.Product;
import com.inventory.backend.entities.ShippingInfoManufacturerOrder;
import com.inventory.backend.services.impl.ShippingInfoManufacturerOrderService;

@Repository
public class ManufacturerOrderDao implements IDao<ManufacturerOrder, Long> {

    private final JdbcTemplate jdbcTemplate;

    private ShippingInfoManufacturerOrderService shippingInfoManufacturerOrderService;

    public ManufacturerOrderDao(JdbcTemplate jdbcTemplate,
                                ShippingInfoManufacturerOrderService shippingInfoManufacturerOrderService) {
        this.jdbcTemplate = jdbcTemplate;
        this.shippingInfoManufacturerOrderService = shippingInfoManufacturerOrderService;
    }

    @Override
    public void create(ManufacturerOrder a) {
        String sql = "INSERT INTO manufacturer_orders (ordered_from, processed_by_employee_id, date_of_order) VALUES (?, ?, ?)";
        

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
                    int insertId = generatedKeys.getInt(1);
                    String sql2 = "INSERT INTO manufacturer_orders_products (manufacturer_order_id, product_id, quantity) VALUES (?, ?, ?)";
                    jdbcTemplate.batchUpdate(sql2, a.getProducts().stream().map(pair -> new Object[]{insertId, pair.first.getProductId(), pair.second}).toList());

                    a.setOrderId((long) insertId);
                    ShippingInfoManufacturerOrder shippingInfoManufacturerOrder = ShippingInfoManufacturerOrder.builder()
                            .shippingDate(null)
                            .expectedDeliveryDate(null)
                            .status(ShippingInfoManufacturerOrder.Status.PENDING)
                            .manufacturerOrder(a)
                            .build();
                    shippingInfoManufacturerOrderService.save(shippingInfoManufacturerOrder);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<ManufacturerOrder> findById(Long id) {
        String sql = "SELECT mo.*, m.*, p.*, e.*, mop.quantity FROM manufacturer_orders mo LEFT JOIN manufacturers m ON mo.ordered_from = m.manufacturer_id LEFT JOIN manufacturer_orders_products mop ON mo.order_id = mop.manufacturer_order_id LEFT JOIN products p ON mop.product_id = p.product_id LEFT JOIN employees e ON mo.processed_by_employee_id = e.employee_id WHERE mo.order_id = ?";
        TreeMap<Long, ManufacturerOrder> manufacturerOrderMap = jdbcTemplate.query(sql, new ManufacturerOrderRowMapper(), id);
        return manufacturerOrderMap.values().stream().findFirst();
    }

    @Override
    public List<ManufacturerOrder> findAll() {
        String sql = "SELECT mo.*, m.*, p.*, e.*, mop.quantity FROM manufacturer_orders mo LEFT JOIN manufacturers m ON mo.ordered_from = m.manufacturer_id LEFT JOIN manufacturer_orders_products mop ON mo.order_id = mop.manufacturer_order_id LEFT JOIN products p ON mop.product_id = p.product_id LEFT JOIN employees e ON mo.processed_by_employee_id = e.employee_id";
        TreeMap<Long, ManufacturerOrder> manufacturerOrderMap = jdbcTemplate.query(sql, new ManufacturerOrderRowMapper());
        return List.copyOf(manufacturerOrderMap.values());
    }

    @Override
    public void update(ManufacturerOrder a, Long id) {
        String sql = "UPDATE manufacturer_orders SET ordered_from = ?, processed_by_employee_id = ?, date_of_order = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, a.getManufacturer().getManufacturerId(), a.getProcessedByEmployee().getEmployeeId(), a.getDateOfOrder(), id);
        sql = "DELETE FROM manufacturer_orders_products WHERE manufacturer_order_id = ?";
        jdbcTemplate.update(sql, id);
        sql = "INSERT INTO manufacturer_orders_products (manufacturer_order_id, product_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, a.getProducts().stream().map(pair -> new Object[]{id, pair.first.getProductId(), pair.second}).toList());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM manufacturer_orders WHERE order_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class ManufacturerOrderRowMapper implements ResultSetExtractor<TreeMap<Long, ManufacturerOrder>> {
        @Override
        public TreeMap<Long, ManufacturerOrder> extractData(ResultSet rs) throws SQLException, DataAccessException {
            TreeMap<Long, ManufacturerOrder> manufacturerOrderMap = new TreeMap<>();
            while (rs.next()) {
                ManufacturerOrder manufacturerOrder = ManufacturerOrder.builder()
                        .orderId(rs.getLong("order_id"))
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

                Category category = Category.builder()
                        .categoryId(rs.getLong("category_id"))
                        .build();

                manufacturerOrderMap.get(manufacturerOrder.getOrderId()).getProducts().add(new CustomerOrder.Pair<>(Product.builder()
                        .productId(rs.getLong("product_id"))
                        .productName(rs.getString("product_name"))
                        .expiryDate(rs.getDate("expiry_date"))
                        .stockQuantity(rs.getInt("stock_quantity"))
                        .sellingPrice(rs.getDouble("selling_price"))
                        .maximumRetailPrice(rs.getDouble("maximum_retail_price"))
                        .category(category)
                        .build(), rs.getInt("quantity")));
            }
            return manufacturerOrderMap;
        }
        
    }

    
}
