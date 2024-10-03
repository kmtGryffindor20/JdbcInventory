package com.inventory.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IModelUpdateController <A, B> {
    
    public ResponseEntity<A> update(@RequestBody A a, @PathVariable B id);

    public ResponseEntity<A> partialUpdate(@RequestBody A a, @PathVariable B id);
    
}
