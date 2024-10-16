package com.inventory.backend.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.Employee;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;


@Repository
public class EmployeeDao implements IDao<Employee, Long> {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Employee employee) {
        String sql = "INSERT INTO employees (first_name, last_name, phone_number, hire_date, designation, manager_employee_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        int insertId = -1;

        try {
            PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getPhoneNumber());
            preparedStatement.setDate(4, employee.getHireDate());
            preparedStatement.setString(5, employee.getDesignation());
            if (employee.getManager() == null) {
                preparedStatement.setNull(6, java.sql.Types.BIGINT);
            } else {
                preparedStatement.setLong(6, employee.getManager().getEmployeeId());
            }

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0 && employee.getEmailAddresses() != null){
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next())
                {
                    insertId = generatedKeys.getInt(1);
    
                    String sql2 = "INSERT INTO employee_email_addresses (employee_id, email_address) VALUES (?, ?)";
                    List<Object[]> batchArgs = new ArrayList<>();
                    for (String email : employee.getEmailAddresses()) {
                        batchArgs.add(new Object[]{insertId, email});
                    }
                    jdbcTemplate.batchUpdate(sql2, batchArgs);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public Optional<Employee> findById(Long id) {

        String sql = "SELECT e.employee_id, e.first_name, e.last_name, e.phone_number, e.hire_date, e.designation, e.manager_employee_id, m.employee_id as manager_id, m.first_name as manager_first_name, m.last_name as manager_last_name, ea.email_address FROM employees e LEFT JOIN employees m ON e.manager_employee_id = m.employee_id LEFT JOIN employee_email_addresses ea ON e.employee_id = ea.employee_id WHERE e.employee_id = ?";

        Map<Long, Employee> employeeMap = jdbcTemplate.query(sql, new EmployeeRowMapper(), id);
        
        return employeeMap.values().stream().findFirst();
    }

    @Override
    public List<Employee> findAll() {
        // write join query to get all employees and their email addresses and managers
        String sql = "SELECT e.employee_id, e.first_name, e.last_name, e.phone_number, e.hire_date, e.designation, e.manager_employee_id, m.employee_id as manager_id, m.first_name as manager_first_name, m.last_name as manager_last_name, ea.email_address FROM employees e LEFT JOIN employees m ON e.manager_employee_id = m.employee_id LEFT JOIN employee_email_addresses ea ON e.employee_id = ea.employee_id";
        Map<Long, Employee> employeeMap = jdbcTemplate.query(sql, new EmployeeRowMapper());

        return employeeMap.values().stream().toList();
    }

    @Override
    public void update(Employee employee, Long id) {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, phone_number = ?, hire_date = ?, designation = ?, manager_employee_id = ? WHERE employee_id = ?";
        jdbcTemplate.update(sql, employee.getFirstName(), employee.getLastName(), employee.getPhoneNumber(), employee.getHireDate(), employee.getDesignation(), employee.getManager().getEmployeeId(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class EmployeeRowMapper implements ResultSetExtractor<Map<Long, Employee>> {
        @Override
        public Map<Long, Employee> extractData(ResultSet rs) throws SQLException {

            Map<Long, Employee> employeeMap = new HashMap<>();

            rs.next();
            
            do
            {
                Long employeeId = rs.getLong("employee_id");

                if (!employeeMap.containsKey(employeeId))
                {
                    Employee manager = Employee.builder()
                        .employeeId(rs.getLong("manager_id"))
                        .firstName(rs.getString("manager_first_name"))
                        .lastName(rs.getString("manager_last_name"))
                        .build();

                    Set<String> emailAddresses = new HashSet<>();
                    emailAddresses.add(rs.getString("email_address"));

                    Employee employee = Employee.builder()
                        .employeeId(employeeId)
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .phoneNumber(rs.getString("phone_number"))
                        .hireDate(rs.getDate("hire_date"))
                        .designation(rs.getString("designation"))
                        .manager(manager)
                        .emailAddresses(emailAddresses)
                        .build();

                    employeeMap.put(employeeId, employee);
                }
                else
                {
                    employeeMap.get(employeeId).getEmailAddresses().add(rs.getString("email_address"));
                }
            }while(rs.next());

            return employeeMap;

    }
}

    
}
