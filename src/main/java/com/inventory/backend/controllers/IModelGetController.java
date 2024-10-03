package com.inventory.backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IModelGetController<A, B> {
    
    public ResponseEntity<A> findById(@PathVariable B id);

    public List<A> findAll();
    
}
