package com.inventory.backend.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.inventory.backend.dao.impl.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenericDao<T, I> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Class<T> entityClass;

    private String tableName;

    private String idName;

    @SuppressWarnings("unchecked")
    public GenericDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityClass = (Class<T>) ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.tableName = entityClass.getAnnotation(Table.class).name();
        this.idName = convertCamelToSnake(entityClass.getDeclaredFields()[0].getName());
    }

    public void create(T t) {
        System.out.println(tableName);
        String sql = "INSERT INTO " + tableName + " (";
        String values = " VALUES (";
        for (var field : entityClass.getDeclaredFields()) {
            sql += convertCamelToSnake(field.getName()) + ", ";
            values += "?, ";
        }
        sql = sql.substring(0, sql.length() - 2) + ")";
        values = values.substring(0, values.length() - 2) + ")";
        sql += values;

        try {
            jdbcTemplate.update(sql, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public Optional<T> findById(I id) {
        String sql = "SELECT * FROM " + tableName + " WHERE " +  idName + " = ?";
        
        try {
            Object entity = jdbcTemplate.query(sql, new Mapper(entityClass), id);
            return Optional.of((T) entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        String sql = "SELECT * FROM " + tableName;
        
        try {
            return (List<T>) jdbcTemplate.query(sql, new Mapper(entityClass));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void update(T t, I id) {
        String sql = "UPDATE " + tableName + " SET ";
        for (var field : entityClass.getDeclaredFields()) {
            sql += convertCamelToSnake(field.getName()) + " = ?, ";
        }
        sql = sql.substring(0, sql.length() - 2) + " WHERE " + idName + " = ?";

        try {
            jdbcTemplate.update(sql, t.getClass().getDeclaredFields(), id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(I id) {
        String sql = "DELETE FROM " + tableName + " WHERE " + idName + " = ?";
        
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertCamelToSnake(String name) {
        StringBuilder result = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (Character.isUpperCase(c)) {
                if (result.length() > 0) {
                    result.append('_'); // Add underscore before uppercase letters (except the first)
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    

    public static class Mapper implements ResultSetExtractor<Object> {
        private Class<?> entityClass;

        public Mapper(Class<?> entityClass) {
            this.entityClass = entityClass;
        }

        @Override
        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Object> list = new ArrayList<>();
            while (rs.next()) {
                Object entity = null;
                try {
                    entity = entityClass.getDeclaredConstructor().newInstance();
                    for (var field : entityClass.getDeclaredFields()) {
                        field.setAccessible(true);
                        field.set(entity, rs.getObject(field.getName()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                list.add(entity);
            }
            return list;
        }
    }
    
}
