package com.inventory.backend.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.embeddable.SalesReportCompositeKey;
import com.inventory.backend.entities.SalesReport;

@Repository
public class SalesReportDao implements IDao<SalesReport, SalesReportCompositeKey>{

    private final JdbcTemplate jdbcTemplate;

    public SalesReportDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(SalesReport a) {
        String sql = "INSERT INTO sales_reports (day, month, year, total_sales, total_orders, top_selling_product_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, a.getSalesReportCompositeKey().getDay(), a.getSalesReportCompositeKey().getMonth(), a.getSalesReportCompositeKey().getYear(), a.getTotalSales(), a.getTotalOrders(), a.getTopSellingProductId());
    }

    @Override
    public Optional<SalesReport> findById(SalesReportCompositeKey id) {
        String sql = "SELECT * FROM sales_reports WHERE day = ? AND month = ? AND year = ?";
        List<SalesReport> results = jdbcTemplate.query(sql, new SalesReportRowMapper(), id.getDay(), id.getMonth(), id.getYear());
        return results.stream().findFirst();
    }

    @Override
    public List<SalesReport> findAll() {
        String sql = "SELECT * FROM sales_reports";
        return jdbcTemplate.query(sql, new SalesReportRowMapper());
    }

    @Override
    public void update(SalesReport a, SalesReportCompositeKey id) {
        String sql = "UPDATE sales_reports SET total_sales = ?, total_orders = ?, top_selling_product_id = ? WHERE day = ? AND month = ? AND year = ?";
        jdbcTemplate.update(sql, a.getTotalSales(), a.getTotalOrders(), a.getTopSellingProductId(), id.getDay(), id.getMonth(), id.getYear());
    }

    @Override
    public void delete(SalesReportCompositeKey id) {
        String sql = "DELETE FROM sales_reports WHERE day = ? AND month = ? AND year = ?";
        jdbcTemplate.update(sql, id.getDay(), id.getMonth(), id.getYear());
    }

    public static class SalesReportRowMapper implements RowMapper<SalesReport> {
        @Override
        public SalesReport mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return SalesReport.builder()
                    .salesReportCompositeKey(SalesReportCompositeKey.builder()
                            .day(rs.getInt("day"))
                            .month(rs.getInt("month"))
                            .year(rs.getInt("year"))
                            .build())
                    .totalSales(rs.getDouble("total_sales"))
                    .totalOrders(rs.getInt("total_orders"))
                    .topSellingProductId(rs.getLong("top_selling_product_id"))
                    .build();
        }
    }

   
    
}
