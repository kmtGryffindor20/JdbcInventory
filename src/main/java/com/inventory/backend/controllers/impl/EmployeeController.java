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
import com.inventory.backend.entities.Employee;
import com.inventory.backend.services.IModelService;

@RestController
public class EmployeeController implements
                                IModelCreateController<Employee, Long>,
                                IModelUpdateController<Employee, Long>,
                                IModelDeleteController<Employee, Long>,
                                IModelGetController<Employee, Long>
{

    private IModelService<Employee, Long> employeeService;
    
    public EmployeeController(IModelService<Employee, Long> employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees/{id}")
    @Override
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/employees")
    @Override
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @DeleteMapping("/employees/{id}")
    @Override
    public ResponseEntity<Employee> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/employees/{id}")
    @Override
    public ResponseEntity<Employee> update(@RequestBody Employee a, @PathVariable Long id) {
        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isPresent()) {
            employeeService.update(a, id);
            return ResponseEntity.ok(a);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/employees/{id}")
    @Override
    public ResponseEntity<Employee> partialUpdate(@RequestBody Employee a, @PathVariable Long id) {
        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isPresent()) {
            employeeService.update(a, id);
            return ResponseEntity.ok(a);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/employees")
    @Override
    public ResponseEntity<Employee> save(@RequestBody Employee a) {
        Optional<Employee> employee = employeeService.save(a);
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        }
        return ResponseEntity.badRequest().build();
    }
    
    
}
