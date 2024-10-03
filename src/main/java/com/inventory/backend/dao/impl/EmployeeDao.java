package com.inventory.backend.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
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
            if (employee.getManagerId() == null) {
                preparedStatement.setNull(6, java.sql.Types.BIGINT);
            } else {
                preparedStatement.setLong(6, employee.getManagerId());
            }

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0 && employee.getEmailAddresses() != null){
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next())
                {
                    insertId = generatedKeys.getInt(1);
    
                    String sql2 = "INSERT INTO employee_email_addresses (employee_id, email_address) VALUES (?, ?)";
                    for (String email : employee.getEmailAddresses()) {
                        jdbcTemplate.update(sql2, insertId, email);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public Optional<Employee> findById(Long id) {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        List<Employee> results = jdbcTemplate.query(sql, new EmployeeRowMapper(), id);
        Set<String> emailAddresses = new HashSet<>();
        String sql2 = "SELECT email_address FROM employee_email_addresses WHERE employee_id = ?";
        List<String> emailResults = jdbcTemplate.query(sql2, (rs, rowNum) -> rs.getString("email_address"), id);
        emailAddresses.addAll(emailResults);
        if (results.size() > 0) {
            Employee employee = results.get(0);
            employee.setEmailAddresses(emailAddresses);
            return Optional.of(employee);
        }
        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        List<Employee> results = jdbcTemplate.query(sql, new EmployeeRowMapper());
        for (Employee employee : results) {
            Set<String> emailAddresses = new HashSet<>();
            String sql2 = "SELECT email_address FROM employee_email_addresses WHERE employee_id = ?";
            List<String> emailResults = jdbcTemplate.query(sql2, (rs, rowNum) -> rs.getString("email_address"), employee.getEmployeeId());
            emailAddresses.addAll(emailResults);
            employee.setEmailAddresses(emailAddresses);
        }
        return results;
    }

    @Override
    public void update(Employee employee, Long id) {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, phone_number = ?, hire_date = ?, designation = ?, manager_employee_id = ? WHERE employee_id = ?";
        jdbcTemplate.update(sql, employee.getFirstName(), employee.getLastName(), employee.getPhoneNumber(), employee.getHireDate(), employee.getDesignation(), employee.getManagerId(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return Employee.builder()
                    .employeeId(rs.getLong("employee_id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .phoneNumber(rs.getString("phone_number"))
                    .hireDate(rs.getDate("hire_date"))
                    .designation(rs.getString("designation"))
                    .managerId(rs.getLong("manager_employee_id"))
                    .build();
    }
}

    
}
