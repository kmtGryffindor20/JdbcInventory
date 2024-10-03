package com.inventory.backend.services.impl;

import com.inventory.backend.dao.IDao;
import com.inventory.backend.entities.ShippingInfoManufacturerOrder;
import com.inventory.backend.services.IModelService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ShippingInfoManufacturerOrderService implements IModelService<ShippingInfoManufacturerOrder, Long> {

    private IDao<ShippingInfoManufacturerOrder, Long> shippingInfoManufacturerOrderDao;

    public ShippingInfoManufacturerOrderService(IDao<ShippingInfoManufacturerOrder, Long> shippingInfoManufacturerOrderDao) {
        this.shippingInfoManufacturerOrderDao = shippingInfoManufacturerOrderDao;
    }

    @Override
    public Optional<ShippingInfoManufacturerOrder> save(ShippingInfoManufacturerOrder shippingInfoManufacturerOrder) {
        shippingInfoManufacturerOrderDao.create(shippingInfoManufacturerOrder);
        return Optional.of(shippingInfoManufacturerOrder);
    }

    @Override
    public Optional<ShippingInfoManufacturerOrder> findById(Long id) {
        return shippingInfoManufacturerOrderDao.findById(id);
    }

    @Override
    public List<ShippingInfoManufacturerOrder> findAll() {
        return shippingInfoManufacturerOrderDao.findAll();
    }

    @Override
    public Optional<ShippingInfoManufacturerOrder> update(ShippingInfoManufacturerOrder shippingInfoManufacturerOrder, Long id) {
        shippingInfoManufacturerOrderDao.update(shippingInfoManufacturerOrder, id);
        return Optional.of(shippingInfoManufacturerOrder);
    }

    @Override
    public void delete(Long id) {
        shippingInfoManufacturerOrderDao.delete(id);
    }
    
}
