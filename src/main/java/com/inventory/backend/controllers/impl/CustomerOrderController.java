package com.inventory.backend.controllers.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.backend.controllers.IModelCreateController;
import com.inventory.backend.controllers.IModelDeleteController;
import com.inventory.backend.controllers.IModelGetController;
import com.inventory.backend.controllers.IModelUpdateController;
import com.inventory.backend.entities.CustomerOrder;
import com.inventory.backend.services.IModelService;


@RestController
public class CustomerOrderController implements
                                        IModelCreateController<CustomerOrder, Long>,
                                        IModelUpdateController<CustomerOrder, Long>,
                                        IModelDeleteController<CustomerOrder, Long>,
                                        IModelGetController<CustomerOrder, Long>
                                     
{

    private IModelService<CustomerOrder, Long> customerOrderService;

    public CustomerOrderController(IModelService<CustomerOrder, Long> customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @GetMapping("/customerOrders/{id}")
    @Override
    public ResponseEntity<CustomerOrder> findById(@PathVariable Long id) {
        Optional<CustomerOrder> customerOrder = customerOrderService.findById(id);
        if (customerOrder.isPresent()) {
            return ResponseEntity.ok(customerOrder.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public List<CustomerOrder> findAll() {
        return customerOrderService.findAll();
    }

    @DeleteMapping("/customerOrders/{id}")
    @Override
    public ResponseEntity<CustomerOrder> delete(@PathVariable Long id) {
        customerOrderService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/customerOrders/{id}")
    @Override
    public ResponseEntity<CustomerOrder> update(@RequestBody CustomerOrder a, @PathVariable Long id) {
        Optional<CustomerOrder> customerOrder = customerOrderService.findById(id);
        if (customerOrder.isPresent()) {
            customerOrderService.update(a, id);
            return ResponseEntity.ok(a);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/customerOrders/{id}")
    @Override
    public ResponseEntity<CustomerOrder> partialUpdate(@RequestBody CustomerOrder a, @PathVariable Long id) {
        Optional<CustomerOrder> customerOrder = customerOrderService.findById(id);
        if (customerOrder.isPresent()) {
            customerOrderService.update(a, id);
            return ResponseEntity.ok(a);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/customerOrders")
    @Override
    public ResponseEntity<CustomerOrder> save(@RequestBody CustomerOrder a) {
        Optional<CustomerOrder> customerOrder = customerOrderService.save(a);
        if (customerOrder.isPresent()) {
            return ResponseEntity.ok(customerOrder.get());
        }
        return ResponseEntity.badRequest().build();
    }

    
    
}
