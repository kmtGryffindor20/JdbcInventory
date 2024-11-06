package com.inventory.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inventory.backend.dao.impl.CartDao;
import com.inventory.backend.entities.Cart;
import com.inventory.backend.services.IModelService;

@Service
public class CartService implements IModelService<Cart, Long> {

    private CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public Optional<Cart> save(Cart a) {
        cartDao.create(a);
        return Optional.of(a);
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartDao.findById(id);
    }

    @Override
    public List<Cart> findAll() {
        return cartDao.findAll();
    }

    @Override
    public Optional<Cart> update(Cart a, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void addProduct(Long cartId, Long productId, Integer quantity) {
        cartDao.addProduct(cartId, productId, quantity);
    }

    public void removeProduct(Long cartId, Long productId) {
        cartDao.removeProduct(cartId, productId);
    }

    public Long getCartIdByCustomerEmail(String email) {
        return cartDao.getCartIdByCustomerEmail(email);
    }

    @Override
    public void delete(Long id) {
        cartDao.delete(id);
    }
    
}
