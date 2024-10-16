package com.inventory.backend.dao.impl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Bean;


@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

    String name();

}
