package com.inventory.backend.controllers;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

public interface IModelGetController<A, B> {
    
    public String findById(@PathVariable B id, Model model);

    public String findAll(Model model);
    
}
