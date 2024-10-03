package com.inventory.backend.services.impl;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.CustomerOrder;
import com.inventory.backend.services.IModelService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CustomerOrderService implements IModelService<CustomerOrder, Long> {

    private IDao<CustomerOrder, Long> customerOrderDao;

    public CustomerOrderService(IDao<CustomerOrder, Long> customerOrderDao) {
        this.customerOrderDao = customerOrderDao;
    }

    @Override
    public Optional<CustomerOrder> save(CustomerOrder customerOrder) {
        customerOrderDao.create(customerOrder);
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
    
}
