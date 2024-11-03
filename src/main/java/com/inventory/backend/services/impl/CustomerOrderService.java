package com.inventory.backend.services.impl;

import com.inventory.backend.dao.impl.CustomerOrderDao;
import com.inventory.backend.entities.CustomerOrder;
import com.inventory.backend.services.IModelService;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CustomerOrderService implements IModelService<CustomerOrder, Long> {

    private CustomerOrderDao customerOrderDao;
    private SalesReportService salesReportService;

    public CustomerOrderService(CustomerOrderDao customerOrderDao, SalesReportService salesReportService) {
        this.customerOrderDao = customerOrderDao;
        this.salesReportService = salesReportService;
    }

    @Override
    public Optional<CustomerOrder> save(CustomerOrder customerOrder) {
        customerOrderDao.create(customerOrder);
        // update SalesReport
        Date orderDate = customerOrder.getDateOfOrder();
        salesReportService.updateSalesReport(orderDate, customerOrder);
        return Optional.of(customerOrder);
    }

    @Override
    public Optional<CustomerOrder> findById(Long id) {
        return customerOrderDao.findById(id);
    }

    @Override
    public List<CustomerOrder> findAll() {
        return customerOrderDao.findAll();
    }

    @Override
    public Optional<CustomerOrder> update(CustomerOrder customerOrder, Long id) {
        customerOrderDao.update(customerOrder, id);
        return Optional.of(customerOrder);
    }

    @Override
    public void delete(Long id) {
        customerOrderDao.delete(id);
    }

    public Map<String, Double> totalSalesByCategory() {
        return customerOrderDao.totalSalesByCategory();
    }

    
    
}
