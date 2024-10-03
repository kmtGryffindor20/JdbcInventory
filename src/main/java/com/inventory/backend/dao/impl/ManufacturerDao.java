package com.inventory.backend.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.Manufacturer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ManufacturerDao implements IDao<Manufacturer, Long> {

    private final JdbcTemplate jdbcTemplate;

    public ManufacturerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Manufacturer manufacturer) {
        String sql = "INSERT INTO manufacturers (manufacturer_name, manufacturer_address) VALUES (?, ?)";
        int insertId = -1;
        try{
            PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, manufacturer.getManufacturerName());
            preparedStatement.setString(2, manufacturer.getAddress());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0)
            {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                insertId = generatedKeys.getInt(1);
                String sql2 = "INSERT INTO manufacturer_email_addresses (manufacturer_id, email_address) VALUES (?, ?)";
                for (String email : manufacturer.getEmailIds()) {
                    jdbcTemplate.update(sql2, insertId, email);
                }
    
                String sql3 = "INSERT INTO manufacturer_phone_numbers (manufacturer_id, phone_number) VALUES (?, ?)";
                for (String phone : manufacturer.getContactNumbers()) {
                    jdbcTemplate.update(sql3, insertId, phone);
                }
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        String sql = "SELECT * FROM manufacturers WHERE manufacturer_id = ?";
        List<Manufacturer> results = jdbcTemplate.query(sql, new ManufacturerRowMapper(), id);
        return results.stream().findFirst();
    }

    @Override
    public List<Manufacturer> findAll() {
        String sql = "SELECT * FROM manufacturers";
        return jdbcTemplate.query(sql, new ManufacturerRowMapper());
    }

    @Override
    public void update(Manufacturer manufacturer, Long id) {
        String sql = "UPDATE manufacturers SET manufacturer_name = ?, manufacturer_address = ? WHERE manufacturer_id = ?";
        jdbcTemplate.update(sql, manufacturer.getManufacturerName(), manufacturer.getAddress(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM manufacturers WHERE manufacturer_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class ManufacturerRowMapper implements RowMapper<Manufacturer> {
        @Override
        public Manufacturer mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return Manufacturer.builder()
                    .manufacturerId(rs.getLong("manufacturer_id"))
                    .manufacturerName(rs.getString("manufacturer_name"))
                    .address(rs.getString("manufacturer_address"))
                    .build();
        }
    }
    
}
