package com.inventory.backend.services.impl;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.OrderReturns;
import com.inventory.backend.services.IModelService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class OrderReturnsService implements IModelService<OrderReturns, Long> {

    private IDao<OrderReturns, Long> orderReturnsDao;

    public OrderReturnsService(IDao<OrderReturns, Long> orderReturnsDao) {
        this.orderReturnsDao = orderReturnsDao;
    }

    @Override
    public Optional<OrderReturns> save(OrderReturns orderReturns) {
        orderReturnsDao.create(orderReturns);
        return Optional.of(orderReturns);
    }

    @Override
    public Optional<OrderReturns> findById(Long id) {
        return orderReturnsDao.findById(id);
    }

    @Override
    public List<OrderReturns> findAll() {
        return orderReturnsDao.findAll();
    }

    @Override
    public Optional<OrderReturns> update(OrderReturns orderReturns, Long id) {
        orderReturnsDao.update(orderReturns, id);
        return Optional.of(orderReturns);
    }

    @Override
    public void delete(Long id) {
        orderReturnsDao.delete(id);
    }
    
}