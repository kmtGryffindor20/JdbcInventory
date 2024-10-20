package com.inventory.backend.services.impl;

import com.inventory.backend.dao.impl.ProductDao;
import com.inventory.backend.entities.Product;
import com.inventory.backend.services.IModelService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ProductService implements IModelService<Product, Long> {

    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Optional<Product> save(Product product) {
        productDao.create(product);
        return Optional.of(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productDao.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Optional<Product> update(Product product, Long id) {
        productDao.update(product, id);
        return Optional.of(product);
    }

    @Override
    public void delete(Long id) {
        productDao.delete(id);
    }

    public List<Product> findDealsOfTheDay() {
        return productDao.findDealsOfTheDay();
    }

    public List<Product> getByCategory(Long categoryId) {
        return productDao.getByCategory(categoryId);
    }
    
}
