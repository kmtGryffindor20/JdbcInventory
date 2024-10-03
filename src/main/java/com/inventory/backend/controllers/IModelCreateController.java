package com.inventory.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IModelCreateController <A, B> {
    
    public ResponseEntity<A> save(@RequestBody A a);
    
}
