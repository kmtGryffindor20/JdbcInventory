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
import com.inventory.backend.entities.Customer;
import com.inventory.backend.services.IModelService;


@RestController
public class CustomerController implements
                                         IModelCreateController<Customer, String>,
                                            IModelUpdateController<Customer, String>,
                                            IModelDeleteController<Customer, String>,
                                            IModelGetController<Customer, String>
{

    private IModelService<Customer, String> customerService;

    public CustomerController(IModelService<Customer, String> customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/customers/{id}")
    @Override
    public ResponseEntity<Customer> findById(@PathVariable String id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/customers")
    @Override
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @DeleteMapping("/customers/{id}")
    @Override
    public ResponseEntity<Customer> delete(@PathVariable String id) {
        customerService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/customers/{id}")
    @Override
    public ResponseEntity<Customer> update(@RequestBody Customer a, @PathVariable String id) {
        Optional <Customer> customer = customerService.update(a, id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/customers/{id}")
    @Override
    public ResponseEntity<Customer> partialUpdate(@RequestBody Customer a, @PathVariable String id) {
        Optional <Customer> customer = customerService.update(a, id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/customers")
    @Override
    public ResponseEntity<Customer> save(@RequestBody Customer a) {
        Optional <Customer> customer = customerService.save(a);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        }
        return ResponseEntity.badRequest().build();
    }

    
    
}
