package com.inventory.backend.services.impl;

import com.inventory.backend.dao.impl.SalesReportDao;
import com.inventory.backend.embeddable.SalesReportCompositeKey;
import com.inventory.backend.entities.CustomerOrder;
import com.inventory.backend.entities.Product;
import com.inventory.backend.entities.SalesReport;
import com.inventory.backend.services.IModelService;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SalesReportService implements IModelService<SalesReport, SalesReportCompositeKey> {

    private SalesReportDao salesReportDao;

    public SalesReportService(SalesReportDao salesReportDao) {
        this.salesReportDao = salesReportDao;
    }

    @Override
    public Optional<SalesReport> save(SalesReport salesReport) {
        salesReportDao.create(salesReport);
        return Optional.of(salesReport);
    }

    @Override
    public Optional<SalesReport> findById(SalesReportCompositeKey id) {
        return salesReportDao.findById(id);
    }

    @Override
    public List<SalesReport> findAll() {
        return salesReportDao.findAll();
    }

    @Override
    public Optional<SalesReport> update(SalesReport salesReport, SalesReportCompositeKey id) {
        salesReportDao.update(salesReport, id);
        return Optional.of(salesReport);
    }

    public Optional<SalesReport> updateSalesReport(Date orderDate, CustomerOrder customerOrder)
    {
        SalesReportCompositeKey key = convertToKey(orderDate);
        Optional<SalesReport> salesReport = salesReportDao.findById(key);
        if (salesReport.isPresent()) {
            SalesReport sr = salesReport.get();
            for (CustomerOrder.Pair<Product, Integer> iterable_element : customerOrder.getProducts()) {
                sr.setTotalSales(sr.getTotalSales() + iterable_element.first.getCostPrice() * iterable_element.second);
            }
            sr.setTotalOrders(sr.getTotalOrders() + 1);
            salesReportDao.update(sr, key);
        } else {
            SalesReport newSalesReport = SalesReport.builder()
                                                    .salesReportCompositeKey(key)
                                                    .totalOrders(1)
                                                    .build();
            for (CustomerOrder.Pair<Product, Integer> iterable_element : customerOrder.getProducts()) {
                newSalesReport.setTotalSales(newSalesReport.getTotalSales() + iterable_element.first.getCostPrice() * iterable_element.second);
            }
            salesReportDao.create(newSalesReport);
        }
        return salesReport;
    }

    @Override
    public void delete(SalesReportCompositeKey id) {
        salesReportDao.delete(id);
    }   

    public Map<Date, Double> weeklySales(Date startDate, Date endDate) {
        new SalesReportCompositeKey();
        SalesReportCompositeKey startDateKey =  SalesReportCompositeKey.builder()
                                                                            .day(startDate.toLocalDate().getDayOfMonth())
                                                                            .month(startDate.toLocalDate().getMonthValue())
                                                                            .year(startDate.toLocalDate().getYear())
                                                                            .build();

        SalesReportCompositeKey endDateKey =  SalesReportCompositeKey.builder()
                                                                            .day(endDate.toLocalDate().getDayOfMonth())
                                                                            .month(endDate.toLocalDate().getMonthValue())
                                                                            .year(endDate.toLocalDate().getYear())
                                                                            .build();

        List<SalesReport> sr = salesReportDao.weeklySales(startDateKey, endDateKey);
        Map <Date, Double> weeklySales = new java.util.HashMap<>();
        for (SalesReport salesReport : sr) {
            weeklySales.put(convertToDate(salesReport.getSalesReportCompositeKey()), salesReport.getTotalSales());
        }

        return weeklySales;
    }

    private Date convertToDate(SalesReportCompositeKey key) {
        return Date.valueOf(key.getYear() + "-" + key.getMonth() + "-" + key.getDay());
    }

    private SalesReportCompositeKey convertToKey(Date date) {
        String[] dateParts = date.toString().split("-");
        return SalesReportCompositeKey.builder()
                                        .day(Integer.parseInt(dateParts[2]))
                                        .month(Integer.parseInt(dateParts[1]))
                                        .year(Integer.parseInt(dateParts[0]))
                                        .build();
    }
    
}
