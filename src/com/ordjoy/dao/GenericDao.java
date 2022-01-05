package com.ordjoy.dao;

import com.ordjoy.entity.Entity;

import java.util.Optional;

public interface GenericDao<K, E extends Entity> {

    Optional<E> findById(K id);

    Optional<E> findByName(String name);

    void update(E entity);

    boolean deleteById(K id);
}