package com.ordjoy.dao;

import com.ordjoy.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T extends Entity> {

    Optional<T> findById(Long id);

    Optional<T> findByName(String name);

    List<T> findAll();

    void update(T entity);

    boolean deleteById(Long id);
}