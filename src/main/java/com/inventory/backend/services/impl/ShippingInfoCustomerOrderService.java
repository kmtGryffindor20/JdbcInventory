package com.inventory.backend.services.impl;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.ShippingInfoCustomerOrder;
import com.inventory.backend.services.IModelService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ShippingInfoCustomerOrderService implements IModelService<ShippingInfoCustomerOrder, Long> {

    private IDao<ShippingInfoCustomerOrder, Long> shippingInfoCustomerOrderDao;

    public ShippingInfoCustomerOrderService(IDao<ShippingInfoCustomerOrder, Long> shippingInfoCustomerOrderDao) {
        this.shippingInfoCustomerOrderDao = shippingInfoCustomerOrderDao;
    }

    @Override
    public Optional<ShippingInfoCustomerOrder> save(ShippingInfoCustomerOrder shippingInfoCustomerOrder) {
        shippingInfoCustomerOrderDao.create(shippingInfoCustomerOrder);
        return Optional.of(shippingInfoCustomerOrder);
    }

    @Override
    public Optional<ShippingInfoCustomerOrder> findById(Long id) {
        return shippingInfoCustomerOrderDao.findById(id);
    }

    @Override
    public List<ShippingInfoCustomerOrder> findAll() {
        return shippingInfoCustomerOrderDao.findAll();
    }

    @Override
    public Optional<ShippingInfoCustomerOrder> update(ShippingInfoCustomerOrder shippingInfoCustomerOrder, Long id) {
        shippingInfoCustomerOrderDao.update(shippingInfoCustomerOrder, id);
        return Optional.of(shippingInfoCustomerOrder);
    }

    @Override
    public void delete(Long id) {
        shippingInfoCustomerOrderDao.delete(id);
    }
    
}
