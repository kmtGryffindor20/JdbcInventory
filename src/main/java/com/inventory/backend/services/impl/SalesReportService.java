package com.inventory.backend.services.impl;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.embeddable.SalesReportCompositeKey;
import com.inventory.backend.entities.SalesReport;
import com.inventory.backend.services.IModelService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class SalesReportService implements IModelService<SalesReport, SalesReportCompositeKey> {

    private IDao<SalesReport, SalesReportCompositeKey> salesReportDao;

    public SalesReportService(IDao<SalesReport, SalesReportCompositeKey> salesReportDao) {
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

    @Override
    public void delete(SalesReportCompositeKey id) {
        salesReportDao.delete(id);
    }   
    
}
