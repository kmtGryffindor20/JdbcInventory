package com.inventory.backend.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.inventory.backend.dao.GenericDao;
import com.inventory.backend.entities.Category;

@Repository
public class CatNew extends GenericDao<Category, Long> {

    @Autowired
    public CatNew(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }
    
}
