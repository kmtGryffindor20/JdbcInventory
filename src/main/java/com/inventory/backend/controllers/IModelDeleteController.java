package com.inventory.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IModelDeleteController <A, B> {
        
    public ResponseEntity<A> delete(@PathVariable B id);
}
